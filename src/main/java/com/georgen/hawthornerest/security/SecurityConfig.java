package com.georgen.hawthornerest.security;

import com.georgen.hawthornerest.model.users.Permission;
import com.georgen.hawthornerest.model.users.Role;
import com.georgen.hawthornerest.services.SettingsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.georgen.hawthornerest.model.constants.RestApiPath.*;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private AuthenticationProvider authenticationProvider;
    private JwtFilter jwtFilter;
    private SettingsService settingsService;
    private AuthenticationEntryPoint jwtEntryPoint;

    public SecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtFilter jwtFilter,
            SettingsService settingsService,
            @Qualifier("jwtEntryPoint") AuthenticationEntryPoint jwtEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
        this.settingsService = settingsService;
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        boolean isAuthRequired = settingsService.get().isAuthRequired();

        if (isAuthRequired){
            setAuthenticationPolicy(security);
        } else {
            permitAnyRequest(security);
        }

        security.sessionManagement(
                        sessionCustomizer -> sessionCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        security.authenticationProvider(authenticationProvider);
        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        security.csrf(AbstractHttpConfigurer::disable);
        security.cors(
                cors -> cors.configurationSource(
                        getCorsConfiguration("*")
                )
        );
        security.exceptionHandling(
                customizer -> customizer.authenticationEntryPoint(jwtEntryPoint)
        );

        return security.build();
    }

    private void permitAnyRequest(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(requestsCustomizer -> requestsCustomizer.anyRequest().permitAll());
    }

    private void setAuthenticationPolicy(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(
                requestsCustomizer -> {
                    requestsCustomizer

                            .requestMatchers(
                                    getAuthPath(),
                                    "/openapi",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**"
                            )
                            .permitAll()

                            .requestMatchers(getGroupPath(SETTINGS))
                            .hasRole(Role.ADMIN.name())

                            .requestMatchers(HttpMethod.GET, getReadingPathsArray(DOCUMENT))
                            .hasAuthority(Permission.DOCUMENT_READ.getSubject())

                            .requestMatchers(HttpMethod.POST, getGroupPath(DOCUMENT))
                            .hasAuthority(Permission.DOCUMENT_WRITE.getSubject())

                            .requestMatchers(HttpMethod.DELETE, getGroupPath(DOCUMENT))
                            .hasAuthority(Permission.DOCUMENT_WRITE.getSubject())

                            .requestMatchers(HttpMethod.GET, getReadingPathsArray(FILE))
                            .hasAuthority(Permission.FILE_READ.getSubject())

                            .requestMatchers(HttpMethod.POST, getGroupPath(FILE))
                            .hasAuthority(Permission.FILE_WRITE.getSubject())

                            .requestMatchers(HttpMethod.DELETE, getGroupPath(FILE))
                            .hasAuthority(Permission.FILE_WRITE.getSubject())

                            .requestMatchers(HttpMethod.GET, getReadingPathsArray(USER))
                            .hasAuthority(Permission.USER_READ.getSubject())

                            .requestMatchers(HttpMethod.POST, getGroupPath(USER))
                            .hasAuthority(Permission.USER_WRITE.getSubject())

                            .requestMatchers(getGroupPath(USER, "/bulkActivation"))
                            .hasAuthority(Permission.USER_WRITE.getSubject())

                            .requestMatchers(HttpMethod.DELETE, getGroupPath(USER))
                            .hasAuthority(Permission.USER_WRITE.getSubject())

                            .anyRequest()
                            .authenticated();
                }
        );
    }

    private UrlBasedCorsConfigurationSource getCorsConfiguration(String... origins){
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(origins));
        corsConfiguration.setAllowedMethods(List.of(origins));
        corsConfiguration.setAllowedHeaders(List.of(origins));
        corsConfiguration.setExposedHeaders(List.of(origins));

        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }
}

package com.georgen.hawthornerest.security;

import com.georgen.hawthornerest.model.users.Permission;
import com.georgen.hawthornerest.model.users.Role;
import com.georgen.hawthornerest.services.SettingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.georgen.hawthornerest.model.constants.RestApi.*;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private AuthenticationProvider authenticationProvider;
    private JwtFilter jwtFilter;
    private SettingsService settingsService;

    public SecurityConfig(
            AuthenticationProvider authenticationProvider,
            JwtFilter jwtFilter,
            SettingsService settingsService
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtFilter = jwtFilter;
        this.settingsService = settingsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        boolean isAuthRequired = settingsService.get().isAuthRequired();

        security.csrf(securityCustomizer -> securityCustomizer.disable());

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

        return security.build();
    }

    private void permitAnyRequest(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(requestsCustomizer -> requestsCustomizer.anyRequest().permitAll());
    }

    private void setAuthenticationPolicy(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(
                requestsCustomizer -> {
                    requestsCustomizer
                            .requestMatchers(getAuthPath())
                            .permitAll();

                    requestsCustomizer
                            .anyRequest()
                            .authenticated();

                    requestsCustomizer
                            .requestMatchers(getGroupPath(SETTINGS))
                            .hasRole(Role.ADMIN.name());

                    requestsCustomizer
                            .requestMatchers(HttpMethod.GET, getReadingPathsArray(DOCUMENT))
                            .hasAuthority(Permission.DOCUMENT_READ.getSubject())

                            .requestMatchers(HttpMethod.POST, getGroupPath(DOCUMENT))
                            .hasAuthority(Permission.DOCUMENT_WRITE.getSubject())

                            .requestMatchers(HttpMethod.DELETE, getGroupPath(DOCUMENT))
                            .hasAuthority(Permission.DOCUMENT_WRITE.getSubject());

                    requestsCustomizer
                            .requestMatchers(HttpMethod.GET, getReadingPathsArray(FILE))
                            .hasAuthority(Permission.FILE_READ.getSubject())

                            .requestMatchers(HttpMethod.POST, getGroupPath(FILE))
                            .hasAuthority(Permission.FILE_WRITE.getSubject())

                            .requestMatchers(HttpMethod.DELETE, getGroupPath(FILE))
                            .hasAuthority(Permission.FILE_WRITE.getSubject());

                    requestsCustomizer
                            .requestMatchers(HttpMethod.GET, getReadingPathsArray(USER))
                            .hasAuthority(Permission.USER_READ.getSubject())

                            .requestMatchers(HttpMethod.POST, getGroupPath(USER))
                            .hasAuthority(Permission.USER_WRITE.getSubject())

                            .requestMatchers(HttpMethod.DELETE, getGroupPath(USER))
                            .hasAuthority(Permission.USER_WRITE.getSubject());
                }
        );
    }
}

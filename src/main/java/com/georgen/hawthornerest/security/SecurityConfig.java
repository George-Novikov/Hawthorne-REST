package com.georgen.hawthornerest.security;

import com.georgen.hawthornerest.model.users.Permission;
import com.georgen.hawthornerest.model.users.Role;
import com.georgen.hawthornerest.services.SettingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
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

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        boolean isAuthRequired = settingsService.get().isAuthRequired();

        return httpSecurity
                .csrf(securityCustomizer -> securityCustomizer.disable())
                .authorizeHttpRequests(
                        requestsCustomizer -> {
                            requestsCustomizer.requestMatchers("/api/v1/auth/**").permitAll();

                            if (isAuthRequired){
                                requestsCustomizer.anyRequest().authenticated();
                            }

                            requestsCustomizer
                                    .requestMatchers(HttpMethod.GET, "/api/v1/document", "/api/v1/document/list", "/api/v1/document/count")
                                    .hasRole(Permission.DOCUMENT_READ.getSubject())

                                    .requestMatchers(HttpMethod.POST, "/api/v1/document").
                                    hasRole(Permission.DOCUMENT_WRITE.getSubject())

                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/document")
                                    .hasRole(Permission.DOCUMENT_WRITE.getSubject());

                            requestsCustomizer
                                    .requestMatchers(HttpMethod.GET, "/api/v1/file", "/api/v1/file/list", "/api/v1/file/count")
                                    .hasRole(Permission.FILE_READ.getSubject())

                                    .requestMatchers(HttpMethod.POST, "/api/v1/file")
                                    .hasRole(Permission.FILE_WRITE.getSubject())

                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/file")
                                    .hasRole(Permission.FILE_WRITE.getSubject());

                            requestsCustomizer
                                    .requestMatchers(HttpMethod.GET, "/api/v1/user", "/api/v1/user/list", "/api/v1/user/count")
                                    .hasRole(Permission.USER_READ.getSubject())

                                    .requestMatchers(HttpMethod.POST, "/api/v1/user")
                                    .hasRole(Permission.USER_WRITE.getSubject())

                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/user")
                                    .hasRole(Permission.USER_WRITE.getSubject());

                        }
                )
                .sessionManagement(
                        sessionCustomizer -> sessionCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

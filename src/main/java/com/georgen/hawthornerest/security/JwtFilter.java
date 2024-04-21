package com.georgen.hawthornerest.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private JwtHandler jwtHandler;
    private UserDetailsService userDetailsService;
    private HandlerExceptionResolver resolver;
    public JwtFilter(JwtHandler jwtHandler,
                     UserDetailsService userDetailsService,
                     @Qualifier("authExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtHandler = jwtHandler;
        this.userDetailsService = userDetailsService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String jwtToken;
            final String userLogin;

            if (authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            jwtToken = authHeader.substring(7);
            userLogin = jwtHandler.extractLogin(jwtToken);

            if (userLogin != null && !isAuthenticated()){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userLogin);
                if (userDetails != null && jwtHandler.isTokenValid(jwtToken, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e){
            response.sendError(HttpStatus.FORBIDDEN.value(), "The access token has expired.");
        }
    }

    private boolean isAuthenticated(){
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}

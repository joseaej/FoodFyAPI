package com.FoodFyAPI.FoodFyAPI.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new SupabaseTokenFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    public static class SupabaseTokenFilter extends BasicAuthenticationFilter {
        public SupabaseTokenFilter() {
            super((AuthenticationManager) authentication -> authentication);
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain) throws IOException, ServletException {

            String path = request.getRequestURI();

            if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
                chain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Invalid token.");
                return;
            }

            String token = authHeader.substring(7);

            if (token.isBlank()) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("Empty token.");
                return;
            }

            Authentication auth = new UsernamePasswordAuthenticationToken("supabase-user", null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(request, response);
        }

    }
}

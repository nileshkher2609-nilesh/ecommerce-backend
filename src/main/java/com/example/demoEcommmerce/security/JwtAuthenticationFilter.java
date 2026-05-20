package com.example.demoEcommmerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demoEcommmerce.jwt.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {
//OncePerRequestFilter -> Run this filter once for every request
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService) {

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");
//request.getHeader -> Read Authorization Header
        String jwt = null;
        String email = null;

        // Check Bearer token
        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            jwt = authHeader.substring(7);
// authHeader.substring(7) -> Extract JWT and remove bearer

            email = jwtUtil.extractUsername(jwt);
// extracting email from payload.
        }

        // Authenticate user
        if (email != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(email);
            System.out.println(userDetails.getAuthorities());
// Fetched user from user DB.

            if (jwtUtil.validateToken(jwt,
                    userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                
//Represents authenticated user.

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
                
                System.out.println(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getAuthorities()
                );
//  SecurityContextHolder.getContext() -> You are telling spring this user is authenticated.
            }
        }
        System.out.println("PATH: " + request.getServletPath());

        System.out.println(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );
        filterChain.doFilter(request, response);
    }
}
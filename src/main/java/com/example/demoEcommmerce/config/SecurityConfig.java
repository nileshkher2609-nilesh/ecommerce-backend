package com.example.demoEcommmerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import com.example.demoEcommmerce.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(
                            SessionCreationPolicy.STATELESS
                    )
            )
            

            .authorizeHttpRequests(auth -> auth
            		
            		 .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

            	    // Public APIs
            	    .requestMatchers(
            	            "/api/auth/**"
            	    ).permitAll()

            	    // Product APIs
            	    .requestMatchers(HttpMethod.GET,
            	            "/api/products/**")
            	    .permitAll()
            	    
            	    .requestMatchers(
            	            HttpMethod.GET,
            	            "/api/orders/**"
            	    ).authenticated()

            	    // ADMIN ONLY
            	    .requestMatchers(HttpMethod.POST,
            	            "/api/products")
            	    .hasAuthority("ADMIN")

            	    .requestMatchers(HttpMethod.PUT,
            	            "/api/products/**")
            	    .hasAuthority("ADMIN")

            	    .requestMatchers(HttpMethod.DELETE,
            	            "/api/products/**")
            	    .hasAuthority("ADMIN")

            	    // Everything else requires login
            	    .anyRequest().authenticated()
            	);
//            .addFilterBefore(
//                    jwtAuthenticationFilter,
//                    UsernamePasswordAuthenticationFilter.class
//            );


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
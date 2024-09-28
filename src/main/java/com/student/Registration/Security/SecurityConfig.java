package com.student.Registration.Security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF (for stateless APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // Public endpoints
                        .requestMatchers("/api/courses/**").permitAll() // Secure all course endpoints
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .httpBasic(withDefaults()) // Use the new method for HTTP Basic auth
                .anonymous(anonymous -> anonymous // Configure anonymous access
                        .key("anonymousKey") // Optional: Set a key for anonymous authentication
                        .authenticationFilter(new AnonymousAuthenticationFilter("anonymousKey"))
                );

        return http.build();
    }
}




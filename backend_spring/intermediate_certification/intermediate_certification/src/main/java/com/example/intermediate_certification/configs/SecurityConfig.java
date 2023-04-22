package com.example.intermediate_certification.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
            .requestMatchers("*/admin/**").hasRole("ADMIN")
            .requestMatchers("/cart/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/order/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/**").permitAll()
        ;
        return httpSecurity.build();
    }
}

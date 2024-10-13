//package com.example.demo.filemanager.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity  // Ensure custom security is enabled
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Disable CSRF (use only if you're building a stateless API)
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // Authorization rules
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/auth/signup", "/auth/login").permitAll()  // Allow access to signup/login without authentication
//                        .anyRequest().authenticated()  // All other requests need authentication
//                )
//
//                // Disable HTTP Basic authentication, assuming you're handling your own login
//                .httpBasic(httpBasicConfigurer -> {});  // Optional: you can skip this if you don't need basic auth
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Password encoder for secure password storage
//    }
//}

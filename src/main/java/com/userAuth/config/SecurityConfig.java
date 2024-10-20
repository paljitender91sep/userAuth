package com.userAuth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configure HTTP security for authorization and authentication
        http
                .authorizeHttpRequests()
                .requestMatchers("/", "/login**", "/error").permitAll() // Allow public access to these endpoints
                .anyRequest().authenticated() // All other requests require authentication
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome", true)
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome", true)
                .and()
                .logout().permitAll();

        return http.build();
    }

    // Configure in-memory authentication
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user = User.withUsername("admin")
                .password("{noop}admin") // "{noop}" indicates that the password is stored in plain text (not recommended for production)
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

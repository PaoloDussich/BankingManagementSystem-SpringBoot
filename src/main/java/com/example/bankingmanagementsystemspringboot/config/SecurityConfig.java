package com.example.bankingmanagementsystemspringboot.config;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpServletResponse httpServletResponse) throws Exception{

        http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.ignoringRequestMatchers("/api/v1/users/login")).authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll().anyRequest().authenticated()).formLogin(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable).exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)));

        return http.build();
    }

}

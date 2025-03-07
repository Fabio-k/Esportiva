package org.fatec.esportiva.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/login").permitAll()
                                                .requestMatchers("/images/**").permitAll()
                                                .requestMatchers("/js/**").permitAll()
                                                .requestMatchers("/css/**").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/login/auth").permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/h2-console/**").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(login -> login
                                                .loginPage("/login")
                                                .permitAll())
                                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/login"))
                                .build();
        }
}

package com.maxdlr.p13.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity()
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(
        cors -> cors.configurationSource(
            _ -> {
              var corsConfiguration = new CorsConfiguration();
              corsConfiguration.setAllowedOriginPatterns(List.of("*"));
              corsConfiguration.setAllowedMethods(
                  List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
              corsConfiguration.setAllowedHeaders(List.of("Content-Type", "Accept"));
              corsConfiguration.setAllowCredentials(true);
              return corsConfiguration;
            }))
        .csrf(csrf -> csrf.ignoringRequestMatchers("/ws/**", "/api/**", "/graph**"))
        .authorizeHttpRequests(
            auth -> auth.anyRequest().permitAll());
    return http.build();
  }
}

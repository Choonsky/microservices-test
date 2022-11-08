package com.nemirovsky.reviewservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/review/*").permitAll()
                .antMatchers(HttpMethod.POST, "/review").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/review/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/review/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
        return http.build();
    }

}
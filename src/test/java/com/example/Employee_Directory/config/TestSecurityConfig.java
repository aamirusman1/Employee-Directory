package com.example.Employee_Directory.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Profile("test")
//@TestConfiguration
public class TestSecurityConfig {

//    @Bean
//    @Primary
//    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
}

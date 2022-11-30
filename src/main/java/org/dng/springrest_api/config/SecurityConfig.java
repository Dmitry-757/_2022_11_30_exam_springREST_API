package org.dng.springrest_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        List<UserDetails> ul = List.of(
                User.builder()
                        .username("user")
                        .password(bCryptPasswordEncoder.encode("123"))
                        .roles("USER")
                        .build(),
                User.builder()
                        .username("Admin")
                        .password(bCryptPasswordEncoder.encode("321"))
                        .roles("ADMIN")
                        .build()
        );
        return new InMemoryUserDetailsManager(ul);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests() //Это строкой мы говорим предоставить разрешения для следующих url.
//                .requestMatchers("/", "/resources/**").permitAll() //сюда можно всем
//                .anyRequest().hasRole("USER")
//               .and()
//                .csrf()
//                .disable()
//                ;
//        return http.build();
        http
                .csrf().disable()
                .authorizeHttpRequests().anyRequest().authenticated()
                .and().httpBasic();
//                .and().sessionManagement().disable();
        return http.build();
    }

}

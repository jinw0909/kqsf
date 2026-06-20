package com.kqsf.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        // static
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/assets/**",
                                "/site/**",
                                "/favicon.ico",
                                "/favicon-16x16.png",
                                "/favicon-32x32.png",
                                "/apple-touch-icon.png",
                                "/android-chrome-192x192.png",
                                "/android-chrome-512x512.png",
                                "/site.webmanifest",
                                "/robots.txt",
                                "/sitemap.xml"
                        ).permitAll()

                        // public pages
                        .requestMatchers(
                                "/",
                                "/products/**",
                                "/technology/**",
                                "/vision/**",
                                "/contact/**"
                        ).permitAll()

                        // auth pages
                        .requestMatchers(
                                "/login",
                                "/error"
                        ).permitAll()

                        // admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // default
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(adminLoginSuccessHandler())
                        .failureUrl("/login?error")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )

//                .csrf(csrf -> csrf
//                        // Thymeleaf form에서는 csrf token 넣는 게 원칙이라 보통 disable 안 함.
//                        // 외부 API 테스트용 endpoint가 생기면 그때만 ignoringRequestMatchers 추가.
//                        // .ignoringRequestMatchers("/api/**")
//                )

                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler adminLoginSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/admin");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
}

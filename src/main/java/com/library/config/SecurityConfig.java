package com.library.config;

import com.library.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (WebSecurity web) ->
                web.ignoring()
                        .requestMatchers("/uploads/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        return http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // PUBLIC PAGES
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/register.html",

                                "/dashboard.html",
                                "/librarian-dashboard.html",
                                "/member-dashboard.html",

                                "/books.html",
                                "/book-details.html",
                                "/edit-book.html",

                                "/users.html",
                                "/user-details.html",

                                "/categories.html",

                                "/issue-book.html",
                                "/return-book.html",
                                "/issue-history.html",

                                "/reserve-book.html",
                                "/reservation-list.html",

                                "/fine-payment.html",
                                "/fines.html",
                                "/qr-payment.html",
                                "/edit-user.html",
                                "/myfine.html",
                                "/myhistory.html",
                                "/member-book-reserve.html",

                                "/css/**",
                                "/js/**",

                                "/api/auth/**"
                        )
                        .permitAll()

                        // QR IMAGE PUBLIC
                        .requestMatchers(
                                "/api/fines/qr/**"
                        )
                        .permitAll()

                        // DASHBOARD
                        .requestMatchers(
                                "/api/dashboard",
                                "/api/users/dashboard"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN"
                        )

                        // USERS
                        .requestMatchers(
                                "/api/users/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN"
                        )

                        // CATEGORIES
                        .requestMatchers(
                                "/api/categories/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN"
                        )

                        // BOOKS
                        .requestMatchers(
                                "/api/books/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN",
                                "MEMBER"
                        )

                        // ISSUE / RETURN
                        .requestMatchers(
                                "/api/issues/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN",
                                "MEMBER"
                        )

                        // FINES
                        .requestMatchers(
                                "/api/fines/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN",
                                "MEMBER"
                        )

                        // RESERVATIONS
                        .requestMatchers(
                                "/api/reservations/**"
                        )
                        .hasAnyRole(
                                "ADMIN",
                                "LIBRARIAN",
                                "MEMBER"
                        )



                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }
}
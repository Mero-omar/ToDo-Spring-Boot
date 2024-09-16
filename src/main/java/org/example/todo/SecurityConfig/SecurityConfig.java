package org.example.todo.SecurityConfig;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.http.HttpRequest;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthfilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChin(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register","/auth/login")
                        .permitAll()  // Public routes
                        .anyRequest().
                        authenticated()  // All other routes need authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session for JWT
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthfilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();

    }
}

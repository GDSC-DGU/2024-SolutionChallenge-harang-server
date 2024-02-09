package org.harang.server.security.config;

import lombok.RequiredArgsConstructor;
import org.harang.server.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable).formLogin(AbstractHttpConfigurer::disable)

                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                // TODO: 이후에 구현되는 로그인 api 엔드포인트랑 동일한지 확인
                                .requestMatchers("/v1/login").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

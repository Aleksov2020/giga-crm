package com.yeel.giga.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf(AbstractHttpConfigurer::disable) // Отключение CSRF
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register").permitAll() // Разрешение доступа к этим маршрутам
                .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
        )
        .formLogin(login -> login
                .loginPage("/login") // Страница логина
                .loginProcessingUrl("/login") // URL для обработки логина
                .defaultSuccessUrl("/home", true) // Перенаправление после успешного логина
                .failureUrl("/login?error=true") // Перенаправление после ошибки логина
        )
        .logout(logout -> logout
                .logoutUrl("/logout") // URL для выхода
                .logoutSuccessUrl("/login") // Перенаправление после выхода
                .invalidateHttpSession(true) // Инвалидация сессии
                .deleteCookies("JSESSIONID") // Удаление куки после выхода
        ).build();
    }
}

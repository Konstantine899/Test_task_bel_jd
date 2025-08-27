package com.recordsapp.records_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/images/**","/webjars/**").permitAll()
                .requestMatchers("/", "/home", "/public/**").permitAll()
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Все остальные запросы требуют аутентификации
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Указываем страницу логина
                .loginProcessingUrl("/login") // URL, на который отправляется форма логина (обрабатывается Spring Security)
                .defaultSuccessUrl("/dashboard", true) // Перенаправление после успешного входа
                .failureUrl("/login?error=true") // Перенаправление при ошибке входа
                .permitAll() // Разрешаем всем доступ к странице логина
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL для выхода
                .logoutSuccessUrl("/login?logout=true") // Перенаправление после выхода
                .invalidateHttpSession(true) // Аннулировать сессию
                .deleteCookies("JSESSIONID") // Удалить куки сессии
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
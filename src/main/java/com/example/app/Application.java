package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Включаем поддержку аудитинга (для полей created_at/updated_at)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

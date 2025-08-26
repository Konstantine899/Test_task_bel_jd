package com.recordsapp.records_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Включаем поддержку аудитинга (для полей created_at/updated_at)
public class RecordsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordsAppApplication.class, args);
	}

}

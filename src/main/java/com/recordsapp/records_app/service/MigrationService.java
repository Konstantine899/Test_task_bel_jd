package com.recordsapp.records_app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MigrationService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    	@Override
	@Transactional
	public void run(String... args) throws Exception {
		logger.info("Начинаем выполнение миграций...");
		
		try {
			// Сначала проверяем, есть ли таблицы
			if (!tablesExist()) {
				logger.info("Таблицы не найдены. Hibernate создаст их автоматически.");
				// Hibernate создаст таблицы благодаря ddl-auto=create-drop
				// Ждем немного, чтобы Hibernate завершил создание
				Thread.sleep(2000);
			} else {
				logger.info("Таблицы уже существуют, пропускаем создание.");
			}
			
			// Выполняем миграцию V2: заполнение тестовыми данными
			executeMigration("db/migration/V2__fill_test_data.sql", "V2");
			
			logger.info("Все миграции выполнены успешно");
			
		} catch (Exception e) {
			logger.error("Ошибка при выполнении миграций: {}", e.getMessage(), e);
			throw e;
		}
	}

	private boolean tablesExist() {
		try {
			// Проверяем существование таблицы users
			String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'users'";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
			return count != null && count > 0;
		} catch (Exception e) {
			logger.debug("Таблицы не существуют: {}", e.getMessage());
			return false;
		}
	}

    private void executeMigration(String resourcePath, String migrationName) {
        try {
            logger.info("Выполняем миграцию {}: {}", migrationName, resourcePath);
            
            // Читаем SQL скрипт из ресурсов
            ClassPathResource resource = new ClassPathResource(resourcePath);
            String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            
            // Выполняем SQL скрипт
            jdbcTemplate.execute(sql);
            
            logger.info("Миграция {} выполнена успешно", migrationName);
            
        } catch (Exception e) {
            logger.error("Ошибка при выполнении миграции {}: {}", migrationName, e.getMessage(), e);
            throw new RuntimeException("Не удалось выполнить миграцию " + migrationName, e);
        }
    }
}

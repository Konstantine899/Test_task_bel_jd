-- START: V2__fill_test_data.sql
-- ==============================================
-- V2__fill_test_data.sql
-- Заполнение тестовыми данными только если таблицы пусты
-- ==============================================

DO $$
DECLARE
    user_count INTEGER;
    record_count INTEGER;
    encrypted_password TEXT := '$2a$10$r8V6L5s2q1W9T3Y7U2VZb.ZZ8X3Y2V1W4R5T6Y7U8I9O0P'; -- password123
BEGIN
    -- Проверяем количество пользователей и записей
    SELECT COUNT(*) INTO user_count FROM users;
    SELECT COUNT(*) INTO record_count FROM records;
    
    RAISE NOTICE 'Проверка данных: пользователей = %, записей = %', user_count, record_count;
    
    -- Заполняем тестовыми данными только если таблицы пусты
    IF user_count = 0 THEN
        RAISE NOTICE 'Таблица users пуста. Добавляем тестовых пользователей...';
        
        -- Вставляем тестовых пользователей
        INSERT INTO users (username, password, email, created_at) VALUES
        ('иван_петров', encrypted_password, 'иван_петров@example.com', NOW() - INTERVAL '30 days'),
        ('мария_сидорова', encrypted_password, 'мария_сидорова@example.com', NOW() - INTERVAL '25 days'),
        ('сергей_иванов', encrypted_password, 'сергей_иванов@example.com', NOW() - INTERVAL '20 days'),
        ('анна_кузнецова', encrypted_password, 'анна_кузнецова@example.com', NOW() - INTERVAL '15 days'),
        ('дмитрий_смирнов', encrypted_password, 'дмитрий_смирнов@example.com', NOW() - INTERVAL '10 days');
        
        RAISE NOTICE 'Тестовые пользователи добавлены успешно';
    ELSE
        RAISE NOTICE 'В таблице users уже есть данные (% пользователей), пропускаем добавление', user_count;
    END IF;
    
    -- Заполняем тестовыми записями только если таблица записей пуста
    IF record_count = 0 THEN
        RAISE NOTICE 'Таблица records пуста. Добавляем тестовые записи...';
        
        -- Получаем ID пользователей для создания записей
        DECLARE
            ivan_id BIGINT;
            maria_id BIGINT;
        BEGIN
            SELECT id INTO ivan_id FROM users WHERE username = 'иван_петров';
            SELECT id INTO maria_id FROM users WHERE username = 'мария_сидорова';
            
            -- Создаем тестовые записи для ивана_петрова
            IF ivan_id IS NOT NULL THEN
                INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at) VALUES
                ('Мой отпуск в горах', 'Замечательный отпуск, проведенный в Карпатах. Чистый воздух, красивые пейзажи и отличная компания. Обязательно вернусь снова!', 7500, NOW() - INTERVAL '15 days', 'mountains.jpg', ivan_id, NOW() - INTERVAL '14 days', NOW() - INTERVAL '14 days'),
                ('Рабочие задачи на неделю', 'План задач на текущую неделю: завершить проект, провести встречи с командой, подготовить отчет для руководства.', 8, NOW() - INTERVAL '7 days', NULL, ivan_id, NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days'),
                ('Идеи для нового проекта', 'Интересные идеи для разработки мобильного приложения: трекер привычек, планировщик задач, система учета финансов.', 150000, NOW() - INTERVAL '3 days', 'ideas.png', ivan_id, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days');
                
                RAISE NOTICE 'Записи для ивана_петрова добавлены';
            END IF;
            
            -- Создаем тестовые записи для марии_сидоровой
            IF maria_id IS NOT NULL THEN
                INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at) VALUES
                ('Посещение художественной выставки', 'Была на выставке современного искусства. Очень впечатлили работы молодых художников, особенно инсталляции.', 2500, NOW() - INTERVAL '12 days', 'exhibition.jpg', maria_id, NOW() - INTERVAL '11 days', NOW() - INTERVAL '11 days'),
                ('Кулинарные эксперименты', 'Приготовила новый десерт - тирамису. Получилось не хуже, чем в итальянском ресторане!', 1200, NOW() - INTERVAL '8 days', 'tiramisu.jpg', maria_id, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days');
                
                RAISE NOTICE 'Записи для марии_сидоровой добавлены';
            END IF;
        END;
        
        RAISE NOTICE 'Тестовые записи добавлены успешно';
    ELSE
        RAISE NOTICE 'В таблице records уже есть данные (% записей), пропускаем добавление', record_count;
    END IF;
    
    RAISE NOTICE 'Миграция V2 завершена успешно';
END $$;
-- END: V2__fill_test_data.sql

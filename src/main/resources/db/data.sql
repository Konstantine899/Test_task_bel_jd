-- ==============================================
-- data.sql - Заполнение тестовыми данными
-- ОБНОВЛЕНО: Данные взяты из файла user_data
-- ==============================================

-- Вставляем тестовых пользователей (только если таблица пуста)
-- BCrypt хеши взяты из реального файла user_data
-- ИСПРАВЛЕНО: Упрощен формат timestamp, убрана кириллица в username
INSERT INTO users (username, password, email, created_at) 
SELECT 'ivan_petrov', '$2a$10$ZBHPHnRwNoKmy.OZdit0leoZwBFqMqAj9K2F08HNshkKt4ul4NOx6', 'ivan_petrov@example.com', CURRENT_TIMESTAMP - INTERVAL '30 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'ivan_petrov');

INSERT INTO users (username, password, email, created_at) 
SELECT 'maria_sidorova', '$2a$10$Y7IbSy4gTEsUElFoMnoVkeCZfkf1WtxQ40uVock.JMd4vSYIMyD82', 'maria_sidorova@example.com', CURRENT_TIMESTAMP - INTERVAL '25 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'maria_sidorova');

INSERT INTO users (username, password, email, created_at) 
SELECT 'sergey_ivanov', '$2a$10$nrqIwH9AY7O/f/jQT78CiejGcgFaVz.l83p/d0LEchYZegrxy10BO', 'sergey_ivanov@example.com', CURRENT_TIMESTAMP - INTERVAL '20 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'sergey_ivanov');

INSERT INTO users (username, password, email, created_at) 
SELECT 'anna_kuznecova', '$2a$10$qw2jsX0lePdcHFuP43jsGedc5bH4FpSRBEQWSS8BQkvg6v1aTAtUS', 'anna_kuznecova@example.com', CURRENT_TIMESTAMP - INTERVAL '15 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'anna_kuznecova');

INSERT INTO users (username, password, email, created_at) 
SELECT 'dmitriy_smirnov', '$2a$10$nqkLLcKX20nL45oIsnp1fuHLpr1Ir5iwE39etOJRbt3KFyb6iBTqW', 'dmitriy_smirnov@example.com', CURRENT_TIMESTAMP - INTERVAL '10 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'dmitriy_smirnov');

-- Вставляем тестовые записи (только если таблица записей пуста)
INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Мой отпуск в горах', 'Замечательный отпуск в Карпатах. Чистый воздух, красивые пейзажи!', 7500, NOW() - INTERVAL '15 days', 'mountains.jpg', u.id, NOW() - INTERVAL '14 days', NOW() - INTERVAL '14 days'
FROM users u WHERE u.username = 'ivan_petrov' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Мой отпуск в горах');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Рабочие задачи на неделю', 'План задач: завершить проект, провести встречи, подготовить отчет.', 8, NOW() - INTERVAL '7 days', NULL, u.id, NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days'
FROM users u WHERE u.username = 'ivan_petrov' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Рабочие задачи на неделю');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Идеи для нового проекта', 'Интересные идеи: трекер привычек, планировщик задач, система учета финансов.', 150000, NOW() - INTERVAL '3 days', 'ideas.png', u.id, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'
FROM users u WHERE u.username = 'ivan_petrov' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Идеи для нового проекта');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Посещение художественной выставки', 'Была на выставке современного искусства. Очень впечатлили работы молодых художников.', 2500, NOW() - INTERVAL '12 days', 'exhibition.jpg', u.id, NOW() - INTERVAL '11 days', NOW() - INTERVAL '11 days'
FROM users u WHERE u.username = 'maria_sidorova' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Посещение художественной выставки');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Кулинарные эксперименты', 'Приготовила новый десерт - тирамису. Получилось не хуже, чем в итальянском ресторане!', 1200, NOW() - INTERVAL '8 days', 'tiramisu.jpg', u.id, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'
FROM users u WHERE u.username = 'maria_sidorova' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Кулинарные эксперименты');

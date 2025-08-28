-- ==============================================
-- data.sql - Заполнение тестовыми данными
-- ==============================================

-- Вставляем тестовых пользователей (только если таблица пуста)
INSERT INTO users (username, password, email, created_at) 
SELECT 'иван_петров', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'иван_петров@example.com', NOW() - INTERVAL '30 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'иван_петров');

INSERT INTO users (username, password, email, created_at) 
SELECT 'мария_сидорова', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'мария_сидорова@example.com', NOW() - INTERVAL '25 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'мария_сидорова');

INSERT INTO users (username, password, email, created_at) 
SELECT 'сергей_иванов', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'сергей_иванов@example.com', NOW() - INTERVAL '20 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'сергей_иванов');

INSERT INTO users (username, password, email, created_at) 
SELECT 'анна_кузнецова', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'анна_кузнецова@example.com', NOW() - INTERVAL '15 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'анна_кузнецова');

INSERT INTO users (username, password, email, created_at) 
SELECT 'дмитрий_смирнов', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'дмитрий_смирнов@example.com', NOW() - INTERVAL '10 days'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'дмитрий_смирнов');

-- Вставляем тестовые записи (только если таблица записей пуста)
INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Мой отпуск в горах', 'Замечательный отпуск в Карпатах. Чистый воздух, красивые пейзажи!', 7500, NOW() - INTERVAL '15 days', 'mountains.jpg', u.id, NOW() - INTERVAL '14 days', NOW() - INTERVAL '14 days'
FROM users u WHERE u.username = 'иван_петров' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Мой отпуск в горах');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Рабочие задачи на неделю', 'План задач: завершить проект, провести встречи, подготовить отчет.', 8, NOW() - INTERVAL '7 days', NULL, u.id, NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days'
FROM users u WHERE u.username = 'иван_петров' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Рабочие задачи на неделю');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Идеи для нового проекта', 'Интересные идеи: трекер привычек, планировщик задач, система учета финансов.', 150000, NOW() - INTERVAL '3 days', 'ideas.png', u.id, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'
FROM users u WHERE u.username = 'иван_петров' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Идеи для нового проекта');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Посещение художественной выставки', 'Была на выставке современного искусства. Очень впечатлили работы молодых художников.', 2500, NOW() - INTERVAL '12 days', 'exhibition.jpg', u.id, NOW() - INTERVAL '11 days', NOW() - INTERVAL '11 days'
FROM users u WHERE u.username = 'мария_сидорова' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Посещение художественной выставки');

INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at)
SELECT 'Кулинарные эксперименты', 'Приготовила новый десерт - тирамису. Получилось не хуже, чем в итальянском ресторане!', 1200, NOW() - INTERVAL '8 days', 'tiramisu.jpg', u.id, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'
FROM users u WHERE u.username = 'мария_сидорова' AND NOT EXISTS (SELECT 1 FROM records WHERE title = 'Кулинарные эксперименты');

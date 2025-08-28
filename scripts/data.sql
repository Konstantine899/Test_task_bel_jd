-- SQL скрипт для генерации тестовых данных для приложения Records App
-- Поддерживает PostgreSQL и создает реалистичные данные на русском языке

BEGIN TRANSACTION;

-- Очистка существующих данных (раскомментировать при необходимости)
-- DELETE FROM records;
-- DELETE FROM users;

-- 1. Создание тестовых пользователей
INSERT INTO users (username, password, email, created_at) VALUES
('иван_петров', '$$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'иван_петров@example.com', NOW() - INTERVAL '30 days'),
('мария_сидорова', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'мария_сидорова@example.com', NOW() - INTERVAL '25 days'),
('сергей_иванов', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'сергей_иванов@example.com', NOW() - INTERVAL '20 days'),
('анна_кузнецова', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'анна_кузнецова@example.com', NOW() - INTERVAL '15 days'),
('дмитрий_смирнов', '$2a$10$b7c1PwqWtS4gXw4NUo0teOcW5OUP0C7L6SshsJr6sVBXjDMdR.9eW', 'дмитрий_смирнов@example.com', NOW() - INTERVAL '10 days');

-- 2. Создание записей для каждого пользователя
-- Записи для иван_петров (5 записей)
INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at) VALUES
('Мой отпуск в горах', 'Замечательный отпуск, проведенный в Карпатах. Чистый воздух, красивые пейзажи и отличная компания. Обязательно вернусь снова!', 7500, NOW() - INTERVAL '15 days', 'mountains.jpg', 1, NOW() - INTERVAL '14 days', NOW() - INTERVAL '14 days'),
('Рабочие задачи на неделю', 'План задач на текущую неделю: завершить проект, провести встречи с командой, подготовить отчет для руководства.', 8, NOW() - INTERVAL '7 days', NULL, 1, NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days'),
('Идеи для нового проекта', 'Интересные идеи для разработки мобильного приложения: трекер привычек, планировщик задач, система учета финансов.', 150000, NOW() - INTERVAL '3 days', 'ideas.png', 1, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days'),
('Рецепт домашнего хлеба', 'Попробовал новый рецепт домашнего хлеба. Получилось очень вкусно! Мука, вода, соль, дрожжи и немного любви.', 500, NOW() - INTERVAL '10 days', 'bread.jpg', 1, NOW() - INTERVAL '9 days', NOW() - INTERVAL '9 days'),
('Спортивные достижения', 'Пробежал 10 км за 45 минут. Новый личный рекорд! Постепенно улучшаю результаты.', 10000, NOW() - INTERVAL '5 days', NULL, 1, NOW() - INTERVAL '4 days', NOW() - INTERVAL '4 days');

-- Записи для мария_сидорова (6 записей)
INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at) VALUES
('Посещение художественной выставки', 'Была на выставке современного искусства. Очень впечатлили работы молодых художников, особенно инсталляции.', 2500, NOW() - INTERVAL '12 days', 'exhibition.jpg', 2, NOW() - INTERVAL '11 days', NOW() - INTERVAL '11 days'),
('Кулинарные эксперименты', 'Приготовила новый десерт - тирамису. Получилось не хуже, чем в итальянском ресторане!', 1200, NOW() - INTERVAL '8 days', 'tiramisu.jpg', 2, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'),
('Планы на выходные', 'На выходные запланировала поездку за город, чтение новой книги и встречу с друзьями.', 3, NOW() - INTERVAL '4 days', NULL, 2, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days'),
('Изучение английского языка', 'Начала заниматься с новым преподавателем. Уже вижу прогресс в разговорной речи.', 45, NOW() - INTERVAL '20 days', NULL, 2, NOW() - INTERVAL '19 days', NOW() - INTERVAL '19 days'),
('Фотографии природы', 'Сделала серию фотографий осеннего парка. Листья такие красивые золотые и красные!', 780, NOW() - INTERVAL '6 days', 'autumn.jpg', 2, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days'),
('Книжные рекомендации', 'Прочитала интересную книгу по психологии. Очень рекомендую всем, кто интересуется саморазвитием.', 1, NOW() - INTERVAL '2 days', NULL, 2, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day');

-- Записи для сергей_иванов (4 записи)
INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at) VALUES
('Ремонт в квартире', 'Завершил ремонт в гостиной. Теперь комната выглядит современно и уютно. Осталось только повесить полки.', 50000, NOW() - INTERVAL '18 days', 'renovation.jpg', 3, NOW() - INTERVAL '17 days', NOW() - INTERVAL '17 days'),
('IT конференция', 'Посетил ежегодную IT конференцию. Много интересных докладов по новым технологиям и трендам разработки.', 1500, NOW() - INTERVAL '5 days', 'conference.png', 3, NOW() - INTERVAL '4 days', NOW() - INTERVAL '4 days'),
('Программирование на Java', 'Изучаю новые возможности Java 17. Очень нравятся sealed classes и pattern matching.', 17, NOW() - INTERVAL '10 days', NULL, 3, NOW() - INTERVAL '9 days', NOW() - INTERVAL '9 days'),
('Велосипедная прогулка', 'Проехал 25 км на велосипеде по живописным местам. Отличная физическая нагрузка и свежий воздух.', 25000, NOW() - INTERVAL '3 days', 'biking.jpg', 3, NOW() - INTERVAL '2 days', NOW() - INTERVAL '2 days');

-- Записи для анна_кузнецова (7 записей)
INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at) VALUES
('Йога и медитация', 'Начала практиковать йогу каждое утро. Чувствую себя более энергичной и сконцентрированной.', 30, NOW() - INTERVAL '14 days', 'yoga.jpg', 4, NOW() - INTERVAL '13 days', NOW() - INTERVAL '13 days'),
('Садоводство на балконе', 'Выращиваю зелень и цветы на балконе. Уже собрала первый урожай базилика и петрушки.', 15, NOW() - INTERVAL '20 days', 'garden.jpg', 4, NOW() - INTERVAL '19 days', NOW() - INTERVAL '19 days'),
('Вязание свитера', 'Завершила вязание теплого свитера. Отлично подойдет для холодных зимних дней.', 1200, NOW() - INTERVAL '7 days', 'sweater.jpg', 4, NOW() - INTERVAL '6 days', NOW() - INTERVAL '6 days'),
('Домашний кинотеатр', 'Посмотрели с семьей новый фильм. Отличный способ провести вечер вместе.', 5, NOW() - INTERVAL '2 days', NULL, 4, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day'),
('Благотворительность', 'Участвовала в организации благотворительного мероприятия. Собрали средства для детского приюта.', 50000, NOW() - INTERVAL '9 days', 'charity.jpg', 4, NOW() - INTERVAL '8 days', NOW() - INTERVAL '8 days'),
('Изучение испанского', 'Начала учить испанский язык. Пока сложно, но очень интересно!', 10, NOW() - INTERVAL '12 days', NULL, 4, NOW() - INTERVAL '11 days', NOW() - INTERVAL '11 days'),
('Приготовление пасты', 'Освоила новый рецепт пасты карбонара. Семье очень понравилось!', 450, NOW() - INTERVAL '4 days', 'pasta.jpg', 4, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days');

-- Записи для дмитрий_смирнов (8 записей)
INSERT INTO records (title, text, number_value, date, image_path, user_id, created_at, updated_at) VALUES
('Рыбалка на озере', 'Отлично порыбачил на озере. Поймал несколько крупных окуней и щуку. Отличный улов!', 8500, NOW() - INTERVAL '16 days', 'fishing.jpg', 5, NOW() - INTERVAL '15 days', NOW() - INTERVAL '15 days'),
('Настройка домашнего сервера', 'Настроил домашний сервер для хранения данных и медиафайлов. Теперь все организовано и доступно.', 2000, NOW() - INTERVAL '8 days', 'server.png', 5, NOW() - INTERVAL '7 days', NOW() - INTERVAL '7 days'),
('Чтение технической литературы', 'Изучаю новую книгу по архитектуре программного обеспечения. Много полезной информации для работы.', 350, NOW() - INTERVAL '11 days', NULL, 5, NOW() - INTERVAL '10 days', NOW() - INTERVAL '10 days'),
('Поход в горы', 'Совершил двухдневный поход в горы. Прекрасные виды и отличная компания.', 2, NOW() - INTERVAL '6 days', 'hiking.jpg', 5, NOW() - INTERVAL '5 days', NOW() - INTERVAL '5 days'),
('Ремонт автомобиля', 'Поменял масло и фильтры в автомобиле. Теперь машина работает как новая.', 7500, NOW() - INTERVAL '13 days', NULL, 5, NOW() - INTERVAL '12 days', NOW() - INTERVAL '12 days'),
('Игра в шахматы', 'Сыграл несколько партий в шахматы с другом. Выиграл со счетом 3:2!', 5, NOW() - INTERVAL '4 days', 'chess.png', 5, NOW() - INTERVAL '3 days', NOW() - INTERVAL '3 days'),
('Фотографирование птиц', 'Сфотографировал несколько редких видов птиц в лесу. Отличные кадры для коллекции.', 12, NOW() - INTERVAL '9 days', 'birds.jpg', 5, NOW() - INTERVAL '8 days', NOW() - INTERVAL '8 days'),
('Планирование путешествия', 'Начал планировать путешествие по Европе на следующий год. Уже составил примерный маршрут.', 150000, NOW() - INTERVAL '2 days', NULL, 5, NOW() - INTERVAL '1 day', NOW() - INTERVAL '1 day');

COMMIT;

-- Проверка созданных данных
SELECT 'Пользователи созданы: ' || COUNT(*) FROM users;
SELECT 'Записи созданы: ' || COUNT(*) FROM records;
SELECT 'Записей у пользователя 1: ' || COUNT(*) FROM records WHERE user_id = 1;
SELECT 'Записей у пользователя 2: ' || COUNT(*) FROM records WHERE user_id = 2;
SELECT 'Записей у пользователя 3: ' || COUNT(*) FROM records WHERE user_id = 3;
SELECT 'Записей у пользователя 4: ' || COUNT(*) FROM records WHERE user_id = 4;
SELECT 'Записей у пользователя 5: ' || COUNT(*) FROM records WHERE user_id = 5;
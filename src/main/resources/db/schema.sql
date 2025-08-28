-- ==============================================
-- schema.sql - Создание таблиц
-- ==============================================

-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы записей
CREATE TABLE IF NOT EXISTS records (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    text VARCHAR(1000) NOT NULL,
    number_value INTEGER NOT NULL CHECK (number_value >= 0 AND number_value <= 1000000),
    date DATE NOT NULL,
    image_path VARCHAR(255),
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Индексы для улучшения производительности
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_records_user_id ON records(user_id);
CREATE INDEX IF NOT EXISTS idx_records_date ON records(date);

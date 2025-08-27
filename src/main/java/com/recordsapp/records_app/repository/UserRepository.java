package com.recordsapp.records_app.repository;


import com.recordsapp.records_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью User в базе данных.
 * Наследует JpaRepository, предоставляя стандартные CRUD-операции.
 * Добавляем метод для поиска пользователя по имени (username).
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его имени.
     * Будет автоматически преобразован в SQL-запрос вида: SELECT * FROM users WHERE username = ?
     * 
     * @param username имя пользователя для поиска
     * @return Optional, содержащий пользователя, если он найден
     */
    Optional<User> findByUsername(String username);
}

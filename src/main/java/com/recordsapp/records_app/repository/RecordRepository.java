package com.recordsapp.records_app.repository;

import com.recordsapp.records_app.entity.Record;
import com.recordsapp.records_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Record в базе данных.
 */
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    /**
     * Находит все записи конкретного пользователя с поддержкой пагинации
     * @param user пользователь
     * @param pageable параметры пагинации
     * @return страница записей
     */
    Page<Record> findAllByUser(User user, Pageable pageable);

    /**
     * Находит запись по ID, но только если она принадлежит указанному пользователю
     * @param id ID записи
     * @param user пользователь
     * @return Optional с записью, если найдена и принадлежит пользователю
     */
    Optional<Record> findByIdAndUser(Long id, User user);
}

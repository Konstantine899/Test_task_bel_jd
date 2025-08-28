package com.recordsapp.records_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Сущность (Entity) для таблицы "records".
 * Отображает запись, созданную пользователем.
 */
@Entity
@Table(name = "records")
@EntityListeners(AuditingEntityListener.class) // Для @CreatedDate и @LastModifiedDate
@Data 
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Текст записи не может быть пустым")
    @Size(max = 1000, message = "Текст записи не должен превышать 1000 символов")
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull(message = "Число обязательно для заполнения")
    @Column(name = "number_value", nullable = false)
    @Min(value = 0, message = "Число должно быть не меньше 0")
    @Max(value = 1000000, message = "Число должно быть не больше 1000000")
    private Integer number;

    /**
     * Поле для даты записи.
     * @NotNull - валидация: поле не должно быть null.
     */
    @Column(name = "record_date")
    @NotNull(message = "Дата записи обязательна для заполнения")
    private LocalDate date;

    @Column(name = "image_path")
    private String imagePath;

    /**
     * Связь "Многие-к-Одному" с сущностью User.
     * @ManyToOne - много записей могут принадлежать одному пользователю.
     * @JoinColumn(name = "user_id") - указывает имя столбца внешнего ключа в таблице 'records'.
     * fetch = FetchType.LAZY - ленивая загрузка пользователя (подгружается по необходимости).
     * optional = false - связь обязательна (NOT NULL в БД).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Конструкторы
    public Record() {
    }

    public Record(String text, Integer number, LocalDate date) {
        this.text = text;
        this.number = number;
        this.date = date;
    }
} 

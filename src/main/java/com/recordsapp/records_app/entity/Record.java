package com.recordsapp.records_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class Record {
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название записи не может быть пустым")
    @Size(max = 255, message = "Название записи не должно превышать 255 символов")
    private String title;

    /**
     * @Lob - аннотация для больших текстовых полей.
     * В PostgreSQL маппится на тип TEXT.
     */
    @Lob
    private String description;

    @Column(name = "number_value")
    private Double numberValue;

    /**
     * Поле для даты записи.
     * @NotNull - валидация: поле не должно быть null.
     */
    @Column(name = "record_date")
    @NotNull(message = "Дата записи обязательна для заполнения")
    private LocalDate recordDate;

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

    public Record(String title, String description, Double numberValue, LocalDate recordDate) {
        this.title = title;
        this.description = description;
        this.numberValue = numberValue;
        this.recordDate = recordDate;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Double numberValue) {
        this.numberValue = numberValue;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }  
}

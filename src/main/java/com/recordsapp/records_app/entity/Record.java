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

     @NotBlank(message = "Текст записи не может быть пустым")
    @Size(max = 1000, message = "Текст записи не должен превышать 1000 символов")
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull(message = "Число обязательно для заполнения")
    @Column(name = "number_value", nullable = false)
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

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     public String getText() {
        return text;
    }
 public void setText(String text) {
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public void setDate(LocalDate date) {
    this.date = date;
}
}

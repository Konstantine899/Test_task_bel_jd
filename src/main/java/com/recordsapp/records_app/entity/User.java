package com.recordsapp.records_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class) // Включает автоматическое заполнение полей с @CreatedDate
public class User {
    /**
     * Первичный ключ.
     * @Id - указывает, что это поле является первичным ключом.
     * @GeneratedValue(strategy = GenerationType.IDENTITY) - стратегия генерации ID:
     * использует автоинкремент в PostgreSQL (SERIAL/BIGSERIAL).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     * @Column - настройки маппинга для столбца.
     * unique = true - добавляет уникальное ограничение в БД.
     * nullable = false - добавляет ограничение NOT NULL в БД.
     * @NotBlank - аннотация валидации: строка не должна быть пустой или состоять из пробелов.
     * @Size - аннотация валидации: длина строки должна быть между min и max.
     */
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя пользователя должно быть от 3 до 50 символов")
    private String username;

    /**
     * Пароль (должен храниться в зашифрованном виде, например, BCrypt).
     * nullable = false - NOT NULL в БД.
     */

    @Column(nullable = false, length = 60)
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    /**
     * Электронная почта.
     * @Email - аннотация валидации: проверяет корректность формата email.
     */

    @Email(message = "Некорректный формат email")
    private String email;

    /**
     * Дата и время создания пользователя.
     * @CreatedDate - аннотация Spring Data, автоматически устанавливает текущую дату и время при создании сущности.
     * @Column(name = "created_at") - явно указывает имя столбца в БД.
     */
     @CreatedDate
    @Column(name = "created_at", updatable = false) // updatable = false запрещает обновление этого поля
    private LocalDateTime createdAt;

      /**
     * Связь "Один-ко-Многим" с сущностью Record.
     * mappedBy = "user" - указывает, что владельцем связи является поле "user" в классе Record.
     * Это обратная сторона двунаправленной связи.
     * cascade = CascadeType.ALL - операции (сохранение, удаление и т.д.) будут каскадироваться на связанные записи.
     * fetch = FetchType.LAZY - данные записей будут подгружаться только при явном обращении к ним (для производительности).
     */

      @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Record> records = new ArrayList<>();

    // Конструкторы
    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Геттеры и сеттеры (можно заменить аннотацией Lombok @Data)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    // Вспомогательный метод для двунаправленной связи
    public void addRecord(Record record) {
        records.add(record);
        record.setUser(this);
    }

}

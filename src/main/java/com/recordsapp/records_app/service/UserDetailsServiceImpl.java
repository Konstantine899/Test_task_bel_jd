package com.recordsapp.records_app.service;


import com.recordsapp.records_app.entity.User;
import com.recordsapp.records_app.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Кастомная реализация UserDetailsService для Spring Security.
 * Загружает данные пользователя из базы данных (PostgreSQL) по его имени.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Внедрение зависимости UserRepository через конструктор
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Основной метод для загрузки пользователя по имени.
     * 
     * @param username имя пользователя, которое ввел пользователь на форме логина
     * @return UserDetails объект, который Spring Security использует для аутентификации и авторизации
     * @throws UsernameNotFoundException если пользователь с таким именем не найден в БД
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя в репозитории (БД)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));

        // ДОБАВЛЕНО: Логирование для диагностики
        System.out.println("=== ДИАГНОСТИКА АУТЕНТИФИКАЦИИ ===");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password hash: " + user.getPassword());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Created at: " + user.getCreatedAt());
        System.out.println("ID: " + user.getId());
        System.out.println("=====================================");

        // Преобразуем нашу сущность User в UserDetails, который понимает Spring Security
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) // Пароль ДОЛЖЕН быть уже зашифрован BCrypt
                .roles("USER") // Роль по умолчанию. Вы можете добавить поле 'role' в сущность User для динамических ролей.
                .build();
    }
}

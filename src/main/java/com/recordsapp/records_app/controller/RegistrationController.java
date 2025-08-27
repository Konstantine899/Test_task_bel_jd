package com.recordsapp.records_app.controller;


import com.recordsapp.records_app.entity.User;
import com.recordsapp.records_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Контроллер для обработки регистрации новых пользователей.
 */
@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Внедрение зависимостей через конструктор
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Просто отображаем форму регистрации
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String email,
                              Model model) {

        // Проверяем, нет ли уже пользователя с таким именем
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "register";
        }

        // Создаем нового пользователя
        User newUser = new User();
        newUser.setUsername(username);
        // ВАЖНО: Шифруем пароль перед сохранением (требование №3)
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);

        // Сохраняем пользователя в базу данных
        userRepository.save(newUser);

        // Перенаправляем на страницу логина с сообщением об успехе
        return "redirect:/login?registered=true";
    }
}

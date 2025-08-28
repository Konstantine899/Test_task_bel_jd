package com.recordsapp.records_app.controller;

import com.recordsapp.records_app.entity.Record;
import com.recordsapp.records_app.entity.User;
import com.recordsapp.records_app.repository.UserRepository;
import com.recordsapp.records_app.service.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(RecordController.class);


    public RecordController(RecordService recordService, UserRepository userRepository) {
        this.recordService = recordService;
        this.userRepository = userRepository;
    }

    private User getCurrentUser(UserDetails authUser) {
        if (authUser == null) {
            throw new IllegalStateException("Не удалось определить текущего пользователя");
        }
        return userRepository.findByUsername(authUser.getUsername())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден: " + authUser.getUsername()));
    }

    /**
     * Список записей с пагинацией
     */
    @GetMapping
    public String listRecords(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date,desc") String sort,
            Model model) {
        
        User user = getCurrentUser(authUser);
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && "desc".equals(sortParams[1]) ? 
            Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        Page<Record> recordsPage = recordService.getRecordsForUser(user, pageable);
        
        model.addAttribute("recordsPage", recordsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sort", sort);
        
        return "records/list";
    }

    /**
     * Форма создания новой записи
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Record record = new Record();
        record.setDate(LocalDate.now()); // Устанавливаем текущую дату по умолчанию
        model.addAttribute("record", record);
        return "records/form";
    }

    /**
     * Создание новой записи
     */
    @PostMapping("/create")
    public String createRecord(
            @AuthenticationPrincipal UserDetails authUser,
            @Valid @ModelAttribute("record") Record record,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model) {

        User user = getCurrentUser(authUser);
        logger.info("Получен запрос на создание записи от пользователя: {}", user.getUsername());
        logger.debug("Данные записи: text={}, number={}, date={}", record.getText(), record.getNumber(), record.getDate());
        
        if (bindingResult.hasErrors()) {
            // Добавляем ошибки валидации в модель для отображения в форме
           logger.warn("Ошибки валидации: {}", bindingResult.getAllErrors());
            model.addAttribute("record", record);
            return "records/form";
        }
        
        try {
            Record savedRecord = recordService.saveRecord(user, record, imageFile);
            logger.info("Запись успешно создана с ID: {}", savedRecord.getId());
            redirectAttributes.addFlashAttribute("success", "Запись успешно создана!");
            return "redirect:/records";
        } catch (Exception e) {
            logger.error("Ошибка при создании записи: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании записи: " + e.getMessage());
            model.addAttribute("record", record);
            return "records/form";
        }
    }

    /**
     * Форма редактирования записи
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        User user = getCurrentUser(authUser);
        return recordService.findRecordForUserById(user, id)
                .map(record -> {
                    model.addAttribute("record", record);
                    return "records/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Запись не найдена или у вас нет прав для редактирования");
                    return "redirect:/records";
                });
    }

    /**
     * Обновление записи
     */
    @PostMapping("/edit/{id}")
    public String updateRecord(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long id,
            @Valid @ModelAttribute("record") Record record,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        User user = getCurrentUser(authUser);
        if (bindingResult.hasErrors()) {
            // Добавляем ошибки валидации в модель для отображения в форме
            record.setId(id); // Сохраняем ID для корректного отображения формы
            model.addAttribute("record", record);
            return "records/form";
        }
        
        try {
            record.setId(id); // Убедимся, что ID установлен
            recordService.saveRecord(user, record, imageFile);
            redirectAttributes.addFlashAttribute("success", "Запись успешно обновлена!");
            return "redirect:/records";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении записи: " + e.getMessage());
            // Возвращаемся к форме с сохраненными данными
            record.setId(id);
            model.addAttribute("record", record);
            return "records/form";
        }
    }

    /**
     * Удаление записи
     */
    @PostMapping("/delete/{id}")
    public String deleteRecord(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        User user = getCurrentUser(authUser);
        try {
            recordService.deleteRecord(user, id);
            redirectAttributes.addFlashAttribute("success", "Запись успешно удалена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении записи: " + e.getMessage());
        }
        
        return "redirect:/records";
    }
}

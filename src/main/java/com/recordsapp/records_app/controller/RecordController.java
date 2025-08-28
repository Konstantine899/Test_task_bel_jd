package com.recordsapp.records_app.controller;

import com.recordsapp.records_app.entity.Record;
import com.recordsapp.records_app.entity.User;
import com.recordsapp.records_app.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    /**
     * Список записей с пагинацией
     */
    @GetMapping
    public String listRecords(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date,desc") String sort,
            Model model) {
        
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
            @AuthenticationPrincipal User user,
            @Valid @ModelAttribute("record") Record record,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            // Добавляем ошибки валидации в модель для отображения в форме
            model.addAttribute("record", record);
            return "records/form";
        }
        
        try {
            recordService.saveRecord(user, record, imageFile);
            redirectAttributes.addFlashAttribute("success", "Запись успешно создана!");
            return "redirect:/records";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании записи: " + e.getMessage());
            // Возвращаемся к форме с сохраненными данными
            model.addAttribute("record", record);
            return "records/form";
        }
    }

    /**
     * Форма редактирования записи
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {
        
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
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @ModelAttribute("record") Record record,
            BindingResult bindingResult,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            Model model) {
        
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
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        try {
            recordService.deleteRecord(user, id);
            redirectAttributes.addFlashAttribute("success", "Запись успешно удалена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении записи: " + e.getMessage());
        }
        
        return "redirect:/records";
    }
}

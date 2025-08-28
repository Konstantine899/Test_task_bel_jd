package com.recordsapp.records_app.service;

import com.recordsapp.records_app.entity.Record;
import com.recordsapp.records_app.entity.User;
import com.recordsapp.records_app.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class RecordService {

    private final RecordRepository recordRepository;
    private final Path uploadsDir = Paths.get("./uploads");
    private static final Logger logger = LoggerFactory.getLogger(RecordService.class);


    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
        // Создаем директорию для загрузок, если не существует
        try {
            Files.createDirectories(uploadsDir);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для загрузок", e);
        }
    }

    /**
     * Возвращает страницу записей для пользователя
     */
    public Page<Record> getRecordsForUser(User user, Pageable pageable) {
        return recordRepository.findAllByUser(user, pageable);
    }

    /**
     * Сохраняет новую или обновляет существующую запись
     */
    public Record saveRecord(User user, Record record, MultipartFile imageFile) throws IOException {
        logger.info("Начинаем сохранение записи для пользователя: {}", user.getUsername());

        try {
            // Устанавливаем связь с пользователем
            record.setUser(user);
            logger.debug("Установлена связь с пользователем: {}", user.getId());
            
        
        // Если это редактирование существующей записи, сохраняем старое изображение
        String oldImagePath = null;
        if (record.getId() != null) {
            Optional<Record> existingRecord = recordRepository.findById(record.getId());
            if (existingRecord.isPresent()) {
                oldImagePath = existingRecord.get().getImagePath();
            }
        }
        
        // Обработка загрузки изображения
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = saveImage(imageFile);
            record.setImagePath(fileName);
            
            // Если загружено новое изображение, удаляем старое
            if (oldImagePath != null) {
                deleteImage(oldImagePath);
            }
        } else if (oldImagePath != null) {
            // Если изображение не загружено, сохраняем старое
            record.setImagePath(oldImagePath);
        }
            Record savedRecord = recordRepository.save(record);
            logger.info("Запись успешно сохранена с ID: {}", savedRecord.getId());
            return savedRecord;
            
        } catch (Exception e) {
            logger.error("Ошибка при сохранении записи: {}", e.getMessage(), e);
            throw e;
        }
    }
        
    

    /**
     * Находит запись по ID, если она принадлежит пользователю
     */
    public Optional<Record> findRecordForUserById(User user, Long id) {
        return recordRepository.findByIdAndUser(id, user);
    }

    /**
     * Удаляет запись по ID, только если она принадлежит пользователю
     */
    public void deleteRecord(User user, Long id) throws IOException {
        Optional<Record> recordOpt = recordRepository.findByIdAndUser(id, user);
        if (recordOpt.isPresent()) {
            Record record = recordOpt.get();
            // Удаляем связанный файл изображения
            if (record.getImagePath() != null) {
                deleteImage(record.getImagePath());
            }
            recordRepository.delete(record);
        }
    }

    /**
     * Сохраняет изображение и возвращает имя файла
     */
    private String saveImage(MultipartFile imageFile) throws IOException {
        String originalFileName = imageFile.getOriginalFilename();
        String fileExtension = originalFileName != null ? 
            originalFileName.substring(originalFileName.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID() + fileExtension;
        
        Path filePath = uploadsDir.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        return fileName;
    }

    /**
     * Удаляет изображение
     */
    private void deleteImage(String fileName) throws IOException {
        Path filePath = uploadsDir.resolve(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}

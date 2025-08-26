package com.recordsapp.records_app.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * КОНФИГУРАЦИЯ КОДИРОВКИ UTF-8
 * Для правильного отображения русских букв
 */


@Configuration
public class EncodingConfig {
  @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
}

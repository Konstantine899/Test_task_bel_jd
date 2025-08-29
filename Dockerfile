# Оптимизированный Dockerfile для Render.com
# Этап 1: Сборка Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем только pom.xml сначала для кэширования зависимостей
COPY pom.xml .

# Скачиваем зависимости (кэшируется отдельно от кода)
RUN mvn dependency:go-offline -B

# Копируем исходный код
COPY src ./src

# Собираем проект с явным указанием главного класса
RUN mvn clean package -DskipTests -B -Dspring-boot.repackage.mainClass=com.recordsapp.records_app.RecordsAppApplication

# Проверяем, что JAR файл создан
RUN ls -la target/ && test -f target/records-app-0.0.1-SNAPSHOT.jar

# Этап 2: Запуск приложения
FROM eclipse-temurin:21-jre-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Устанавливаем необходимые пакеты для работы с файлами
RUN apk add --no-cache tzdata

# Создаем пользователя для безопасности
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Создаем директорию для загрузок
RUN mkdir -p /app/uploads && \
    chown -R appuser:appgroup /app

# Копируем JAR файл из этапа сборки
COPY --from=build /app/target/records-app-0.0.1-SNAPSHOT.jar app.jar

# Устанавливаем правильные права доступа
RUN chown appuser:appgroup app.jar

# Переменные окружения для Render
ENV PORT=8080
ENV TZ=UTC
ENV SPRING_PROFILES_ACTIVE=production

# Открываем порт
EXPOSE 8080

# Переключаемся на непривилегированного пользователя
USER appuser

# Команда запуска с поддержкой переменной PORT
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-production} -jar app.jar --server.port=${PORT:-8080}"]
# Этап 1: Сборка Maven
FROM maven:3.9.6-openjdk-21 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и src
COPY pom.xml .
COPY src ./src

# Собираем проект
RUN mvn clean package -DskipTests

# Этап 2: Запуск приложения
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR файл из этапа сборки
COPY --from=build /app/target/records-app-0.0.1-SNAPSHOT.jar app.jar

# Создаем директорию для загрузок
RUN mkdir -p /app/uploads

# Открываем порт
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]
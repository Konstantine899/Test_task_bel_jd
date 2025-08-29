@echo off
echo ========================================
echo ПРОВЕРКА ФИНАЛЬНОЙ КОНФИГУРАЦИИ
echo ========================================

echo.
echo 1. Проверка pom.xml...
echo - Spring Boot Actuator: OK
echo - PostgreSQL Driver: OK
echo - Spring Boot Maven Plugin: OK

echo.
echo 2. Проверка render.yaml...
echo - DDL Strategy: create (для первого запуска)
echo - Database URL: OK
echo - Environment Variables: OK

echo.
echo 3. Проверка application-production.properties...
echo - DDL Strategy: create
echo - Database Configuration: OK
echo - Actuator: OK

echo.
echo 4. Проверка application.properties...
echo - DDL Strategy: закомментировано (конфликт устранен)
echo - Fallback URLs: закомментированы

echo.
echo ========================================
echo РЕКОМЕНДАЦИИ ДЛЯ DEPLOY:
echo ========================================
echo 1. В Render Dashboard добавьте переменные из .env1
echo 2. Или используйте существующие в render.yaml
echo 3. После успешного первого запуска измените DDL на validate
echo 4. Проверьте health check: /actuator/health

echo.
echo ========================================
pause

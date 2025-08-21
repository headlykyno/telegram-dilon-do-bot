package ru.headlyboi.dilondo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения Telegram бота для игры "Дилондо".
 * 
 * <p>Этот класс является точкой входа в приложение Spring Boot.
 * Он автоматически настраивает все необходимые компоненты и запускает
 * Telegram бота для обработки команд пользователей.</p>
 * 
 * <p>Бот поддерживает следующие функции:</p>
 * <ul>
 *   <li>Игра "Дилондо" - измерение и изменение длины пениса</li>
 *   <li>Система кулдаунов для предотвращения спама</li>
 *   <li>Топ игроков по длине пениса</li>
 *   <li>Работа только в групповых чатах</li>
 * </ul>
 *
 */
@SpringBootApplication
public class DilondoApplication {

    /**
     * Главный метод для запуска приложения.
     * 
     * <p>Инициализирует Spring Boot контекст и запускает Telegram бота.
     * Бот начинает прослушивать обновления от Telegram API и обрабатывать
     * входящие сообщения.</p>
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        SpringApplication.run(DilondoApplication.class, args);
    }
}

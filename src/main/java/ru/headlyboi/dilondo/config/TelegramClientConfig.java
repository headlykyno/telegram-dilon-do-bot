package ru.headlyboi.dilondo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Конфигурация Telegram клиента для взаимодействия с Telegram Bot API.
 *
 * <p>Этот класс настраивает и создает экземпляр Telegram клиента,
 * который используется для отправки сообщений и других операций
 * с Telegram Bot API.</p>
 *
 * <p>Конфигурация включает настройку токена бота и других параметров
 * подключения к Telegram API.</p>
 */
@Configuration
public class TelegramClientConfig {

    /**
     * Создает и настраивает экземпляр Telegram клиента.
     *
     * <p>Инициализирует клиент с токеном бота, полученным из конфигурации.
     * Клиент используется для выполнения всех операций с Telegram Bot API.</p>
     *
     * @param botToken токен бота из конфигурации
     * @return настроенный экземпляр Telegram клиента
     */
    @Bean
    public TelegramClient telegramClient(@Value("${telegram.bot.api.key}") String botToken) {
        return new OkHttpTelegramClient(botToken);
    }
}

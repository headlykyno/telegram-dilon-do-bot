package ru.headlyboi.dilondo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.headlyboi.dilondo.polling.DilondoBot;

/**
 * Конфигурация для настройки long polling режима работы Telegram бота.
 *
 * <p>Этот класс настраивает и запускает long polling приложение для
 * получения обновлений от Telegram Bot API. Обеспечивает непрерывное
 * прослушивание входящих сообщений и их обработку.</p>
 *
 * <p>Конфигурация включает регистрацию бота в polling приложении
 * и настройку параметров подключения.</p>
 */
@Configuration
@RequiredArgsConstructor
public class TelegramBotPollingConfig {

    /**
     * Создает и настраивает long polling приложение для Telegram бота.
     *
     * <p>Инициализирует приложение и регистрирует бота для получения
     * обновлений. Приложение начинает прослушивание входящих сообщений
     * от Telegram API.</p>
     *
     * @param dilondoBot экземпляр бота для регистрации
     * @param botToken   токен бота из конфигурации
     * @return настроенное long polling приложение
     * @throws TelegramApiException при ошибке регистрации бота
     */
    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication(
            DilondoBot dilondoBot, @Value("${telegram.bot.api.key}") String botToken)
            throws TelegramApiException {
        TelegramBotsLongPollingApplication application = new TelegramBotsLongPollingApplication();
        application.registerBot(botToken, dilondoBot);
        return application;
    }
}

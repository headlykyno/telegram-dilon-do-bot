package com.headlyboy.tgdilondobot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Класс конфигурации Telegram pooling.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TelegramBotPollingConfig {

    private final LongPollingSingleThreadUpdateConsumer tgDilondoBot;
    private final TelegramClientConfig telegramClientConfig;

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication() throws TelegramApiException {
        TelegramBotsLongPollingApplication tgBotApplication = new TelegramBotsLongPollingApplication();
        tgBotApplication.registerBot(telegramClientConfig.getApiKey(), tgDilondoBot);
        log.info("Telegram bots application started");
        return tgBotApplication;
    }


}

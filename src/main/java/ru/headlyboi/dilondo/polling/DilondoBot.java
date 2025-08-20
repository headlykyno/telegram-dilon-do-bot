package ru.headlyboi.dilondo.polling;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.headlyboi.dilondo.enums.ChatTypeEnum;
import ru.headlyboi.dilondo.enums.CommandEnum;
import ru.headlyboi.dilondo.service.api.CommandService;

/**
 * Основной класс Telegram бота для обработки сообщений и команд.
 *
 * <p>Этот класс реализует интерфейс {@link LongPollingUpdateConsumer} и отвечает за:</p>
 * <ul>
 *   <li>Получение обновлений от Telegram API</li>
 *   <li>Фильтрацию сообщений (только групповые чаты)</li>
 *   <li>Определение типа команды</li>
 *   <li>Делегирование обработки соответствующим сервисам</li>
 *   <li>Отправку ответов пользователям</li>
 * </ul>
 *
 * <p>Бот работает только в групповых чатах и игнорирует личные сообщения.
 * Использует многопоточность для обработки обновлений.</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DilondoBot implements LongPollingUpdateConsumer {

    @Value("${polling-threads:500}")
    private Integer threadCount;

    private final TelegramClient telegramClient;
    private final Map<CommandEnum, CommandService> commandServices;
    private Executor updatesExecutor;

    /**
     * Инициализирует пул потоков для обработки обновлений.
     *
     * <p>Вызывается автоматически после создания бина Spring.</p>
     */
    @PostConstruct
    public void executorInit() {
        updatesExecutor = Executors.newFixedThreadPool(threadCount);
    }

    /**
     * Обрабатывает список обновлений от Telegram API.
     *
     * <p>Каждое обновление обрабатывается в отдельном потоке для обеспечения
     * параллельной обработки и улучшения производительности.</p>
     *
     * @param updates список обновлений от Telegram API
     */
    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> updatesExecutor.execute(() -> consume(update)));
    }

    /**
     * Обрабатывает одно обновление от Telegram API.
     *
     * <p>Проверяет валидность обновления, определяет тип чата,
     * извлекает команду и делегирует обработку соответствующему сервису.</p>
     *
     * @param update обновление от Telegram API
     */
    private void consume(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        log.info("Consuming update {}", update.getMessage().getChatId());
        String type = update.getMessage().getChat().getType();
        boolean updateHasText = update.hasMessage() && update.getMessage().hasText();
        boolean isPrivateChat = ChatTypeEnum.PRIVATE.name().toLowerCase().equals(type);
        if (updateHasText && !isPrivateChat) {
            CommandEnum command = Optional.ofNullable(update.getMessage())
                    .map(Message::getText)
                    .map(String::trim)
                    .map(CommandEnum::parseAndGet)
                    .orElse(CommandEnum.UNKNOWN);

            CommandService service = commandServices.get(command);
            List<BotApiMethod<?>> messages = service.processDick(update);
            try {
                for (BotApiMethod<?> message : messages) {
                    telegramClient.execute(message);
                }
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

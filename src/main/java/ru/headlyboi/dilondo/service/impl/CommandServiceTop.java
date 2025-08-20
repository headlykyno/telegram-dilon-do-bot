package ru.headlyboi.dilondo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.headlyboi.dilondo.dto.ChatDto;
import ru.headlyboi.dilondo.enums.CommandEnum;
import ru.headlyboi.dilondo.mapper.DilondoMapper;
import ru.headlyboi.dilondo.repository.DilondoRepository;
import ru.headlyboi.dilondo.repository.model.TopEntity;
import ru.headlyboi.dilondo.service.api.CommandService;

import java.util.List;

/**
 * Сервис для обработки команды "Топ" - отображения рейтинга игроков.
 *
 * <p>Этот сервис отвечает за:</p>
 * <ul>
 *   <li>Получение списка игроков чата, отсортированного по длине пениса</li>
 *   <li>Формирование красивого сообщения с рейтингом</li>
 *   <li>Отправку дополнительного сообщения с игральной костью</li>
 * </ul>
 *
 * <p>Рейтинг отображается в формате "Позиция. Имя пользователя : Длина см"
 * с специальными эмодзи для первых мест.</p>
 *
 */
@Service
@RequiredArgsConstructor
public class CommandServiceTop implements CommandService {

    private final DilondoMapper dilondoMapper;
    private final DilondoRepository dilondoRepository;

    /**
     * Обрабатывает команду "Топ" и генерирует ответные сообщения.
     *
     * <p>Метод выполняет следующие действия:</p>
     * <ol>
     *   <li>Извлекает данные чата из обновления</li>
     *   <li>Получает список игроков, отсортированный по длине пениса</li>
     *   <li>Формирует текстовое сообщение с рейтингом</li>
     *   <li>Создает дополнительное сообщение с игральной костью</li>
     *   <li>Возвращает оба сообщения</li>
     * </ol>
     *
     * @param update обновление от Telegram API
     * @return список с текстовым сообщением и игральной костью
     */
    @Override
    public List<BotApiMethod<?>> processDick(Update update) {
        Integer messageId = update.getMessage().getMessageId();
        ChatDto chat = dilondoMapper.mapToChatDto(update);

        List<TopEntity> topList = dilondoRepository.findTopBy(chat.id());
        StringBuilder builder = new StringBuilder();
        builder.append("Большой \uD83C\uDF46, большие \uD83E\uDD5A\uD83E\uDD5A\n");
        int pos = 1;
        for (TopEntity topEntity : topList) {
            builder.append(String.format("%d. %s : %s см", pos++, topEntity.name(),
                    topEntity.dickLength()));
            if (pos == 2) {
                builder.append(" \uD83D\uDC51 \n");
            } else {
                builder.append(" \n");
            }
        }
        SendMessage message = SendMessage.builder()
                .chatId(chat.id())
                .text(builder.toString())
                .replyToMessageId(messageId)
                .build();
        SendDice dice = SendDice.builder()
                .chatId(chat.id())
                .emoji("🎰")
                .replyToMessageId(messageId)
                .build();

        return List.of(message, dice);
    }

    /**
     * Возвращает тип команды, обрабатываемой данным сервисом.
     *
     * @return {@link CommandEnum#TOP}
     */
    @Override
    public CommandEnum getCommand() {
        return CommandEnum.TOP;
    }
}

package ru.headlyboi.dilondo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.headlyboi.dilondo.dto.ChatDto;
import ru.headlyboi.dilondo.dto.ChatUserDto;
import ru.headlyboi.dilondo.dto.DickResultDto;
import ru.headlyboi.dilondo.dto.UserDto;
import ru.headlyboi.dilondo.enums.CommandEnum;
import ru.headlyboi.dilondo.mapper.DilondoMapper;
import ru.headlyboi.dilondo.repository.DilondoRepository;
import ru.headlyboi.dilondo.repository.model.ChatEntity;
import ru.headlyboi.dilondo.repository.model.ChatUserEntity;
import ru.headlyboi.dilondo.repository.model.UserEntity;
import ru.headlyboi.dilondo.service.api.CommandService;
import ru.headlyboi.dilondo.service.helper.DickLengthHelper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Сервис для обработки команды "Дилондо" - основной игровой механики бота.
 *
 * <p>Этот сервис реализует основную игровую логику приложения:</p>
 * <ul>
 *   <li>Обработка команды измерения длины пениса</li>
 *   <li>Управление системой кулдаунов</li>
 *   <li>Сохранение и обновление данных пользователей</li>
 *   <li>Генерация ответных сообщений с результатами</li>
 * </ul>
 *
 * <p>Сервис работает с транзакциями для обеспечения целостности данных
 * и использует вспомогательный класс {@link DickLengthHelper} для расчета
 * изменений длины пениса.</p>
 */
@Service
@RequiredArgsConstructor
public class CommandServiceDick implements CommandService {

    @Value("${cooldown}")
    private Long cooldown;

    private final DickLengthHelper dickLengthHelper;
    private final DilondoMapper dilondoMapper;
    private final DilondoRepository dilondoRepository;

    @Override
    @Transactional
    public List<BotApiMethod<?>> processDick(Update update) {
        Integer messageId = update.getMessage().getMessageId();
        ChatDto chat = dilondoMapper.mapToChatDto(update);
        UserDto user = dilondoMapper.mapToUserDto(update);

        ChatEntity chatEntity = dilondoRepository.insertOrUpdateWithReturnEntity(chat);
        UserEntity userEntity = dilondoRepository.insertOrUpdateWithReturnEntity(user);

        SendMessage sendMessage =
                dilondoRepository.findChatUserByIds(chatEntity.id(), userEntity.id())
                        .map(chatUser -> handleExistingUser(chatUser, chatEntity.id(), messageId))
                        .orElseGet(
                                () -> handleNewUser(chatEntity.id(), userEntity.id(), messageId));
        return Collections.singletonList(sendMessage);
    }

    @Override
    public CommandEnum getCommand() {
        return CommandEnum.DICK;
    }

    private SendMessage handleExistingUser(ChatUserEntity chatUser, Long chatId,
                                           Integer messageId) {
        LocalDateTime now = LocalDateTime.now();
        if (isCooldownActive(chatUser.lastRunAt(), now)) {
            return createCooldownMessage(chatId, messageId, chatUser.lastRunAt(), now);
        }

        DickResultDto newResult = dickLengthHelper.calculate(chatUser.dickLength(), false);
        updateChatUser(chatUser, newResult.after(), now);

        return createResultMessage(chatId, messageId, newResult);
    }

    /**
     * Обрабатывает нового пользователя в чате.
     *
     * <p>Создает запись для нового пользователя с начальной длиной пениса
     * и возвращает сообщение с результатом.</p>
     *
     * @param chatId    идентификатор чата
     * @param userId    идентификатор пользователя
     * @param messageId идентификатор исходного сообщения
     * @return ответное сообщение
     */
    private SendMessage handleNewUser(Long chatId, Long userId, Integer messageId) {
        DickResultDto dickResult = dickLengthHelper.calculate(0L, true);
        createChatUser(chatId, userId, dickResult.after());

        return createResultMessage(chatId, messageId, dickResult);
    }

    /**
     * Проверяет, активен ли кулдаун для пользователя.
     *
     * @param lastRun     время последнего использования команды
     * @param currentTime текущее время
     * @return {@code true} если кулдаун еще активен
     */
    private boolean isCooldownActive(LocalDateTime lastRun, LocalDateTime currentTime) {
        return lastRun.plusSeconds(cooldown).isAfter(currentTime);
    }

    /**
     * Создает сообщение о кулдауне с оставшимся временем.
     *
     * @param chatId      идентификатор чата
     * @param messageId   идентификатор исходного сообщения
     * @param lastRun     время последнего использования
     * @param currentTime текущее время
     * @return сообщение о кулдауне
     */
    private SendMessage createCooldownMessage(Long chatId, Integer messageId,
                                              LocalDateTime lastRun, LocalDateTime currentTime) {
        Duration remainingTime = Duration.between(currentTime, lastRun.plusSeconds(cooldown));
        long hours = remainingTime.toHours();
        long minutes = remainingTime.toMinutesPart();

        return SendMessage.builder()
                .chatId(chatId)
                .text(String.format("Вы уже играли, повторите через %02d:%02d", hours, minutes))
                .replyToMessageId(messageId)
                .build();
    }

    /**
     * Создает сообщение с результатом измерения длины пениса.
     *
     * @param chatId    идентификатор чата
     * @param messageId идентификатор исходного сообщения
     * @param result    результат расчета
     * @return сообщение с результатом
     */
    private SendMessage createResultMessage(Long chatId, Integer messageId, DickResultDto result) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(String.format("Ваш пенiс %s на %s см. Теперь его длина составляет %s",
                        result.resultEnum().getResult(), Math.abs(result.difference()),
                        result.after()))
                .replyToMessageId(messageId)
                .build();
    }

    /**
     * Обновляет данные пользователя в чате.
     *
     * @param chatUser  текущие данные пользователя
     * @param newLength новая длина пениса
     * @param lastRun   время последнего использования
     */
    private void updateChatUser(ChatUserEntity chatUser, Long newLength, LocalDateTime lastRun) {
        ChatUserDto updatedUser = ChatUserDto.builder()
                .userId(chatUser.userId())
                .chatId(chatUser.chatId())
                .dickLength(newLength)
                .lastRunAt(lastRun)
                .build();
        dilondoRepository.save(updatedUser);
    }

    /**
     * Создает новую запись пользователя в чате.
     *
     * @param chatId     идентификатор чата
     * @param userId     идентификатор пользователя
     * @param dickLength начальная длина пениса
     */
    private void createChatUser(Long chatId, Long userId, Long dickLength) {
        ChatUserDto newUser = ChatUserDto.builder()
                .chatId(chatId)
                .userId(userId)
                .dickLength(dickLength)
                .lastRunAt(LocalDateTime.now())
                .build();
        dilondoRepository.save(newUser);
    }
}
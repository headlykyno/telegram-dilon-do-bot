package ru.headlyboi.dilondo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Перечисление команд, поддерживаемых Telegram ботом.
 *
 * <p>Этот enum определяет все команды, которые может обрабатывать бот.
 * Каждая команда имеет соответствующий текст и связанный с ней сервис
 * для обработки.</p>
 *
 * <p>Команды используются для:</p>
 * <ul>
 *   <li>Определения типа входящего сообщения</li>
 *   <li>Маршрутизации к соответствующим сервисам обработки</li>
 *   <li>Валидации поддерживаемых команд</li>
 * </ul>
 */
@Getter
@RequiredArgsConstructor
public enum CommandEnum {

    DICK("/dick"),
    TOP("/top"),
    UNKNOWN("UNKNOWN");

    private final String command;

    /**
     * Парсит текстовую команду и возвращает соответствующий enum.
     *
     * <p>Сравнивает входящий текст с текстовыми представлениями всех команд
     * и возвращает соответствующий enum. Если команда не найдена, возвращает
     * {@link #UNKNOWN}.</p>
     *
     * @param command текст команды для парсинга
     * @return соответствующий enum или {@link #UNKNOWN} если команда не найдена
     */
    public static CommandEnum parseAndGet(@NotNull String command) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (command.startsWith(commandEnum.command)) {
                return commandEnum;
            }
        }
        return UNKNOWN;
    }
}

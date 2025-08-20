package ru.headlyboi.dilondo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.headlyboi.dilondo.dto.ChatDto;
import ru.headlyboi.dilondo.dto.UserDto;

/**
 * Маппер для преобразования объектов Telegram API в DTO приложения.
 *
 * <p>Этот интерфейс использует MapStruct для автоматической генерации
 * кода преобразования между объектами Telegram API и внутренними DTO
 * приложения.</p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Преобразование обновлений Telegram в DTO чатов</li>
 *   <li>Преобразование обновлений Telegram в DTO пользователей</li>
 *   <li>Маппинг полей с использованием аннотаций {@link Mapping}</li>
 * </ul>
 *
 * <p>Маппер интегрирован со Spring Framework для автоматического
 * создания бинов и внедрения зависимостей.</p>
 */
@Mapper(componentModel = "spring")
public interface DilondoMapper {

    /**
     * Преобразует обновление Telegram в DTO чата.
     *
     * <p>Извлекает данные чата из объекта {@link Update} и создает
     * соответствующий DTO объект с маппингом полей:</p>
     * <ul>
     *   <li>ID чата из {@code message.chat.id}</li>
     *   <li>Название чата из {@code message.chat.title}</li>
     * </ul>
     *
     * @param update обновление от Telegram API
     * @return DTO объект чата
     */
    @Mapping(target = "id", source = "message.chat.id")
    @Mapping(target = "name", source = "message.chat.title")
    ChatDto mapToChatDto(Update update);

    /**
     * Преобразует обновление Telegram в DTO пользователя.
     *
     * <p>Извлекает данные пользователя из объекта {@link Update} и создает
     * соответствующий DTO объект с маппингом полей:</p>
     * <ul>
     *   <li>ID пользователя из {@code message.from.id}</li>
     *   <li>Имя пользователя из {@code message.from.userName}</li>
     * </ul>
     *
     * @param update обновление от Telegram API
     * @return DTO объект пользователя
     */
    @Mapping(target = "id", source = "message.from.id")
    @Mapping(target = "username", source = "message.from.userName")
    UserDto mapToUserDto(Update update);
}

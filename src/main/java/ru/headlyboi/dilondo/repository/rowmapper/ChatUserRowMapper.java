package ru.headlyboi.dilondo.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.headlyboi.dilondo.repository.model.ChatUserEntity;

/**
 * Маппер для преобразования результатов SQL запросов в объекты {@link ChatUserEntity}.
 *
 * <p>Этот класс реализует интерфейс {@link RowMapper} для автоматического
 * преобразования строк результатов SQL запросов в объекты сущности пользователя в чате.
 * Используется в репозитории для маппинга данных из таблицы chat_user.</p>
 *
 * <p>Маппер интегрирован со Spring Framework для автоматического
 * создания бинов и внедрения зависимостей.</p>
 */
@Component
public class ChatUserRowMapper implements RowMapper<ChatUserEntity> {

    /**
     * Преобразует строку результата SQL запроса в объект {@link ChatUserEntity}.
     *
     * @param rs     результат SQL запроса
     * @param rowNum номер строки в результате
     * @return объект сущности пользователя в чате
     * @throws SQLException при ошибке доступа к данным результата
     */
    @Override
    public ChatUserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ChatUserEntity.builder()
                .userId(rs.getLong("user_id"))
                .chatId(rs.getLong("chat_id"))
                .lastRunAt(rs.getObject("last_run_at", LocalDateTime.class))
                .dickLength(rs.getLong("dick_length"))
                .build();
    }
}

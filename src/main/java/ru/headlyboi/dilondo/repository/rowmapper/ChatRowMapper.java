package ru.headlyboi.dilondo.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.headlyboi.dilondo.repository.model.ChatEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Маппер для преобразования результатов SQL запросов в объекты {@link ChatEntity}.
 *
 * <p>Этот класс реализует интерфейс {@link RowMapper} для автоматического
 * преобразования строк результатов SQL запросов в объекты сущности чата.
 * Используется в репозитории для маппинга данных из таблицы chat.</p>
 *
 * <p>Маппер интегрирован со Spring Framework для автоматического
 * создания бинов и внедрения зависимостей.</p>
 */
@Component
public class ChatRowMapper implements RowMapper<ChatEntity> {

    /**
     * Преобразует строку результата SQL запроса в объект {@link ChatEntity}.
     *
     * @param rs     результат SQL запроса
     * @param rowNum номер строки в результате
     * @return объект сущности чата
     * @throws SQLException при ошибке доступа к данным результата
     */
    @Override
    public ChatEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ChatEntity.builder()
                .id(rs.getLong("chat_id"))
                .chatName(rs.getString("chat_name"))
                .build();
    }
}

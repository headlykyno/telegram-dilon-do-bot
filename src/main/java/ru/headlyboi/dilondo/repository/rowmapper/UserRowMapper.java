package ru.headlyboi.dilondo.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.headlyboi.dilondo.repository.model.UserEntity;

/**
 * Маппер для преобразования результатов SQL запросов в объекты {@link UserEntity}.
 *
 * <p>Этот класс реализует интерфейс {@link RowMapper} для автоматического
 * преобразования строк результатов SQL запросов в объекты сущности пользователя.
 * Используется в репозитории для маппинга данных из таблицы user.</p>
 *
 * <p>Маппер интегрирован со Spring Framework для автоматического
 * создания бинов и внедрения зависимостей.</p>
 */
@Component
public class UserRowMapper implements RowMapper<UserEntity> {

    /**
     * Преобразует строку результата SQL запроса в объект {@link UserEntity}.
     *
     * @param rs     результат SQL запроса
     * @param rowNum номер строки в результате
     * @return объект сущности пользователя
     * @throws SQLException при ошибке доступа к данным результата
     */
    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserEntity.builder()
                .id(rs.getLong("user_id"))
                .username(rs.getString("username"))
                .build();
    }
}

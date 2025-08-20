package ru.headlyboi.dilondo.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.headlyboi.dilondo.repository.model.TopEntity;

/**
 * Маппер для преобразования результатов SQL запросов в объекты {@link TopEntity}.
 *
 * <p>Этот класс реализует интерфейс {@link RowMapper} для автоматического
 * преобразования строк результатов SQL запросов в объекты сущности топ-рейтинга.
 * Используется в репозитории для маппинга данных из JOIN запросов для получения
 * рейтинга игроков.</p>
 *
 * <p>Маппер интегрирован со Spring Framework для автоматического
 * создания бинов и внедрения зависимостей.</p>
 */
@Component
public class TopRowMapper implements RowMapper<TopEntity> {

    /**
     * Преобразует строку результата SQL запроса в объект {@link TopEntity}.
     *
     * @param rs     результат SQL запроса
     * @param rowNum номер строки в результате
     * @return объект сущности топ-рейтинга
     * @throws SQLException при ошибке доступа к данным результата
     */
    @Override
    public TopEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TopEntity.builder()
                .name(rs.getString("username"))
                .dickLength(rs.getLong("dick_length"))
                .build();
    }
}

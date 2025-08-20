package ru.headlyboi.dilondo.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.headlyboi.dilondo.dto.ChatDto;
import ru.headlyboi.dilondo.dto.ChatUserDto;
import ru.headlyboi.dilondo.dto.UserDto;
import ru.headlyboi.dilondo.repository.model.ChatEntity;
import ru.headlyboi.dilondo.repository.model.ChatUserEntity;
import ru.headlyboi.dilondo.repository.model.TopEntity;
import ru.headlyboi.dilondo.repository.model.UserEntity;
import ru.headlyboi.dilondo.repository.rowmapper.ChatRowMapper;
import ru.headlyboi.dilondo.repository.rowmapper.ChatUserRowMapper;
import ru.headlyboi.dilondo.repository.rowmapper.TopRowMapper;
import ru.headlyboi.dilondo.repository.rowmapper.UserRowMapper;

/**
 * Репозиторий для работы с базой данных приложения "Дилондо".
 *
 * <p>Этот класс предоставляет методы для:</p>
 * <ul>
 *   <li>Сохранения и обновления данных чатов</li>
 *   <li>Сохранения и обновления данных пользователей</li>
 *   <li>Управления связями пользователей с чатами</li>
 *   <li>Получения рейтинга игроков</li>
 * </ul>
 *
 * <p>Использует PostgreSQL с поддержкой UPSERT операций (ON CONFLICT)
 * для обеспечения уникальности данных и оптимизации производительности.</p>
 */
@Repository
@RequiredArgsConstructor
public class DilondoRepository {


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ChatUserRowMapper chatUserRowMapper;
    private final UserRowMapper userRowMapper;
    private final ChatRowMapper chatRowMapper;
    private final TopRowMapper topRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Сохраняет или обновляет данные чата в базе данных.
     *
     * <p>Использует UPSERT операцию (INSERT ... ON CONFLICT DO UPDATE)
     * для обеспечения уникальности чатов по идентификатору.</p>
     *
     * @param chat данные чата для сохранения
     * @return сохраненная сущность чата с данными из базы
     */
    public ChatEntity insertOrUpdateWithReturnEntity(ChatDto chat) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", chat.id())
                .addValue("name", chat.name());
        return jdbcTemplate.queryForObject("INSERT INTO chat(chat_id, chat_name) " +
                "VALUES (:id, :name) " +
                "ON CONFLICT (chat_id) DO UPDATE SET chat_id = :id, chat_name = :name " +
                "RETURNING chat_id, chat_name", params, chatRowMapper);
    }

    /**
     * Сохраняет или обновляет данные пользователя в базе данных.
     *
     * <p>Использует UPSERT операцию для обеспечения уникальности пользователей
     * по идентификатору.</p>
     *
     * @param user данные пользователя для сохранения
     * @return сохраненная сущность пользователя с данными из базы
     */
    public UserEntity insertOrUpdateWithReturnEntity(UserDto user) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", user.id())
                .addValue("username", user.username());
        return jdbcTemplate.queryForObject("INSERT INTO \"user\" (user_id, username) " +
                "VALUES (:id, :username) " +
                "ON CONFLICT (user_id) DO UPDATE SET user_id = :id, username = :username " +
                "RETURNING user_id, username", params, userRowMapper);
    }

    /**
     * Находит пользователя в конкретном чате по их идентификаторам.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return Optional с данными пользователя в чате или пустой Optional, если не найден
     */
    public Optional<ChatUserEntity> findChatUserByIds(Long chatId, Long userId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("chatId", chatId);

        List<ChatUserEntity> query = jdbcTemplate.query("SELECT * " +
                "FROM chat_user cu " +
                "WHERE cu.user_id = :userId  AND cu.chat_id = :chatId", params, chatUserRowMapper);
        return query.isEmpty() ? Optional.empty() : Optional.ofNullable(query.get(0));
    }

    /**
     * Сохраняет или обновляет данные пользователя в чате.
     *
     * <p>Использует UPSERT операцию для обеспечения уникальности записей
     * по комбинации идентификаторов пользователя и чата.</p>
     *
     * @param chatDto данные пользователя в чате для сохранения
     * @return сохраненная сущность пользователя в чате
     */
    public ChatUserEntity save(ChatUserDto chatDto) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("chat_id", chatDto.chatId())
                .addValue("user_id", chatDto.userId())
                .addValue("lastRunAt", chatDto.lastRunAt())
                .addValue("dickLength", chatDto.dickLength());
        return namedParameterJdbcTemplate.queryForObject(
                "INSERT INTO chat_user (user_id, chat_id, last_run_at, dick_length) " +
                        "VALUES(:user_id, :chat_id, :lastRunAt, :dickLength)" +
                        "ON CONFLICT (user_id, chat_id) " +
                        "DO UPDATE SET user_id = :user_id, " +
                        "chat_id = :chat_id, " +
                        "last_run_at = :lastRunAt, " +
                        "dick_length = :dickLength " +
                        "RETURNING user_id, chat_id, last_run_at, dick_length ",
                params, chatUserRowMapper);
    }

    /**
     * Получает рейтинг игроков в конкретном чате, отсортированный по длине пениса.
     *
     * <p>Выполняет JOIN запрос для получения имен пользователей и их длины пениса,
     * сортирует по убыванию длины.</p>
     *
     * @param chatId идентификатор чата
     * @return список игроков, отсортированный по длине пениса (от большего к меньшему)
     */
    public List<TopEntity> findTopBy(Long chatId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("chat_id", chatId);
        return namedParameterJdbcTemplate.query(
                "SELECT u.username, cu.dick_length " +
                        "FROM chat_user cu " +
                        "INNER JOIN \"user\" u on cu.user_id = u.user_id " +
                        "INNER JOIN chat c ON cu.chat_id = c.chat_id " +
                        "WHERE c.chat_id = :chat_id " +
                        "ORDER BY cu.dick_length DESC",
                params, topRowMapper);
    }
}

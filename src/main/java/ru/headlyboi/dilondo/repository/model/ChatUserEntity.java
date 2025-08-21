package ru.headlyboi.dilondo.repository.model;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Сущность для представления данных пользователя в чате в базе данных.
 *
 * <p>Этот класс представляет таблицу связей пользователей с чатами в базе данных
 * и содержит игровую статистику пользователя, включая длину пениса и время
 * последнего использования команды.</p>
 *
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record ChatUserEntity(Long userId, Long chatId, LocalDateTime lastRunAt, Long dickLength) {
}

package ru.headlyboi.dilondo.repository.model;

import lombok.Builder;

/**
 * Сущность для представления данных чата в базе данных.
 *
 * <p>Этот класс представляет таблицу чатов в базе данных и содержит
 * основную информацию о Telegram чате, необходимую для работы приложения.</p>
 *
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record ChatEntity(Long id, String chatName) {
}

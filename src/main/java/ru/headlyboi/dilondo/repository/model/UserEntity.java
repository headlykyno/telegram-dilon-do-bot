package ru.headlyboi.dilondo.repository.model;

import lombok.Builder;

/**
 * Сущность для представления данных пользователя в базе данных.
 *
 * <p>Этот класс представляет таблицу пользователей в базе данных и содержит
 * основную информацию о Telegram пользователе, необходимую для работы приложения.</p>
 *
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record UserEntity(Long id, String username) {
}

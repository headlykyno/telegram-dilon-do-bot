package ru.headlyboi.dilondo.repository.model;

import lombok.Builder;

/**
 * Сущность для представления данных топ-рейтинга игроков.
 *
 * <p>Этот класс используется для передачи данных о позиции игрока в рейтинге,
 * включая имя пользователя и его текущую длину пениса.</p>
 *
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record TopEntity(String name, Long dickLength) {
}

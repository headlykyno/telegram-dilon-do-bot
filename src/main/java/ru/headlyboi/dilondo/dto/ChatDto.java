package ru.headlyboi.dilondo.dto;

import lombok.Builder;

/**
 * DTO (Data Transfer Object) для представления данных чата.
 *
 * <p>Этот класс используется для передачи данных о чате между слоями приложения.
 * Содержит основную информацию о Telegram чате, необходимую для работы бота.</p>
 *
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record ChatDto(Long id, String name) {
}

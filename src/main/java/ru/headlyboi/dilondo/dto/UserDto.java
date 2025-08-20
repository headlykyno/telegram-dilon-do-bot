package ru.headlyboi.dilondo.dto;

import lombok.Builder;

/**
 * DTO (Data Transfer Object) для представления данных пользователя.
 * 
 * <p>Этот класс используется для передачи данных о пользователе между слоями приложения.
 * Содержит основную информацию о Telegram пользователе, необходимую для работы бота.</p>
 * 
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record UserDto(Long id, String username) {
}

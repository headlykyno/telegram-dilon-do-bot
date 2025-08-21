package ru.headlyboi.dilondo.dto;

import lombok.Builder;

/**
 * DTO (Data Transfer Object) для представления данных сообщения.
 * 
 * <p>Этот класс используется для передачи данных о сообщении между слоями приложения.
 * Содержит основную информацию о Telegram сообщении, необходимую для работы бота.</p>
 * 
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record MessageDto(Long chatId, String message) {
}

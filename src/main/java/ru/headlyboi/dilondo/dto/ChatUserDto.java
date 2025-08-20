package ru.headlyboi.dilondo.dto;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * DTO (Data Transfer Object) для представления данных пользователя в чате.
 *
 * <p>Этот класс используется для передачи данных о связи пользователя с чатом,
 * включая игровую статистику и временные метки. Содержит информацию о длине
 * пениса пользователя и времени последнего использования команды.</p>
 *
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record ChatUserDto(Long userId, Long chatId, Long dickLength, LocalDateTime lastRunAt) {
}

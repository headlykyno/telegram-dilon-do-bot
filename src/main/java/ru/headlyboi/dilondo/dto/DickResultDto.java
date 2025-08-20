package ru.headlyboi.dilondo.dto;

import lombok.Builder;
import ru.headlyboi.dilondo.enums.DickResultEnum;

/**
 * DTO (Data Transfer Object) для представления результата расчета длины пениса.
 *
 * <p>Этот класс используется для передачи результатов игровой механики
 * "Дилондо" между слоями приложения. Содержит информацию о изменении
 * длины пениса и метаданные для отображения пользователю.</p>
 *
 * <p>Класс является неизменяемым (immutable) и использует паттерн Builder
 * для создания экземпляров.</p>
 */
@Builder
public record DickResultDto(DickResultEnum resultEnum, Long before, Long after, Long difference) {
}

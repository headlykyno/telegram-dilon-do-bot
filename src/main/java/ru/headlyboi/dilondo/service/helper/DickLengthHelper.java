package ru.headlyboi.dilondo.service.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.headlyboi.dilondo.dto.DickResultDto;
import ru.headlyboi.dilondo.enums.DickResultEnum;

import java.util.Random;

/**
 * Вспомогательный сервис для расчета изменений длины пениса в игре "Дилондо".
 *
 * <p>Этот сервис реализует основную игровую механику - случайное изменение
 * длины пениса пользователя с учетом настроенных параметров:</p>
 * <ul>
 *   <li>Процент вероятности положительного изменения</li>
 *   <li>Диапазоны для положительных и отрицательных изменений</li>
 *   <li>Гарантированное положительное изменение для новых пользователей</li>
 * </ul>
 *
 * <p>Все параметры настраиваются через свойства конфигурации Spring.</p>
 */
@Service
public class DickLengthHelper {

    private final Integer percentPlus;
    private final Integer startOnPlus;
    private final Integer endOnPlus;
    private final Integer startOnMinus;
    private final Integer endOnMinus;

    /**
     * Конструктор с параметрами, настраиваемыми через Spring.
     *
     * @param percentPlus  процент вероятности положительного изменения
     * @param startOnPlus  минимальное значение положительного изменения
     * @param endOnPlus    максимальное значение положительного изменения
     * @param startOnMinus минимальное значение отрицательного изменения
     * @param endOnMinus   максимальное значение отрицательного изменения
     */
    public DickLengthHelper(@Value("${length.plus.percent}") Integer percentPlus,
                            @Value("${length.plus.start}") Integer startOnPlus,
                            @Value("${length.plus.end}") Integer endOnPlus,
                            @Value("${length.minus.start}") Integer startOnMinus,
                            @Value("${length.minus.end}") Integer endOnMinus) {
        this.percentPlus = percentPlus;
        this.startOnPlus = startOnPlus;
        this.endOnPlus = endOnPlus;
        this.startOnMinus = startOnMinus;
        this.endOnMinus = endOnMinus;
    }

    /**
     * Рассчитывает новую длину пениса на основе текущей длины и настроек.
     *
     * <p>Алгоритм расчета:</p>
     * <ol>
     *   <li>Если {@code isOnlyPositive} = true, генерируется только положительное изменение</li>
     *   <li>Иначе генерируется случайное число от 1 до 100</li>
     *   <li>Если число меньше {@code percentPlus}, изменение положительное</li>
     *   <li>Иначе изменение отрицательное</li>
     *   <li>Если результат отрицательный, рекурсивно вызывается
     *   с {@code isOnlyPositive} = true</li>
     * </ol>
     *
     * @param lengthBefore   текущая длина пениса
     * @param isOnlyPositive флаг для гарантированного положительного изменения
     * @return результат расчета с новой длиной и метаданными
     */
    public DickResultDto calculate(Long lengthBefore, boolean isOnlyPositive) {
        Long lengthAfter;

        if (isOnlyPositive) {
            int min = Math.min(startOnPlus, endOnPlus);
            int max = Math.max(startOnPlus, endOnPlus);
            Random random = new Random();
            lengthAfter = lengthBefore + (long) (random.nextInt(max - min + 1) + min);
        } else {
            Random randomForPositive = new Random();
            int resultRandom = randomForPositive.nextInt(100) + 1;
            Random random = new Random();
            if (percentPlus > resultRandom) {
                lengthAfter = lengthBefore +
                        (long) (random.nextInt(endOnPlus - startOnPlus + 1) + startOnPlus);
            } else {
                lengthAfter = lengthBefore + (long) (-1) *
                        (random.nextInt(endOnMinus - startOnMinus + 1) + startOnMinus);
            }
        }
        if (lengthAfter < 0) {
            lengthAfter = calculate(lengthBefore, true).after();
        }

        return DickResultDto.builder()
                .resultEnum(lengthBefore > lengthAfter ? DickResultEnum.DOWN : DickResultEnum.UP)
                .before(lengthBefore)
                .after(lengthAfter)
                .difference(lengthBefore - lengthAfter)
                .build();
    }
}

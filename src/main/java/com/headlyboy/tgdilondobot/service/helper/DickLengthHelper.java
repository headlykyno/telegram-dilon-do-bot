package com.headlyboy.tgdilondobot.service.helper;

import com.headlyboy.tgdilondobot.dto.DickResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class DickLengthHelper {

    private final Integer percentPlus;
    private final Integer startOnPlus;
    private final Integer endOnPlus;
    private final Integer startOnMinus;
    private final Integer endOnMinus;

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


    public DickResultDto getRandomInRange(Long lengthBefore, boolean isOnlyPositive) {
        Long lengthAfter;

        if (isOnlyPositive) {
            int min = Math.min(startOnPlus, endOnPlus); // Определяем минимальное значение
            int max = Math.max(startOnPlus, endOnPlus);
            Random random = new Random();
            lengthAfter = lengthBefore + (long) (random.nextInt(max - min + 1) + min);
        } else {
            Random randomForPositive = new Random();
            int resultRandom = randomForPositive.nextInt(100) + 1;
            Random random = new Random();
            if (resultRandom > percentPlus) {
                lengthAfter = lengthBefore + (long) (-1) * (random.nextInt(endOnPlus - startOnPlus + 1) + startOnPlus);
            } else {
                lengthAfter = lengthBefore + (long) (random.nextInt(endOnMinus - startOnMinus + 1) + startOnMinus);
            }
        }

        if (lengthAfter < 0) {
            lengthAfter = getRandomInRange(lengthBefore, true).after();
        }

        return DickResultDto.builder()
                .before(lengthBefore)
                .after(lengthAfter)
                .difference(lengthBefore - lengthAfter)
                .build();
    }
}

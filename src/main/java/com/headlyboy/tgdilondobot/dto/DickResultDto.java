package com.headlyboy.tgdilondobot.dto;

import com.headlyboy.tgdilondobot.enums.DickResultEnum;
import lombok.Builder;

@Builder
public record DickResultDto(DickResultEnum resultEnum, Long before, Long after, Long difference) {
}

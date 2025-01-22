package com.headlyboy.tgdilondobot.dto;

import lombok.Builder;

@Builder
public record MessageDto(Long chatId, String message) {
}

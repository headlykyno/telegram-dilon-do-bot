package com.headlyboy.tgdilondobot.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatUserDto(Long userId, Long chatId, LocalDateTime lastRunAt, Long dickLength) {
}

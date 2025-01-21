package com.headlyboy.tgdilondobot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ChatUserDto {

    private final Long userId;

    private final Long chatId;

    private LocalDateTime lastRunAt;

    private Long dickLength;
}

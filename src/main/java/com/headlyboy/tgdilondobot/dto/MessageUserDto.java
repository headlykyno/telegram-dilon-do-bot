package com.headlyboy.tgdilondobot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MessageUserDto {

    private final Long id;

    private final String username;
}

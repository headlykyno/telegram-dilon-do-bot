package com.headlyboy.tgdilondobot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserEntity {

    private final Long id;

    private final String username;

    private final Long currentSize;
}

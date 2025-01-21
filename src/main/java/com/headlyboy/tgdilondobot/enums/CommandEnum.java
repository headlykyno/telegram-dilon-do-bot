package com.headlyboy.tgdilondobot.enums;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public enum CommandEnum {

    DICK("/dick"),
    UNKNOWN("UNKNOWN");

    private final String command;

    public static CommandEnum parseAndGet(@NotNull String command) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (command.startsWith(commandEnum.command)) {
                return commandEnum;
            }
        }
        return UNKNOWN;
    }
}

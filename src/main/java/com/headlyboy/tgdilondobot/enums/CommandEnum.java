package com.headlyboy.tgdilondobot.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandEnum {

    DICK("/dick"),
    UNKNOWN("UNKNOWN");

    private final String command;

    public static CommandEnum parseAndGet(String command) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.command.equals(command)) {
                return commandEnum;
            }
        }
        return UNKNOWN;
    }
}

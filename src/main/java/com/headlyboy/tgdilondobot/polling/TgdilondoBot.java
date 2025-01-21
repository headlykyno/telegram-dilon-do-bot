package com.headlyboy.tgdilondobot.polling;

import com.headlyboy.tgdilondobot.enums.ChatTypeEnum;
import com.headlyboy.tgdilondobot.enums.CommandEnum;
import com.headlyboy.tgdilondobot.service.api.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TgdilondoBot implements LongPollingSingleThreadUpdateConsumer {

    private final CommandService commandService;

    @Override
    public void consume(Update update) {
        String type = update.getMessage().getChat().getType();
        boolean updateHasText = update.hasMessage() && update.getMessage().hasText();
        boolean chatIsGroup = ChatTypeEnum.GROUP.name().toLowerCase().equals(type);
        if (updateHasText && chatIsGroup) {
            CommandEnum command = Optional.ofNullable(update.getMessage())
                    .map(Message::getText)
                    .map(String::trim)
                    .map(CommandEnum::parseAndGet)
                    .orElse(CommandEnum.UNKNOWN);

            switch (command) {
                case DICK -> commandService.processDick(update);
            }
        }
    }
}

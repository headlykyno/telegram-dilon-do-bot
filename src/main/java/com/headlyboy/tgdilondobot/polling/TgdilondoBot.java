package com.headlyboy.tgdilondobot.polling;

import com.headlyboy.tgdilondobot.dto.MessageDto;
import com.headlyboy.tgdilondobot.enums.ChatTypeEnum;
import com.headlyboy.tgdilondobot.enums.CommandEnum;
import com.headlyboy.tgdilondobot.service.api.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TgdilondoBot implements LongPollingSingleThreadUpdateConsumer {

    private final CommandService commandService;
    private final TelegramClient telegramClient;

    @Override
    public void consume(Update update) {
        String type = update.getMessage().getChat().getType();
        boolean updateHasText = update.hasMessage() && update.getMessage().hasText();
        boolean isPrivateChat = ChatTypeEnum.PRIVATE.name().toLowerCase().equals(type);
        if (updateHasText && !isPrivateChat) {
            CommandEnum command = Optional.ofNullable(update.getMessage())
                    .map(Message::getText)
                    .map(String::trim)
                    .map(CommandEnum::parseAndGet)
                    .orElse(CommandEnum.UNKNOWN);

            MessageDto message = MessageDto.builder()
                    .chatId(update.getMessage().getChatId())
                    .message("Произошла ошибка")
                    .build();
            switch (command) {
                case DICK -> message = commandService.processDick(update);
            }
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(message.chatId())
                    .text(message.message())
                    .replyToMessageId(update.getMessage().getMessageId())
                    .build();

            try {
                telegramClient.execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

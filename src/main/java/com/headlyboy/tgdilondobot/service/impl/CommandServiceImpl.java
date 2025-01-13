package com.headlyboy.tgdilondobot.service.impl;

import com.headlyboy.tgdilondobot.dto.MessageResponseDto;
import com.headlyboy.tgdilondobot.service.api.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final TelegramClient telegramClient;

    @Override
    public MessageResponseDto processDick(Update update) {

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(update.getMessage().getText())
                .replyToMessageId(update.getMessage().getMessageId())
                .build();

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public MessageResponseDto processUnknown(Update update) {
        return null;
    }


    private int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}

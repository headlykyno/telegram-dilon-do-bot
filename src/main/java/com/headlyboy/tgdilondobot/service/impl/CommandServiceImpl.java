package com.headlyboy.tgdilondobot.service.impl;

import com.headlyboy.tgdilondobot.dao.api.ChatRepository;
import com.headlyboy.tgdilondobot.dao.api.ChatUserRepository;
import com.headlyboy.tgdilondobot.dao.api.UserRepository;
import com.headlyboy.tgdilondobot.dto.ChatDto;
import com.headlyboy.tgdilondobot.dto.ChatUserDto;
import com.headlyboy.tgdilondobot.dto.UserDto;
import com.headlyboy.tgdilondobot.service.api.CommandService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.jooq.generated.tables.records.ChatRecord;
import org.jooq.generated.tables.records.ChatUserRecord;
import org.jooq.generated.tables.records.UserRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final TelegramClient telegramClient;
    private final ChatUserRepository chatUserRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Override
    @Transactional
    public ChatDto processDick(Update update) {
        String userName = update.getMessage().getFrom().getUserName();
        Long userId = update.getMessage().getFrom().getId();
        Long chatId = update.getMessage().getChatId();
        String chatName = update.getMessage().getChat().getTitle();

        UserDto userDto = UserDto.builder()
                .id(userId)
                .username(userName)
                .build();
        ChatDto chatDto = ChatDto.builder()
                .id(chatId)
                .name(chatName)
                .build();

        Optional<UserRecord> userRecord = userRepository.insertOrUpdate(userDto);
        Optional<ChatRecord> chatRecord = chatRepository.insertOrUpdate(chatDto);
        ChatUserDto chatUserDto = ChatUserDto.builder()
                .userId(userDto.getId())
                .chatId(chatDto.getId())
                .lastRunAt(LocalDateTime.now())
                .dickLength(0L)
                .build();
        Optional<ChatUserRecord> chatUserRecord = chatUserRepository.insertOrUpdate(chatUserDto);


        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
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

}

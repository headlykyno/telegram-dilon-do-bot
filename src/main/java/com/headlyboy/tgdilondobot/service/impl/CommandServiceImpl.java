package com.headlyboy.tgdilondobot.service.impl;

import com.headlyboy.tgdilondobot.dto.*;
import com.headlyboy.tgdilondobot.mapper.ChatAndUserMapper;
import com.headlyboy.tgdilondobot.repository.api.ChatRepository;
import com.headlyboy.tgdilondobot.repository.api.ChatUserRepository;
import com.headlyboy.tgdilondobot.repository.api.UserRepository;
import com.headlyboy.tgdilondobot.service.api.CommandService;
import com.headlyboy.tgdilondobot.service.helper.DickLengthHelper;
import lombok.RequiredArgsConstructor;
import org.jooq.generated.tables.records.ChatRecord;
import org.jooq.generated.tables.records.ChatUserRecord;
import org.jooq.generated.tables.records.UserRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final TelegramClient telegramClient;
    private final ChatUserRepository chatUserRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final DickLengthHelper dickLengthHelper;
    private final ChatAndUserMapper chatAndUserMapper;

    @Override
    @Transactional
    public MessageDto processDick(Update update) {
        ChatDto chatDto = chatAndUserMapper.mapToChatDto(update);
        UserDto userDto = chatAndUserMapper.mapToUserDto(update);
        UserRecord userRecord = userRepository.insertOrUpdate(userDto)
                .orElseThrow();
        ChatRecord chatRecord = chatRepository.insertOrUpdate(chatDto)
                .orElseThrow();
        ChatUserRecord chatUser = chatUserRepository.findByChatIdAndUserId(chatRecord.getChatId(), userRecord.getUserId())
                .map(chatUserRecord -> {
                    // проверить, играл ли игрок сегодня
                    // если играл, то попросить сыграть завтра
                    // если не играл, то выдать результаты
                    Instant lastRunAt = chatUserRecord.getLastRunAt().toInstant(ZoneOffset.UTC);
                    Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
                    long timeFordick = Math.abs(lastRunAt.toEpochMilli() - now.toEpochMilli());
                    int milliseconds = 2 * 1000;
                    if (timeFordick < milliseconds) {
                        long hours = (milliseconds - timeFordick) / (1000 * 60 * 60);
                        long minutes = ((milliseconds - timeFordick) % (1000 * 60 * 60)) / (1000 * 60);
                        long seconds = ((milliseconds - timeFordick) % (1000 * 60)) / 1000;
                    } else {
                        DickResultDto dickResultDto = dickLengthHelper.getRandomInRange(chatUserRecord.getDickLength(), false);
                        ChatUserDto chatUserDto = ChatUserDto.builder()
                                .userId(userRecord.getUserId())
                                .chatId(chatRecord.getChatId())
                                .dickLength(dickResultDto.after())
                                .lastRunAt(LocalDateTime.now())
                                .build();
                        chatUserRepository.insertOrUpdate(chatUserDto);
                    }
                    return chatUserRecord;
                }).orElseGet(() -> {
                    DickResultDto dickResultDto = dickLengthHelper.getRandomInRange(0L, true);
                    ChatUserDto chatUserDto = ChatUserDto.builder()
                            .userId(userRecord.getUserId())
                            .chatId(chatRecord.getChatId())
                            .dickLength(dickResultDto.after())
                            .lastRunAt(LocalDateTime.now())
                            .build();
                    chatUserRepository.insertOrUpdate(chatUserDto);
                    return null;
                });
        return MessageDto.builder()
                .chatId(chatRecord.getChatId())
                .message("asdasd")
                .build();
    }

}

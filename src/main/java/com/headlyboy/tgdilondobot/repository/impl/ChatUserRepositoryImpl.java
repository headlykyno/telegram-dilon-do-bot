package com.headlyboy.tgdilondobot.repository.impl;

import com.headlyboy.tgdilondobot.repository.api.ChatUserRepository;
import com.headlyboy.tgdilondobot.dto.ChatUserDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.ChatUser;
import org.jooq.generated.tables.records.ChatUserRecord;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatUserRepositoryImpl implements ChatUserRepository {

    private final DSLContext dslContext;

    @Override
    public Optional<ChatUserRecord> insertOrUpdate(ChatUserDto dto) {
        return Optional.ofNullable(dslContext.insertInto(ChatUser.CHAT_USER)
                        .set(ChatUser.CHAT_USER.BOT_CHAT_ID, dto.chatId())
                        .set(ChatUser.CHAT_USER.BOT_USER_ID, dto.userId())
                        .set(ChatUser.CHAT_USER.DICK_LENGTH, dto.dickLength())
                        .set(ChatUser.CHAT_USER.LAST_RUN_AT, dto.lastRunAt())
                        .onDuplicateKeyUpdate()
                        .set(ChatUser.CHAT_USER.DICK_LENGTH, dto.dickLength())
                        .set(ChatUser.CHAT_USER.LAST_RUN_AT, dto.lastRunAt())
                        .returningResult(ChatUser.CHAT_USER)
                        .fetchOne())
                .map(r -> r.into(ChatUser.CHAT_USER));
    }

    @Override
    public Optional<ChatUserRecord> findByChatIdAndUserId(Long chatId, Long userId) {
        return Optional.ofNullable(dslContext.selectFrom(ChatUser.CHAT_USER)
                        .where(ChatUser.CHAT_USER.BOT_CHAT_ID.eq(chatId).and(ChatUser.CHAT_USER.BOT_USER_ID.eq(userId)))
                        .fetchOne())
                .map(r -> r.into(ChatUser.CHAT_USER));
    }
}

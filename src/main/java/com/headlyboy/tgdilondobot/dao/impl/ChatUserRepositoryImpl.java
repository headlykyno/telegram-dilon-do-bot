package com.headlyboy.tgdilondobot.dao.impl;

import com.headlyboy.tgdilondobot.dao.api.ChatUserRepository;
import com.headlyboy.tgdilondobot.dto.ChatUserDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.ChatUser;
import org.jooq.generated.tables.User;
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
                        .set(ChatUser.CHAT_USER.BOT_CHAT_ID, dto.getChatId())
                        .set(ChatUser.CHAT_USER.BOT_USER_ID, dto.getUserId())
                        .set(ChatUser.CHAT_USER.DICK_LENGTH, dto.getDickLength())
                        .set(ChatUser.CHAT_USER.LAST_RUN_AT, dto.getLastRunAt())
                        .returningResult(ChatUser.CHAT_USER)
                        .fetchOne())
                .map(r -> r.into(ChatUser.CHAT_USER));
    }
}

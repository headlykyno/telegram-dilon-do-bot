package com.headlyboy.tgdilondobot.dao.impl;

import com.headlyboy.tgdilondobot.dao.api.ChatRepository;
import com.headlyboy.tgdilondobot.dto.ChatDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.Chat;
import org.jooq.generated.tables.records.ChatRecord;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChatDaoImpl implements ChatRepository {

    private final DSLContext dslContext;

    @Override
    public Optional<ChatRecord> insertOrUpdate(@NonNull ChatDto chatDto) {
        return Optional.ofNullable(dslContext.insertInto(Chat.CHAT)
                        .set(Chat.CHAT.CHAT_ID, chatDto.getId())
                        .set(Chat.CHAT.CHAT_NAME, chatDto.getName())
                        .onDuplicateKeyUpdate()
                        .set(Chat.CHAT.CHAT_ID, chatDto.getId())
                        .set(Chat.CHAT.CHAT_NAME, chatDto.getName())
                        .returningResult(Chat.CHAT)
                        .fetchOne())
                .map(r -> r.into(Chat.CHAT));
    }
}

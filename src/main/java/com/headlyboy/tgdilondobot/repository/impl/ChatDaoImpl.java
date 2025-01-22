package com.headlyboy.tgdilondobot.repository.impl;

import com.headlyboy.tgdilondobot.repository.api.ChatRepository;
import com.headlyboy.tgdilondobot.dto.ChatDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.Chat;
import org.jooq.generated.tables.records.ChatRecord;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.jooq.impl.DSL.coalesce;

@Component
@RequiredArgsConstructor
public class ChatDaoImpl implements ChatRepository {

    private final DSLContext dslContext;

    @Override
    public Optional<ChatRecord> insertOrUpdate(@NonNull ChatDto chatDto) {

        Long chatIdAfterInsertOrUpdate = Optional.ofNullable(dslContext.insertInto(Chat.CHAT)
                        .set(Chat.CHAT.CHAT_ID,  chatDto.id())
                        .set(Chat.CHAT.CHAT_NAME, chatDto.name())
                        .onDuplicateKeyUpdate()
                        .set(Chat.CHAT.CHAT_ID, coalesce(chatDto.id()))
                        .set(Chat.CHAT.CHAT_NAME, coalesce(chatDto.name()))
                        .returningResult(Chat.CHAT.CHAT_ID)
                        .fetchOne())
                .map(r -> r.into(Long.class)).orElseThrow();

        return Optional.ofNullable(dslContext.select().from(Chat.CHAT)
                        .where(Chat.CHAT.CHAT_ID.eq(chatIdAfterInsertOrUpdate))
                        .fetchOne())
                .map(r -> r.into(Chat.CHAT));
    }
}

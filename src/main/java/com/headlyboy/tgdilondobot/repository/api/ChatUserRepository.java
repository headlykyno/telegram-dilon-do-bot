package com.headlyboy.tgdilondobot.repository.api;

import com.headlyboy.tgdilondobot.dto.ChatUserDto;
import org.jooq.generated.tables.records.ChatUserRecord;

import java.util.Optional;

public interface ChatUserRepository {

    Optional<ChatUserRecord> insertOrUpdate(ChatUserDto dto);

    Optional<ChatUserRecord> findByChatIdAndUserId(Long chatId, Long userId);
}

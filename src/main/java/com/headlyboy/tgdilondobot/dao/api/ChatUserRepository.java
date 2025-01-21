package com.headlyboy.tgdilondobot.dao.api;

import com.headlyboy.tgdilondobot.dto.ChatUserDto;
import org.jooq.generated.tables.records.ChatUserRecord;

import java.util.Optional;

public interface ChatUserRepository {

    Optional<ChatUserRecord> insertOrUpdate(ChatUserDto dto);
}

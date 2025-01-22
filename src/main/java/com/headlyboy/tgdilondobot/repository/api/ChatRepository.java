package com.headlyboy.tgdilondobot.repository.api;

import com.headlyboy.tgdilondobot.dto.ChatDto;
import lombok.NonNull;
import org.jooq.generated.tables.records.ChatRecord;

import java.util.Optional;

public interface ChatRepository {

    Optional<ChatRecord> insertOrUpdate(@NonNull ChatDto userDto);

}

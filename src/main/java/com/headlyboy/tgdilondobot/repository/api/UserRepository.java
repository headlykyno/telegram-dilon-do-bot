package com.headlyboy.tgdilondobot.repository.api;

import com.headlyboy.tgdilondobot.dto.UserDto;
import lombok.NonNull;
import org.jooq.generated.tables.records.UserRecord;

import java.util.Optional;

public interface UserRepository {

    Optional<UserRecord> insertOrUpdate(@NonNull UserDto userDto);

}

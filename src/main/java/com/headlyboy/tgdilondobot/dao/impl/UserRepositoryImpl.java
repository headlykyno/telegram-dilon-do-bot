package com.headlyboy.tgdilondobot.dao.impl;

import com.headlyboy.tgdilondobot.dao.api.UserRepository;
import com.headlyboy.tgdilondobot.dto.UserDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.User;
import org.jooq.generated.tables.records.UserRecord;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DSLContext dslContext;

    @Override
    public Optional<UserRecord> insertOrUpdate(@NonNull UserDto userDto) {
        return Optional.ofNullable(dslContext.insertInto(User.USER)
                        .set(User.USER.USER_ID, userDto.getId())
                        .set(User.USER.USERNAME, userDto.getUsername())
                        .onDuplicateKeyUpdate()
                        .set(User.USER.USER_ID, userDto.getId())
                        .set(User.USER.USERNAME, userDto.getUsername())
                        .returningResult(User.USER)
                        .fetchOne())
                .map(r -> r.into(User.USER));
    }

}

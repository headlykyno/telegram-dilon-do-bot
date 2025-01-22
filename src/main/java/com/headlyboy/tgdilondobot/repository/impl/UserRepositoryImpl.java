package com.headlyboy.tgdilondobot.repository.impl;

import com.headlyboy.tgdilondobot.repository.api.UserRepository;
import com.headlyboy.tgdilondobot.dto.UserDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.generated.tables.User;
import org.jooq.generated.tables.records.UserRecord;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DSLContext dslContext;

    @Override
    @Transactional
    public Optional<UserRecord> insertOrUpdate(@NonNull UserDto userDto) {
        Long userIdAfterInsertOrUpdate = Optional.ofNullable(dslContext.insertInto(User.USER)
                        .set(User.USER.USER_ID, userDto.id())
                        .set(User.USER.USERNAME, userDto.username())
                        .onDuplicateKeyUpdate()
                        .set(User.USER.USER_ID, userDto.id())
                        .set(User.USER.USERNAME, userDto.username())
                        .returningResult(User.USER.USER_ID)
                        .fetchOne())
                .map(r -> r.into(Long.class)).orElseThrow();

        return Optional.ofNullable(dslContext.select().from(User.USER)
                        .where(User.USER.USER_ID.eq(userIdAfterInsertOrUpdate))
                        .fetchOne())
                .map(r -> r.into(User.USER));
    }

}

package com.headlyboy.tgdilondobot.dao.impl;

import com.headlyboy.tgdilondobot.dao.api.UserDao;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final DSLContext dslContext;


}

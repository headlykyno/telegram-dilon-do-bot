package com.headlyboy.tgdilondobot.service.api;

import com.headlyboy.tgdilondobot.dto.ChatDto;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandService {

    @Transactional
    ChatDto processDick(Update update);

}

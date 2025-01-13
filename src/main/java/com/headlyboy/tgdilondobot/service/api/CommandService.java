package com.headlyboy.tgdilondobot.service.api;

import com.headlyboy.tgdilondobot.dto.MessageResponseDto;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandService {

    MessageResponseDto processDick(Update update);

    MessageResponseDto processUnknown(Update update);
}

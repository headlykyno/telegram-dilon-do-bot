package com.headlyboy.tgdilondobot.mapper;

import com.headlyboy.tgdilondobot.dto.ChatDto;
import com.headlyboy.tgdilondobot.dto.UserDto;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.Update;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ChatAndUserMapper {

    @Mapping(target = "id", source = "message.chat.id")
    @Mapping(target = "name", source = "message.chat.title")
    ChatDto mapToChatDto(Update update);

    @Mapping(target = "id", source = "message.from.id")
    @Mapping(target = "username", source = "message.from.userName")
    UserDto mapToUserDto(Update update);
}

package com.devilpanda.user_service.adapter.rest.dto;

import com.devilpanda.user_service.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    private final ModelMapper mapper;

    public UserDtoMapper() {
        mapper = new ModelMapper();
        mapper.typeMap(User.class, UserAuthDto.class)
                .addMapping(User::getEmail, UserAuthDto::setEmail)
                .addMapping(User::getLogin, UserAuthDto::setLogin);
        mapper.typeMap(UserCreationRequestDto.class, User.class)
                .addMapping(UserCreationRequestDto::getLogin, User::setLogin)
                .addMapping(UserCreationRequestDto::getEmail, User::setEmail)
                .addMapping(UserCreationRequestDto::getPassword, User::setPassword);
        mapper.typeMap(User.class, UserCreationRequestDto.class);
    }

    public UserAuthDto mapDtoFromUser(User source) {
        return mapper.map(source, UserAuthDto.class);
    }

    public UserCreationRequestDto mapFormDtoFromUser(User source) {
        return mapper.map(source, UserCreationRequestDto.class);
    }

    public User mapUserFromUserDto(UserCreationRequestDto source) {
        return mapper.map(source, User.class);
    }
}

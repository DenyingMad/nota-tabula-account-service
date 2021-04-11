package com.devilpanda.account_service.adapter.rest;

import com.devilpanda.account_service.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {
    private final ModelMapper mapper;

    public UserDtoMapper() {
        mapper = new ModelMapper();
        mapper.typeMap(User.class, UserDto.class)
                .addMapping(User::getEmail, UserDto::setEmail)
                .addMapping(User::getLogin, UserDto::setLogin);
        mapper.typeMap(UserFormDto.class, User.class)
                .addMapping(UserFormDto::getLogin, User::setLogin)
                .addMapping(UserFormDto::getEmail, User::setEmail)
                .addMapping(UserFormDto::getPassword, User::setPassword);
    }

    public UserDto mapDtoFromUser(User source) {
        return mapper.map(source, UserDto.class);
    }

    public User mapUserFromUserDto(UserFormDto source) {
        return mapper.map(source, User.class);
    }
}

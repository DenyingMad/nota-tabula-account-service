package com.devilpanda.user_service.app.api;

import com.devilpanda.user_service.domain.User;

public interface UserService {
    User getUserByLogin(String login);

    User getUserByEmail(String email);

    User createUser(User user);

    User changeUserName(String login, String userName);
}

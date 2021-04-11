package com.devilpanda.account_service.app.api;

import com.devilpanda.account_service.domain.User;

public interface UserService {
    User getUserByLogin(String login);

    User createUser(User user);
}

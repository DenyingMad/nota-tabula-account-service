package com.devilpanda.account_service.app.impl;

import com.devilpanda.account_service.adapter.jpa.UserRepository;
import com.devilpanda.account_service.app.api.UserAlreadyExistsException;
import com.devilpanda.account_service.app.api.UserService;
import com.devilpanda.account_service.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findUserByLogin(user.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException("Login " + user.getLogin());
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email " + user.getEmail());
        }

        return userRepository.saveAndFlush(user);
    }
}

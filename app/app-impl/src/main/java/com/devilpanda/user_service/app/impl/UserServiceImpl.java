package com.devilpanda.user_service.app.impl;

import com.devilpanda.user_service.adapter.jpa.UserRepository;
import com.devilpanda.user_service.app.api.UserAlreadyExistsException;
import com.devilpanda.user_service.app.api.UserNotFoundException;
import com.devilpanda.user_service.app.api.UserService;
import com.devilpanda.user_service.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional
    @Override
    public User createUser(User user) {
        if (userRepository.findUserByLogin(user.getLogin()).isPresent())
            throw new UserAlreadyExistsException("Login " + user.getLogin());
        if (userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException("Email " + user.getEmail());
        user.setUserName(user.getLogin());

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User changeUserName(String login, String userName) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        user.setUserName(userName);
        return user;
    }

    @Transactional
    @Override
    public User changePassword(String login, String password) {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
        user.setPassword(password);
        return user;
    }
}

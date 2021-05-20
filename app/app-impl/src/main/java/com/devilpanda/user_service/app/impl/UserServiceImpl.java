package com.devilpanda.user_service.app.impl;

import com.devilpanda.user_service.adapter.jpa.UserRepository;
import com.devilpanda.user_service.app.api.UserAlreadyExistsException;
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
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public User createUser(User user) {
        if (userRepository.findUserByLogin(user.getLogin()).isPresent())
            throw new UserAlreadyExistsException("Login " + user.getLogin());
        if (userRepository.findUserByEmail(user.getEmail()).isPresent())
            throw new UserAlreadyExistsException("Email " + user.getEmail());

        return userRepository.save(user);
    }
}

package com.devilpanda.account_service.adapter.jpa;

import com.devilpanda.account_service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(String email);
}

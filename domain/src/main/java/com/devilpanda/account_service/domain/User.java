package com.devilpanda.account_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class User extends BaseEntity {
    private String login;
    private String email;
    private String password;
}

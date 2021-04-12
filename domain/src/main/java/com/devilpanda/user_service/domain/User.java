package com.devilpanda.user_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User extends BaseEntity {
    private String login;
    private String email;
    private String password;
}

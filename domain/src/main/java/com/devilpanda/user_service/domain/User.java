package com.devilpanda.user_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User extends BaseEntity {
    private String login;
    private String email;
    private String password;
    private String userName;
    @Column(name = "issubscribed")
    private Boolean isSubscribed;

    @OneToMany(mappedBy = "author")
    private Set<Project> personalProjects;
}

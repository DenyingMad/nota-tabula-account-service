package com.devilpanda.user_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TEAM")
public class Team extends BaseEntity {
    String name;

    @ManyToMany
    @JoinTable(name = "USER_TEAM",
            joinColumns = @JoinColumn(name = "teamid"),
            inverseJoinColumns = @JoinColumn(name = "userid")
    )
    private Set<User> users;
}

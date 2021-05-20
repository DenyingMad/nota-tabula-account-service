package com.devilpanda.user_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(name = "issubscribed")
    private Boolean isSubscribed;

    @OneToMany(mappedBy = "author")
    private Set<Project> personalProjects;
}

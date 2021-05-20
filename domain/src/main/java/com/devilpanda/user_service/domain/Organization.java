package com.devilpanda.user_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ORGANIZATION")
public class Organization extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "organization")
    private Set<Project> projects;

    @OneToMany(mappedBy = "organization")
    private Set<UserOrganization> users;
}

package com.devilpanda.user_service.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "USER_ORGANIZATION")
public class UserOrganization extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "orgaizationid")
    Organization organization;

    @ManyToOne
    @JoinColumn(name = "userid")
    User user;

    private OrganizationRole role;
}

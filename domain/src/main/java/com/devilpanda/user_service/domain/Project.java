package com.devilpanda.user_service.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "PROJECT")
public class Project extends BaseEntity {
    private UUID uuid;
    private String name;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "organizationid")
    private Organization organization;
}

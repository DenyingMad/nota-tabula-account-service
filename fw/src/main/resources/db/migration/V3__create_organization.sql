CREATE TABLE ORGANIZATION
(
    id   BIGSERIAL NOT NULL,
    name VARCHAR,
    PRIMARY KEY (id)
);

CREATE TABLE USER_ORGANIZATION
(
    userId         BIGINT NOT NULL,
    organizationId BIGINT NOT NULL,
    role           VARCHAR,
    PRIMARY KEY (userId, organizationId),
    FOREIGN KEY (userId) REFERENCES USERS (id),
    FOREIGN KEY (organizationId) REFERENCES ORGANIZATION (id)
);

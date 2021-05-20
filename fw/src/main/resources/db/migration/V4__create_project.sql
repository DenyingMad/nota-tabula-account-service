CREATE TABLE PROJECT
(
    id             BIGSERIAL NOT NULL,
    uuid           UUID      NOT NULL,
    name           VARCHAR,
    author         BIGINT    NOT NULL,
    organizationId BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (organizationId) REFERENCES ORGANIZATION (id)
);

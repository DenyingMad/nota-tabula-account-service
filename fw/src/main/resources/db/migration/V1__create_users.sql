CREATE TABLE USERS
(
    id           BIGSERIAL NOT NULL,
    login        VARCHAR   NOT NULL,
    email        VARCHAR   NOT NULL,
    password     VARCHAR   NOT NULL,
    isSubscribed BOOLEAN   DEFAULT FALSE,
    PRIMARY KEY (id)
);

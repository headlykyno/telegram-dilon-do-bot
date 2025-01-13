CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS "user"
(
    user_id  BIGINT PRIMARY KEY DEFAULT nextval('user_seq'::regclass) NOT NULL,
    username VARCHAR(64)                                              NOT NULL
);
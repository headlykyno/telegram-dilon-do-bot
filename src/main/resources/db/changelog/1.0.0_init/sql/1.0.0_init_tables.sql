CREATE TABLE IF NOT EXISTS "user"
(
    user_id  BIGINT PRIMARY KEY NOT NULL,
    username VARCHAR(64)        NOT NULL
);

CREATE TABLE IF NOT EXISTS chat
(
    chat_id   BIGINT PRIMARY KEY NOT NULL,
    chat_name VARCHAR(64)    NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_user
(
    user_id BIGINT REFERENCES "user" (user_id) NOT NULL,
    chat_id BIGINT REFERENCES chat (chat_id)   NOT NULL,
    last_run_at TIMESTAMP                          NOT NULL,
    dick_length BIGINT                             NOT NULL,
    PRIMARY KEY (user_id, chat_id)
);


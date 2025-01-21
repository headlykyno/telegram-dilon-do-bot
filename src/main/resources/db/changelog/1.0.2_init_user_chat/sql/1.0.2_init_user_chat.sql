CREATE TABLE IF NOT EXISTS chat_user
(
    bot_user_id BIGINT REFERENCES "user" (user_id) NOT NULL,
    bot_chat_id BIGINT REFERENCES chat (chat_id)   NOT NULL,
    last_run_at TIMESTAMP                          NOT NULL,
    dick_length BIGINT                             NOT NULL,
    PRIMARY KEY (bot_user_id, bot_chat_id)
);


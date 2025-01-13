CREATE SEQUENCE IF NOT EXISTS chat_seq START WITH 1 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS chat
(
    chat_id   BIGINT PRIMARY KEY DEFAULT nextval('chat_seq'::regclass) NOT NULL,
    chat_name VARCHAR(64)                                                  NOT NULL
);
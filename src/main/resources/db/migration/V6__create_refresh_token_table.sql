CREATE SEQUENCE IF NOT EXISTS auth.refresh_token_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS auth.refresh_token (
    id BIGINT NOT NULL DEFAULT nextval('auth.refresh_token_seq'),
    token VARCHAR(255) NOT NULL UNIQUE,
    jti VARCHAR(255) NOT NULL UNIQUE,
    expiration_date TIMESTAMP,
    revoked BOOLEAN DEFAULT FALSE,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE
);

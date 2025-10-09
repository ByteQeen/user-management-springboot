CREATE SEQUENCE IF NOT EXISTS auth.user_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS auth.users (
    id INT PRIMARY KEY DEFAULT nextval('auth.user_seq'),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP
);

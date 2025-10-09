CREATE SEQUENCE IF NOT EXISTS auth.user_roles_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE IF NOT EXISTS auth.user_roles (
    id integer NOT NULL DEFAULT nextval('auth.user_roles_seq'),
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES auth.users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES auth.roles(id) ON DELETE CASCADE
);

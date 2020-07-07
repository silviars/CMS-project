DROP TABLE IF EXISTS users_permissions CASCADE;
CREATE TABLE users_permissions(
                                  user_id BIGINT NOT NULL,
                                  FOREIGN KEY(user_id) REFERENCES users(id),
                                  permission_id BIGINT NOT NULL,
                                  FOREIGN KEY(permission_id) REFERENCES permissions(id)
);

DROP TABLE IF EXISTS permissions CASCADE;
CREATE TABLE permissions(
                            id SERIAL PRIMARY KEY NOT NULL,
                            name VARCHAR(20) UNIQUE NOT NULL
);

INSERT INTO permissions (name) VALUES ('create_user');
INSERT INTO permissions (name) VALUES ('read_user');
INSERT INTO permissions (name) VALUES ('update_user');
INSERT INTO permissions (name) VALUES ('delete_user');
INSERT INTO permissions (name) VALUES ('create_contact');
INSERT INTO permissions (name) VALUES ('read_contact');
INSERT INTO permissions (name) VALUES ('update_contact');
INSERT INTO permissions (name) VALUES ('delete_contact');
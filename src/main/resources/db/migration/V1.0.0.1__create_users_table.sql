DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users(
                      id SERIAL PRIMARY KEY NOT NULL,
                      username VARCHAR(50) UNIQUE NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role varchar(20) NOT NULL
);

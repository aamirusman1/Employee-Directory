CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL

);

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);


INSERT INTO roles (id,name) VALUES (1,'ADMIN');

INSERT INTO users (id,username, password)
VALUES (1,'admin', '$2a$12$wrOGBnu/bFwthfW21RyyBedMb7r/kDcePewS.gNmlapo2MqkHoPeK');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);


create table users
(
    id       INTEGER not null,
    username VARCHAR not null,
    password VARCHAR not null,
    enabled  BOOLEAN not null
);
INSERT INTO users (id, username, password, enabled) VALUES (1, 'admin', '$2a$12$wrOGBnu/bFwthfW21RyyBedMb7r/kDcePewS.gNmlapo2MqkHoPeK', true);
create table roles
(
    id   INTEGER not null,
    name VARCHAR not null
);
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
create table user_roles
(
    user_id INTEGER not null,
    role_id INTEGER not null
);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
-- Usuário: root@email.com
-- Senha: root
INSERT INTO USERS(enabled, first_name, last_name, username_email, password) VALUES(1, 'Usuário', 'Teste', 'root@email.com', '$2a$10$OwdE5R9brie2GWObVCTAYe8p5QIRa/PaxIv0l6490/4LcYDTIo0rC');
INSERT INTO AUTHORITIES(authority) VALUES('ROLE_USER');
INSERT INTO USERS_AUTHORITIES(USER_ID, AUTHORITIES_ID) VALUES(1, 1);
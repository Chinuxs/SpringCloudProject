INSERT INTO usuarios (username,password, enable, nombre, apellido, email) VALUES ('andres','$2a$10$knJphP6vBcsrlQtrREyWROqG1nG4BqraWXftfykA5BPfCuRKH7R6S',true,'Andres','Gusman','profesor@bolsadeidesa.com');
INSERT INTO usuarios (username,password, enable, nombre, apellido, email) VALUES ('admin','$2a$10$DdMONycjpLR7EOmdBBS6OOD3BQ.JZ00FHlEaACU./cmpYx28jbGVu',true,'John', 'Doe','jhon.doe@bolsadeidesa.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2,1);
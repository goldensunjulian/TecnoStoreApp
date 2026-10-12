use tecnostore;

-- roles (privilegios: 1=vendedor, 2=bodega, 3=admin)
insert into rol (rol, privilegios) values ('Vendedor', 1);
insert into rol (rol, privilegios) values ('Admin Bodega', 2);
insert into rol (rol, privilegios) values ('Administrador', 3);

-- personas
insert into persona (nombre, identificacion, correo, telefono) values ('Carlos Perez', '1001234567', 'carlos@mail.com', '3001234567');
insert into persona (nombre, identificacion, correo, telefono) values ('Ana Gomez', '1009876543', 'ana@mail.com', '3109876543');
insert into persona (nombre, identificacion, correo, telefono) values ('Luis Torres', '1005555555', 'luis@mail.com', '3205555555');

-- empleados (persona_fk coincide con el id de cada persona insertada arriba)
-- carlos = vendedor, ana = bodega, luis = admin
insert into empleado (persona_fk, rol_fk, username, password) values (1, 1, 'carlos', 'carlos123');
insert into empleado (persona_fk, rol_fk, username, password) values (2, 2, 'ana', 'ana12345');
insert into empleado (persona_fk, rol_fk, username, password) values (3, 3, 'luis', 'luis1234');

-- marca
insert into marca (nombre) values ('Samsung');
insert into marca (nombre) values ('Apple');
insert into marca (nombre) values ('Xiaomi');

-- sistema operativo
insert into sistema_operativo (nombre) values ('Android');
insert into sistema_operativo (nombre) values ('iOS');

-- gama
insert into gama (nombre) values ('Alta');
insert into gama (nombre) values ('Media');
insert into gama (nombre) values ('Baja');

-- celulares (marca_fk, modelo, precio, stock, sistema_operativo_fk, gama_fk)
insert into celular (marca_fk, modelo, precio, stock, sistema_operativo_fk, gama_fk) values (1, 'Galaxy S24', 3500000, 10, 1, 1);
insert into celular (marca_fk, modelo, precio, stock, sistema_operativo_fk, gama_fk) values (2, 'iPhone 15', 4800000, 5, 2, 1);
insert into celular (marca_fk, modelo, precio, stock, sistema_operativo_fk, gama_fk) values (3, 'Redmi Note 13', 1200000, 20, 1, 3);

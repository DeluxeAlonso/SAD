-- acciones
insert into saddb.accion values (1,"Movimientos",null);
insert into saddb.accion values (2,"Operaciones",null);
insert into saddb.accion values (3,"Mantenimientos",null);
insert into saddb.accion values (4,"Reportes",null);
insert into saddb.accion values (5,"Seguridad",null);
insert into saddb.accion values (6,"Interfaces",null);
insert into saddb.accion values (7,"Internamiento",1);
insert into saddb.accion values (8,"Pedidos",1);
insert into saddb.accion values (9,"Movimientos Pallets",1);
insert into saddb.accion values (10,"Toma y ajuste de inventario",2);
insert into saddb.accion values (11,"Despacho",2);
insert into saddb.accion values (12,"Almacén",3);
insert into saddb.accion values (13,"Rack",3);
insert into saddb.accion values (14,"Pallet",3);
insert into saddb.accion values (15,"Unidad de transporte",3);
insert into saddb.accion values (16,"Kardex",4);
insert into saddb.accion values (17,"Stock",4);
insert into saddb.accion values (18,"Despacho",4);
insert into saddb.accion values (19,"Disponibilidad de almacén",4);
insert into saddb.accion values (20,"Caducidad de productos",4);
insert into saddb.accion values (21,"Usuarios y Perfiles",5);
insert into saddb.accion values (22,"Log",5);
insert into saddb.accion values (23,"Clientes",6);
insert into saddb.accion values (24,"Productos",6);
-- perfiles
insert into saddb.perfil value (1,"Administrador","");
insert into saddb.perfil value (2,"Jefe de almacen","");
insert into saddb.perfil value (3,"Supervisor","");


-- accion x perfil

-- administrador
insert into saddb.perfil_x_accion value(1,7);
insert into saddb.perfil_x_accion value(1,8);
insert into saddb.perfil_x_accion value(1,9);
insert into saddb.perfil_x_accion value(1,10);
insert into saddb.perfil_x_accion value(1,11);
insert into saddb.perfil_x_accion value(1,12);
insert into saddb.perfil_x_accion value(1,13);
insert into saddb.perfil_x_accion value(1,14);
insert into saddb.perfil_x_accion value(1,15);
insert into saddb.perfil_x_accion value(1,16);
insert into saddb.perfil_x_accion value(1,17);
insert into saddb.perfil_x_accion value(1,18);
insert into saddb.perfil_x_accion value(1,19);
insert into saddb.perfil_x_accion value(1,20);
insert into saddb.perfil_x_accion value(1,21);
insert into saddb.perfil_x_accion value(1,22);
insert into saddb.perfil_x_accion value(1,23);
insert into saddb.perfil_x_accion value(1,24);

-- jefe de almacen
insert into saddb.perfil_x_accion value(2,7);
insert into saddb.perfil_x_accion value(2,8);
insert into saddb.perfil_x_accion value(2,11);
insert into saddb.perfil_x_accion value(2,12);
insert into saddb.perfil_x_accion value(2,13);
insert into saddb.perfil_x_accion value(2,14);
insert into saddb.perfil_x_accion value(2,15);
insert into saddb.perfil_x_accion value(2,16);
insert into saddb.perfil_x_accion value(2,17);
insert into saddb.perfil_x_accion value(2,18);
insert into saddb.perfil_x_accion value(2,19);
insert into saddb.perfil_x_accion value(2,20);
insert into saddb.perfil_x_accion value(2,21);
insert into saddb.perfil_x_accion value(2,23);
insert into saddb.perfil_x_accion value(2,24);

-- supervisor

insert into saddb.perfil_x_accion value(3,9);
insert into saddb.perfil_x_accion value(3,10);
insert into saddb.perfil_x_accion value(3,11);
insert into saddb.perfil_x_accion value(3,16);
insert into saddb.perfil_x_accion value(3,18);


-- usuario root
insert into saddb.usuario values ('root','root','oB5c8xnp8Y0=','root','root','root',null,1,null,0);

-- preguntas
insert into saddb.pregunta_secreta values (1,"¿Cuál es el nombre de tu mascota?");
insert into saddb.pregunta_secreta values (2,"¿Cuál es tu grupo musical favorito?");
insert into saddb.pregunta_secreta values (3,"¿Cuál es el nombre del distrito donde naciste?");

-- almacen
insert into saddb.condicion value (1, "Normal", "Condicion normal");
insert into saddb.condicion value (2, "Refrigerado", "Condicion Refrigerado");

insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (1, "Carga Liviana", 1, 40, 20);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (2, "Carga Mediana", 2,40,20);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (3, "Carga Pesada", 1,40,20);

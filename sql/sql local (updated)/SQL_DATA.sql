insert into saddb.accion(id,nombre) value (1,"Movimientos");
insert into saddb.accion(id,nombre) value (2,"Operaciones");
insert into saddb.accion(id,nombre) value (3,"Mantenimientos");
insert into saddb.accion(id,nombre) value (4,"Reportes");
insert into saddb.accion(id,nombre) value (5,"Seguridad");
insert into saddb.accion(id,nombre) value (6,"Interfaces");
insert into saddb.accion values (7,"Internamiento",1);
insert into saddb.accion values (8,"Pedidos",1);
insert into saddb.accion values (9,"Movimientos Pallets",1);
insert into saddb.accion values (10,"Toma y ajuste de inventario",2);
insert into saddb.accion values (11,"Despacho",2);
insert into saddb.accion values (12,"Devoluciones",2);
insert into saddb.accion values (13,"Almacén",3);
insert into saddb.accion values (14,"Unidad de transporte",3);
insert into saddb.accion values (15,"Rack",3);
insert into saddb.accion values (16,"Pallet",3);
insert into saddb.accion values (17,"Kardex",4);
insert into saddb.accion values (18,"Stock",4);
insert into saddb.accion values (19,"Guías de remisión",4);
insert into saddb.accion values (20,"Disponibilidad de almacén",4);
insert into saddb.accion values (21,"Caducidad de productos",4);
insert into saddb.accion values (22,"Usuarios y Perfiles",5);
insert into saddb.accion values (23,"Log",5);
insert into saddb.accion values (24,"Personal",6);
insert into saddb.accion values (25,"Clientes",6);
insert into saddb.accion values (26,"Productos",6);

insert into saddb.perfil value (1,"Jefe de almacen","");
insert into saddb.perfil value (2,"Supervisor","");
insert into saddb.perfil value (3,"Administrador","");

insert into saddb.condicion value (1, "Normal", "Condicion normal");
insert into saddb.condicion value (2, "Refrigerado", "Condicion Refrigerado");

insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (1, "Carga Liviana", 1, 15, 5.00);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (2, "Carga Mediana", 2,10,5.00);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (3, "Carga Pesada", 1,12,6.00);


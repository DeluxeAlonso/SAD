insert into saddb.accion value (1,"Movimientos");
insert into saddb.accion value (2,"Operaciones");
insert into saddb.accion value (3,"Mantenimientos");
insert into saddb.accion value (4,"Reportes");
insert into saddb.accion value (5,"Seguridad");
insert into saddb.accion value (6,"Interfaces");
insert into saddb.accion value (7,"Sesión");

insert into saddb.perfil value (1,"Jefe de almacen","");
insert into saddb.perfil value (2,"Supervisor","");
insert into saddb.perfil value (3,"Administrador","");

insert into saddb.condicion value (1, "Normal", "Condicion normal");
insert into saddb.condicion value (2, "Refrigerado", "Condicion Refrigerado");

insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (1, "Carga Liviana", 1, 15, 5.00);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (2, "Carga Mediana", 2,10,5.00);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (3, "Carga Pesada", 1,12,6.00);


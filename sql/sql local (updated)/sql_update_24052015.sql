insert into saddb.Condicion value (1, "Normal", "Condicion normal");
insert into saddb.Condicion value (2, "Refrigerado", "Condicion Refrigerado");

insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (1, "Carga Liviana", 1, 15, 5.00);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (2, "Carga Mediana", 2,10,5.00);
insert into saddb.Tipo_Unidad_Transporte (id, descripcion, id_condicion, capacidad_pallets, velocidad_promedio) value (3, "Carga Pesada", 1,12,6.00);

insert into saddb.unidad_transporte value (1, "Alonso", "BGZ326", 15, 1);

insert into Producto (id, nombre, descripcion, stock_total, id_condicion) value (2, "SUblime", "Sublime", 15, 1);

alter table saddb.accion add column idPadre integer null;
alter table saddb.accion add constraint fk_accion foreign key (idPadre) references accion(id);

delete from saddb.accion where id=7;

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

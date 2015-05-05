ALTER TABLE Usuario
  ADD estado integer NULL
    AFTER idPregunta_Secreta;
alter table Usuario modify column password varchar(50);
alter table Usuario modify column idusuario varchar(40);
create database Empresa;
--Anteriormente ya tenia creado el usuario manuel --
grant all privileges on empresa.* to 'manuel'@'%';
flush privileges;
use Empresa;
-- Me cambio al usuario que le hemos dato los permisos --
create table  empleados(nombre varchar(50), apellido varchar (50) , fecha_de_nacimiento date , fecha_de_Ingreso date , puesto varchar(20), salario int(7));
create table  empleados_Antiguos( nombre varchar(50) , apellidos varchar(50) , fecha_de_nacimiento  date , fecha_de_ingreso date , fecha_de_finalizacion date);
insert into empleados values ('Sara','Gonzalez','1980-05-02','1999-06-03','Secretaria',25000);
insert into empleados values ('David','Martin','1999-04-17','2025-03-04','Programador',50000);
insert into empleados values ('David','Martin','1999-03-12','2021-03-12','Auxiliar',40000);
insert into empleados values ('Juan','Torres','1960-01-01','1980-05-24','Jefe',60000);
insert into empleados values ('Pepe','Uriel','1991-10-04','2015-10-01','Administrativo',24000);
insert into empleados values ('Manuel','Parra','2004-10-31','2026-09-12','Encargado',23902);
insert into empleados values ('Elena','Sanchez','1990-09-03','2010-11-02','Supervisora',30000);
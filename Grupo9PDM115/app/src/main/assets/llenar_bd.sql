--==============================================================
-- TABLAS CATÁLOGO
--==============================================================
-- DIA 
DELETE FROM DIA;
INSERT INTO DIA (NOMBREDIA) VALUES ("Domingo");
INSERT INTO DIA (NOMBREDIA) VALUES ("Lunes");
INSERT INTO DIA (NOMBREDIA) VALUES ("Martes");
INSERT INTO DIA (NOMBREDIA) VALUES ("Miércoles");
INSERT INTO DIA (NOMBREDIA) VALUES ("Jueves");
INSERT INTO DIA (NOMBREDIA) VALUES ("Viernes");
INSERT INTO DIA (NOMBREDIA) VALUES ("Sábado");

-- HORARIO
DELETE FROM HORARIO;
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("06:20", "08:00");
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("08:05", "09:45");
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("09:50", "11:30");
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("11:35", "13:15");
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("13:20", "15:00");
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("15:05", "16:45");
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("16:50", "18:30");
INSERT INTO HORARIO(HORAINICIO, HORAFINAL) VALUES ("18:35", "20:15");

-- OPCIONCRUD
DELETE FROM OPCIONCRUD;
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('IRO','Insertar rol',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ERO','Editar rol',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DRO','Eliminar rol',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CRO','Consultar rol',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ICL','Insertar ciclo',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ECL','Editar ciclo',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DCL','Eliminar ciclo',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CCL','Consultar ciclo',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ICM','Insertar ciclo-materia',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ECM','Editar ciclo-materia',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DCM','Eliminar ciclo-materia',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CCM','Consultar ciclo-materia',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ICO','Insertar coordinación',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ECO','Editar coordinación',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DCO','Eliminar coordinación',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CCO','Consultar coordinación',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('IFE','Insertar feriado',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('EFE','Editar feriado',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DFE','Eliminar feriado',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CFE','Consultar feriado',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('IGR','Insertar grupo',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('EGR','Editar grupo',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DGR','Eliminar grupo',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CGR','Consultar grupo',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('IHO','Insertar horario',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('EHO','Editar horario',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DHO','Eliminar horario',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CHO','Consultar horario',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ILO','Insertar local',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ELO','Editar local',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DLO','Eliminar local',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CLO','Consultar local',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('IMA','Insertar materia',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('EMA','Editar materia',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DMA','Eliminar materia',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CMA','Consultar materia',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ITG','Insertar tipo grupo',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ETG','Editar tipo grupo',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DTG','Eliminar tipo grupo',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CTG','Consultar tipo grupo',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ITL','Insertar tipo local',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('ETL','Editar tipo local',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DTL','Eliminar tipo local',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CTL','Consultar tipo local',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('IUS','Insertar usuario',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('EUS','Editar usuario',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DUS','Eliminar usuario',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CUS','Consultar usuario',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('IUN','Insertar unidad',1);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('EUN','Editar unidad',2);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('DUN','Eliminar unidad',3);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('CUN','Consultar unidad',4);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('GSO','Gestionar solicitudes',5);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('RSO','Realizar solicitudes',5);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('GTE','Modificar tema',5);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('GTU','Ver tutoriales',5);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('GEP','Exportar PDF',5);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('GIE','Importar Excel',5);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('GQR','Consultar QR',5);
INSERT INTO OPCIONCRUD(IDOPCION,DESOPCION,NUMCRUD) VALUES ('GEC','Envío de correos',5);

--==============================================================
-- TABLAS ADMINISTRACIÓN ACADÉMICA
--==============================================================
-- CICLO
DELETE FROM CICLO;
INSERT INTO CICLO (NOMBRECICLO, INICIO, FIN, ESTADOCICLO, INICIOPERIODOCLASE, FINPERIODOCLASE)
    VALUES ("CICLO I - 2020", "2020-01-01", "2020-07-22", 1, "2020-02-16", "2020-06-30");
INSERT INTO CICLO (NOMBRECICLO, INICIO, FIN, ESTADOCICLO, INICIOPERIODOCLASE, FINPERIODOCLASE)
    VALUES ("CICLO II - 2020", "2020-07-01", "2020-12-31", 0, "2020-08-15", "2020-12-15");

-- FERIADOS
DELETE FROM FERIADO;
INSERT INTO FERIADO(IDCICLO, FECHAINICIOFERIADO, FECHAFINFERIADO, NOMBREFERIADO, DESCRIPCIONFERIADO, BLOQUEARRESERVAS)
    VALUES (1, "2020-04-05", "2020-04-11", "Semana Santa", "Semana Santa", 1);
INSERT INTO FERIADO(IDCICLO, FECHAINICIOFERIADO, FECHAFINFERIADO, NOMBREFERIADO, DESCRIPCIONFERIADO, BLOQUEARRESERVAS)
    VALUES (1, "2020-06-21", "2020-06-27", "Semana del Estudiante", "Semana del estudiante", 0);
INSERT INTO FERIADO(IDCICLO, FECHAINICIOFERIADO, FECHAFINFERIADO, NOMBREFERIADO, DESCRIPCIONFERIADO, BLOQUEARRESERVAS)
    VALUES (1, "2020-05-10", "", "Día de la madre", "Día de la madre", 1);

-- UNIDAD 
DELETE FROM UNIDAD;
INSERT INTO UNIDAD(nombreent, descripcionent)
    VALUES ("Ninguna", "");
INSERT INTO UNIDAD (nombreent, descripcionent)
    VALUES ("EISI", "Escuela de Ingeniería de Sistemas Informáticos");
INSERT INTO UNIDAD (nombreent, descripcionent)
    VALUES ("UCB", "Unidad de Ciencias Básicas");

-- MATERIA 
DELETE FROM MATERIA;
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("MAT115", "Matemáticas 1", 1, 3);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("PRN115", "Programación I", 0, 2);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("PRN215", "Programación II", 0, 2);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("PRN315", "Programación III", 0, 2);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("PRN415", "Programación IV", 0, 2);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("PRN515", "Programación V", 0, 2);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("FIR115", "Física I", 1, 3);
INSERT INTO MATERIA (codmateria, nombremat, masiva, idunidad)
    VALUES ("TSI115", "Teoría de Sistemas", 0, 2);

-- CICLOMATERIA 
DELETE FROM CICLOMATERIA;
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (1, "MAT115");
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (1, "PRN115");
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (2, "FIR115");
INSERT INTO CICLOMATERIA (idciclo, codmateria) VALUES (2, "TSI115");

-- TIPOGRUPO 
DELETE FROM TIPOGRUPO;
INSERT INTO TIPOGRUPO (nombretipogrupo) VALUES ("Teórico");
INSERT INTO TIPOGRUPO (nombretipogrupo) VALUES ("Laboratorio");
INSERT INTO TIPOGRUPO (nombretipogrupo) VALUES ("Discusión");

-- GRUPO
DELETE FROM GRUPO;
-- Grupos para MAT115
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (1, 1, 01);
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (2, 1, 01);
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (3, 1, 01);
-- Grupos para PRN115
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (1, 2, 01);
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (3, 2, 01);
-- Grupos para IEC115
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (1, 3, 01);
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (2, 3, 01);
-- Grupos para TSI115
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (1, 4, 01);
INSERT INTO GRUPO (idtipogrupo, idciclomateria, numero) VALUES (2, 4, 01);

--==============================================================
-- TABLAS USUARIO
--==============================================================
--ROL
DELETE FROM ROL;
INSERT INTO ROL(NOMBREROL) VALUES ('Administrador');

-- ACCESOUSUARIO
DELETE FROM ACCESOUSUARIO;
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ICL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ECL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DCL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CCL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ICM',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ECM',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DCM',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CCM',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ICO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ECO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DCO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CCO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('IFE',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('EFE',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DFE',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CFE',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('IGR',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('EGR',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DGR',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CGR',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('IHO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('EHO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DHO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CHO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ILO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ELO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DLO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CLO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('IMA',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('EMA',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DMA',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CMA',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ITG',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ETG',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DTG',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CTG',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ITL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ETL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DTL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CTL',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('IUS',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('EUS',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DUS',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CUS',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('IUN',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('EUN',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DUN',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CUN',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('IRO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('ERO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('DRO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('CRO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('GSO',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('RSO',1);

INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('GTE',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('GTU',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('GEP',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('GIE',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('GQR',1);
INSERT INTO ACCESOUSUARIO(IDOPCION,IDROL) VALUES ('GEC',1);


-- USUARIO
DELETE FROM USUARIO;
INSERT INTO USUARIO(IDUSUARIO,IDUNIDAD,IDROL,NOMBREUSUARIO,CLAVEUSUARIO,NOMBREPERSONAL,APELLIDOPERSONAL,CORREOPERSONAL)
    VALUES ('AA001',1,1,'admin','admin','Administrador','App','administrador@admin.com');
INSERT INTO USUARIO(IDUSUARIO,IDUNIDAD,IDROL,NOMBREUSUARIO,CLAVEUSUARIO,NOMBREPERSONAL,APELLIDOPERSONAL,CORREOPERSONAL)
    VALUES ('OL001',1,1,'oslizama','Oscar123','Oscar','Lizama','oslizama@gmail.com');
INSERT INTO USUARIO(IDUSUARIO,IDUNIDAD,IDROL,NOMBREUSUARIO,CLAVEUSUARIO,NOMBREPERSONAL,APELLIDOPERSONAL,CORREOPERSONAL)
    VALUES ('CP001',1,1,'carlosp','Carlos123','Carlos','Pastrán','pastran2526@gmail.com');
INSERT INTO USUARIO(IDUSUARIO,IDUNIDAD,IDROL,NOMBREUSUARIO,CLAVEUSUARIO,NOMBREPERSONAL,APELLIDOPERSONAL,CORREOPERSONAL)
    VALUES ('PA001',1,1,'pablo','Pablo123','Pablo','Avelar','jp.avelar.m96@gmail.com');
INSERT INTO USUARIO(IDUSUARIO,IDUNIDAD,IDROL,NOMBREUSUARIO,CLAVEUSUARIO,NOMBREPERSONAL,APELLIDOPERSONAL,CORREOPERSONAL)
    VALUES ('CC001',1,1,'carlosc','Carlos123','Carlos','Coto','carloscaceres1608@gmail.com');
INSERT INTO USUARIO(IDUSUARIO,IDUNIDAD,IDROL,NOMBREUSUARIO,CLAVEUSUARIO,NOMBREPERSONAL,APELLIDOPERSONAL,CORREOPERSONAL)
    VALUES ('VN001',1,1,'victorn','Victor123','Victor','Navarrete','conermendez95@gmail.com');
-- COORDINACION

--==============================================================
-- TABLAS LOCALES
--==============================================================
-- TIPOLOCAL
DELETE FROM TIPOLOCAL;
INSERT INTO TIPOLOCAL (IDENCARGADO, NOMBRETIPO) VALUES ('OL001', 'Aulas');
INSERT INTO TIPOLOCAL (IDENCARGADO, NOMBRETIPO) VALUES ('PA001', 'Centros de cómputo');

-- LOCAL
DELETE FROM LOCAL;
INSERT INTO LOCAL(IDTIPOLOCAL, NOMBRELOCAL, CAPACIDAD) VALUES (1, "B11", 100);
INSERT INTO LOCAL(IDTIPOLOCAL, NOMBRELOCAL, CAPACIDAD) VALUES (1, "B41", 50);
INSERT INTO LOCAL(IDTIPOLOCAL, NOMBRELOCAL, CAPACIDAD) VALUES (2, "LCOMP-1", 25);
INSERT INTO LOCAL(IDTIPOLOCAL, NOMBRELOCAL, CAPACIDAD) VALUES (2, "LCOMP-2", 25);

--==============================================================
-- TABLAS RESERVAS
--==============================================================

-- EVENTOESPECIAL
 INSERT INTO EVENTOESPECIAL(IDEVENTOESPECIAL, IDCICLOMATERIA, NOMBREEVENTO, FECHAEVENTO, DESCRIPCIONEVENTO )
     VALUES (0,1,'VEAMOS','2020-06-25','sdfgd ');
-- DETALLERESERVA
DELETE FROM DETALLERESERVA;
INSERT INTO DETALLERESERVA(IDDETALLERESERVA,IDDIA,IDHORA,IDLOCAL,IDEVENTOESPECIAL,IDGRUPO,ESTADORESERVA,APROBADO,CUPO,INICIOPERIODORESERVA,FINPERIODORESERVA) VALUES (1,2,1,1,1,1,1,1,100,'2020-02-16','2020-07-16');
INSERT INTO DETALLERESERVA(IDDETALLERESERVA,IDDIA,IDHORA,IDLOCAL,IDEVENTOESPECIAL,IDGRUPO,ESTADORESERVA,APROBADO,CUPO,INICIOPERIODORESERVA,FINPERIODORESERVA) VALUES (2,4,1,1,1,2,1,1,100,'2020-02-16','2020-07-16');
INSERT INTO DETALLERESERVA(IDDETALLERESERVA,IDDIA,IDHORA,IDLOCAL,IDEVENTOESPECIAL,IDGRUPO,ESTADORESERVA,APROBADO,CUPO,INICIOPERIODORESERVA,FINPERIODORESERVA) VALUES (3,5,3,1,1,1,1,1,100,'2020-06-25','2020-07-25');
INSERT INTO DETALLERESERVA(IDDETALLERESERVA,IDDIA,IDHORA,IDLOCAL,IDEVENTOESPECIAL,IDGRUPO,ESTADORESERVA,APROBADO,CUPO,INICIOPERIODORESERVA,FINPERIODORESERVA) VALUES (4,5,4,1,1,1,1,1,100,'2020-06-25','2020-07-25');




-- SOLICITUD

-- RESERVA
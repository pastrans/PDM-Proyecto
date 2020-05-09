---=============================================================
---CAMBIOS
/* 
1. LAS LLAVES INTEGER SON AUTOINCREMENTABLES
2. CAMBIO DE NOMBRE DEL CAMPO "FECHAFINPERIADO" POR "FECHAFINFERIADO"
*/
--===============================================================
--==============================================================
-- Table: OPCIONCRUD
--==============================================================
create table OPCIONCRUD (
IDOPCION             CHAR(3)              not null,
DESOPCION            VARCHAR(30)          not null,
NUMCRUD              INTEGER              not null,
primary key (IDOPCION)
);

--==============================================================
-- Table: UNIDAD
--==============================================================
create table UNIDAD (
IDUNIDAD             INTEGER PRIMARY KEY AUTOINCREMENT,
NOMBREENT            VARCHAR(15)          not null,
DESCRIPCIONENT       VARCHAR(500)         not null,
PRIORIDAD            INTEGER              not null
);

--==============================================================
-- Table: USUARIO
--==============================================================
create table USUARIO (
IDUSUARIO            CHAR(2)              not null,
IDUNIDAD             INTEGER,
NOMBREUSUARIO        VARCHAR(30)          not null,
CLAVEUSUARIO         CHAR(5)              not null,
NOMBREPERSONAL       VARCHAR(30)          not null,
APELLIDOPERSONAL     VARCHAR(30)          not null,
CORREOPERSONAL       VARCHAR(50),
primary key (IDUSUARIO),
foreign key (IDUNIDAD)
      references UNIDAD (IDUNIDAD)
);

--==============================================================
-- Table: ACCESOUSUARIO
--==============================================================
create table ACCESOUSUARIO (
IDACCESOUSUARIO      INTEGER PRIMARY KEY AUTOINCREMENT,
IDOPCION             CHAR(3),
IDUSUARIO            CHAR(2),
foreign key (IDOPCION)
      references OPCIONCRUD (IDOPCION),
foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO)
);

--==============================================================
-- Index: ACCESOUSUARIO_PK
--==============================================================
create unique index ACCESOUSUARIO_PK on ACCESOUSUARIO (
IDACCESOUSUARIO ASC
);

--==============================================================
-- Index: RELATIONSHIP_23_FK
--==============================================================
create  index RELATIONSHIP_23_FK on ACCESOUSUARIO (
IDOPCION ASC
);

--==============================================================
-- Index: RELATIONSHIP_29_FK
--==============================================================
create  index RELATIONSHIP_29_FK on ACCESOUSUARIO (
IDUSUARIO ASC
);

--==============================================================
-- Table: CICLO
--==============================================================
create table CICLO (
IDCICLO              INTEGER PRIMARY KEY AUTOINCREMENT,
INICIO               DATE                 not null,
FIN                  DATE                 not null,
NOMBRECICLO          VARCHAR(20)          not null,
ESTADOCICLO          smallint             not null,
INICIOPERIODOCLASE   DATE                 not null,
FINPERIODOCLASE      DATE                 not null
);

--==============================================================
-- Index: CICLO_PK
--==============================================================
create unique index CICLO_PK on CICLO (
IDCICLO ASC
);

--==============================================================
-- Table: MATERIA
--==============================================================
create table MATERIA (
CODMATERIA           VARCHAR(6)           not null,
IDUNIDAD             INTEGER,
NOMBREMAT            VARCHAR(25)          not null,
MASIVA               smallint             not null,
primary key (CODMATERIA),
foreign key (IDUNIDAD)
      references UNIDAD (IDUNIDAD)
);

--==============================================================
-- Table: CICLOMATERIA
--==============================================================
create table CICLOMATERIA (
IDCICLOMATERIA       INTEGER PRIMARY KEY AUTOINCREMENT,
IDCICLO              INTEGER,
CODMATERIA           VARCHAR(6),
foreign key (IDCICLO)
      references CICLO (IDCICLO),
foreign key (CODMATERIA)
      references MATERIA (CODMATERIA)
);

--==============================================================
-- Index: CICLOMATERIA_PK
--==============================================================
create unique index CICLOMATERIA_PK on CICLOMATERIA (
IDCICLOMATERIA ASC
);

--==============================================================
-- Index: SE_DA_FK
--==============================================================
create  index SE_DA_FK on CICLOMATERIA (
IDCICLO ASC
);

--==============================================================
-- Index: IMPARTIDA_FK
--==============================================================
create  index IMPARTIDA_FK on CICLOMATERIA (
CODMATERIA ASC
);

--==============================================================
-- Index: CORDINA_FK
--==============================================================
create  index CORDINA_FK on CICLOMATERIA (
IDUSUARIO ASC
);

--==============================================================
-- Table: COORDINACION
--==============================================================
create table COORDINACION (
IDCOORDINACION       INTEGER PRIMARY KEY AUTOINCREMENT,
IDUSUARIO            CHAR(2),
IDCICLOMATERIA       INTEGER,
TIPOCOORDINACION     VARCHAR(30)          not null,
foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO),
foreign key (IDCICLOMATERIA)
      references CICLOMATERIA (IDCICLOMATERIA)
);

--==============================================================
-- Index: COORDINACION_PK
--==============================================================
create unique index COORDINACION_PK on COORDINACION (
IDCOORDINACION ASC
);

--==============================================================
-- Index: PERSONAL_REALIZA_COORDINACION_FK
--==============================================================
create  index PERSONAL_REALIZA_COORDINACION_FK on COORDINACION (
IDUSUARIO ASC
);

--==============================================================
-- Index: COORDINACION_DE_MATERIA_FK
--==============================================================
create  index COORDINACION_DE_MATERIA_FK on COORDINACION (
IDCICLOMATERIA ASC
);

--==============================================================
-- Table: DIA
--==============================================================
create table DIA (
IDDIA                INTEGER PRIMARY KEY AUTOINCREMENT,
NOMBREDIA            VARCHAR(25)          not null
);

--==============================================================
-- Table: HORARIO
--==============================================================
create table HORARIO (
IDHORA               INTEGER PRIMARY KEY AUTOINCREMENT,
HORAINICIO           TIME                 not null,
HORAFINAL            TIME                 not null
);

--==============================================================
-- Table: ENCARGADO
--==============================================================
create table ENCARGADO (
IDENCARGADO          INTEGER PRIMARY KEY AUTOINCREMENT,
IDUSUARIO            CHAR(2),
foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO)
);

--==============================================================
-- Table: TIPOLOCAL
--==============================================================
create table TIPOLOCAL (
IDTIPOLOCAL          INTEGER PRIMARY KEY AUTOINCREMENT,
IDENCARGADO          INTEGER,
NOMBRETIPO           VARCHAR(25)          not null,
foreign key (IDENCARGADO)
      references ENCARGADO (IDENCARGADO)
);

--==============================================================
-- Table: LOCAL
--==============================================================
create table LOCAL (
IDLOCAL              INTEGER PRIMARY KEY AUTOINCREMENT,
IDTIPOLOCAL          INTEGER,
NOMBRELOCAL          VARCHAR(25)          not null,
CAPACIDAD            INTEGER              not null,
foreign key (IDTIPOLOCAL)
      references TIPOLOCAL (IDTIPOLOCAL)
);

--==============================================================
-- Table: EVENTOESPECIAL
--==============================================================
create table EVENTOESPECIAL (
IDEVENTOESPECIAL     INTEGER PRIMARY KEY AUTOINCREMENT,
IDCICLOMATERIA       INTEGER,
NOMBREEVENTO         VARCHAR(100)         not null,
FECHAEVENTO          DATE                 not null,
DESCRIPCIONEVENTO    VARCHAR(300),
foreign key (IDCICLOMATERIA)
      references CICLOMATERIA (IDCICLOMATERIA)
);

--==============================================================
-- Table: TIPOGRUPO
--==============================================================
create table TIPOGRUPO (
IDTIPOGRUPO          INTEGER PRIMARY KEY AUTOINCREMENT,
NOMBRETIPOGRUPO      VARCHAR(25)          not null
);

--==============================================================
-- Table: GRUPO
--==============================================================
create table GRUPO (
IDGRUPO              INTEGER PRIMARY KEY AUTOINCREMENT,
IDTIPOGRUPO          INTEGER,
IDCICLOMATERIA       INTEGER,
NUMERO               INTEGER              not null,
foreign key (IDTIPOGRUPO)
      references TIPOGRUPO (IDTIPOGRUPO),
foreign key (IDCICLOMATERIA)
      references CICLOMATERIA (IDCICLOMATERIA)
);

--==============================================================
-- Table: DETALLERESERVA
--==============================================================
create table DETALLERESERVA (
IDDETALLERESERVA     INTEGER PRIMARY KEY AUTOINCREMENT,
IDDIA                INTEGER,
IDHORA               INTEGER,
IDLOCAL              INTEGER,
IDEVENTOESPECIAL     INTEGER,
IDGRUPO              INTEGER,
ESTADORESERVA        smallint             not null,
APROBADO             smallint             not null,
CUPO                 INTEGER              not null,
INICIOPERIODORESERVA DATE                 not null,
FINPERIODORESERVA    DATE                 not null,
foreign key (IDDIA)
      references DIA (IDDIA),
foreign key (IDHORA)
      references HORARIO (IDHORA),
foreign key (IDLOCAL)
      references LOCAL (IDLOCAL),
foreign key (IDEVENTOESPECIAL)
      references EVENTOESPECIAL (IDEVENTOESPECIAL),
foreign key (IDGRUPO)
      references GRUPO (IDGRUPO)
);

--==============================================================
-- Index: DETALLERESERVA_PK
--==============================================================
create unique index DETALLERESERVA_PK on DETALLERESERVA (
IDDETALLERESERVA ASC
);

--==============================================================
-- Index: SE_IMPARTE_FK
--==============================================================
create  index SE_IMPARTE_FK on DETALLERESERVA (
IDDIA ASC
);

--==============================================================
-- Index: CONTIENE_FK
--==============================================================
create  index CONTIENE_FK on DETALLERESERVA (
IDHORA ASC
);

--==============================================================
-- Index: SE_IMPARTE_EN_FK
--==============================================================
create  index SE_IMPARTE_EN_FK on DETALLERESERVA (
IDLOCAL ASC
);

--==============================================================
-- Index: REALIZA_FK
--==============================================================
create  index REALIZA_FK on DETALLERESERVA (
IDEVENTOESPECIAL ASC
);

--==============================================================
-- Index: GRUPO_TIENE_DETRESERVA_FK
--==============================================================
create  index GRUPO_TIENE_DETRESERVA_FK on DETALLERESERVA (
IDGRUPO ASC
);

--==============================================================
-- Index: DIA_PK
--==============================================================
create unique index DIA_PK on DIA (
IDDIA ASC
);

--==============================================================
-- Index: ENCARGADO_PK
--==============================================================
create unique index ENCARGADO_PK on ENCARGADO (
IDENCARGADO ASC
);

--==============================================================
-- Index: ES_FK
--==============================================================
create  index ES_FK on ENCARGADO (
IDUSUARIO ASC
);

--==============================================================
-- Index: EVENTOESPECIAL_PK
--==============================================================
create unique index EVENTOESPECIAL_PK on EVENTOESPECIAL (
IDEVENTOESPECIAL ASC
);

--==============================================================
-- Index: PERTENECE_FK
--==============================================================
create  index PERTENECE_FK on EVENTOESPECIAL (
IDCICLOMATERIA ASC
);

--==============================================================
-- Table: FERIADOS
--==============================================================
create table FERIADOS (
IDFERIADO            INTEGER PRIMARY KEY AUTOINCREMENT,
IDCICLO              INTEGER,
FECHAINICIOFERIADO   DATE                 not null,
FECHAFINFERIADO      DATE                 not null,
NOMBREFERIADO        VARCHAR(50)          not null,
DESCRIPCIONFERIADO   VARCHAR(500)         not null,
BLOQUEARRESERVAS     smallint             not null,
foreign key (IDCICLO)
      references CICLO (IDCICLO)
);

--==============================================================
-- Index: FERIADOS_PK
--==============================================================
create unique index FERIADOS_PK on FERIADOS (
IDFERIADO ASC
);

--==============================================================
-- Index: HAY_FK
--==============================================================
create  index HAY_FK on FERIADOS (
IDCICLO ASC
);

--==============================================================
-- Index: GRUPO_PK
--==============================================================
create unique index GRUPO_PK on GRUPO (
IDGRUPO ASC
);

--==============================================================
-- Index: ES_DE_FK
--==============================================================
create  index ES_DE_FK on GRUPO (
IDTIPOGRUPO ASC
);

--==============================================================
-- Index: REPARTIDA_FK
--==============================================================
create  index REPARTIDA_FK on GRUPO (
IDCICLOMATERIA ASC
);

--==============================================================
-- Index: HORARIO_PK
--==============================================================
create unique index HORARIO_PK on HORARIO (
IDHORA ASC
);

--==============================================================
-- Index: LOCAL_PK
--==============================================================
create unique index LOCAL_PK on LOCAL (
IDLOCAL ASC
);

--==============================================================
-- Index: ES_DE_UN_FK
--==============================================================
create  index ES_DE_UN_FK on LOCAL (
IDTIPOLOCAL ASC
);

--==============================================================
-- Index: MATERIA_PK
--==============================================================
create unique index MATERIA_PK on MATERIA (
CODMATERIA ASC
);

--==============================================================
-- Index: UNIDAD_IMPARTE_MATERIA_FK
--==============================================================
create  index UNIDAD_IMPARTE_MATERIA_FK on MATERIA (
IDUNIDAD ASC
);

--==============================================================
-- Index: OPCIONCRUD_PK
--==============================================================
create unique index OPCIONCRUD_PK on OPCIONCRUD (
IDOPCION ASC
);

--==============================================================
-- Table: SOLICITUD
--==============================================================
create table SOLICITUD (
IDSOLICITUD          INTEGER PRIMARY KEY AUTOINCREMENT,
IDUSUARIO            CHAR(2),
IDENCARGADO          INTEGER,
ASUNTOSOLICITUD      VARCHAR(100)         not null,
TIPOSOLICITUD        INTEGER              not null,
FECHAREALIZADA       DATE                 not null,
ESTADOSOLICITUD      smallint             not null,
FECHARESPUESTA       DATE,
COMETARIO            VARCHAR(500),
NUEVOFINPERIODO      DATE,
APROBADOTOTAL        smallint,
MOSTRARBOTON         smallint,
foreign key (IDUSUARIO)
      references USUARIO (IDUSUARIO),
foreign key (IDENCARGADO)
      references ENCARGADO (IDENCARGADO)
);

--==============================================================
-- Table: RESERVA
--==============================================================
create table RESERVA (
IDRESERVA            INTEGER PRIMARY KEY AUTOINCREMENT,
IDDETALLERESERVA     INTEGER,
IDSOLICITUD          INTEGER,
foreign key (IDDETALLERESERVA)
      references DETALLERESERVA (IDDETALLERESERVA),
foreign key (IDSOLICITUD)
      references SOLICITUD (IDSOLICITUD)
);

--==============================================================
-- Index: RESERVA_PK
--==============================================================
create unique index RESERVA_PK on RESERVA (
IDRESERVA ASC
);

--==============================================================
-- Index: RESERVA_TIENE_DETRESERVA_FK
--==============================================================
create  index RESERVA_TIENE_DETRESERVA_FK on RESERVA (
IDDETALLERESERVA ASC
);

--==============================================================
-- Index: SOLICITUD_TIENE_RESERVA_FK
--==============================================================
create  index SOLICITUD_TIENE_RESERVA_FK on RESERVA (
IDSOLICITUD ASC
);

--==============================================================
-- Index: SOLICITUD_PK
--==============================================================
create unique index SOLICITUD_PK on SOLICITUD (
IDSOLICITUD ASC
);

--==============================================================
-- Index: PERSONAL_REALIZA_SOLICITUD_FK
--==============================================================
create  index PERSONAL_REALIZA_SOLICITUD_FK on SOLICITUD (
IDUSUARIO ASC
);

--==============================================================
-- Index: ENCARGADO_RECIBE_SOLICITUD_FK
--==============================================================
create  index ENCARGADO_RECIBE_SOLICITUD_FK on SOLICITUD (
IDENCARGADO ASC
);

--==============================================================
-- Index: TIPOGRUPO_PK
--==============================================================
create unique index TIPOGRUPO_PK on TIPOGRUPO (
IDTIPOGRUPO ASC
);

--==============================================================
-- Index: TIPOLOCAL_PK
--==============================================================
create unique index TIPOLOCAL_PK on TIPOLOCAL (
IDTIPOLOCAL ASC
);

--==============================================================
-- Index: ESTA_A_CARGO_FK
--==============================================================
create  index ESTA_A_CARGO_FK on TIPOLOCAL (
IDENCARGADO ASC
);

--==============================================================
-- Index: UNIDAD_PK
--==============================================================
create unique index UNIDAD_PK on UNIDAD (
IDUNIDAD ASC
);

--==============================================================
-- Index: USUARIO_PK
--==============================================================
create unique index USUARIO_PK on USUARIO (
IDUSUARIO ASC
);

--==============================================================
-- Index: PERSONAL_PERTENECE_UNIDAD_FK
--==============================================================
create  index PERSONAL_PERTENECE_UNIDAD_FK on USUARIO (
IDUNIDAD ASC
);

DROP TRIGGER IF EXISTS fk_accesousuario_operacioncrud;
DROP TRIGGER IF EXISTS fk_accesousuario_usuario;
DROP TRIGGER IF EXISTS fk_ciclomateria_ciclo;
DROP TRIGGER IF EXISTS fk_ciclomateria_materia;
DROP TRIGGER IF EXISTS fk_coordinacion_ciclomateria;
DROP TRIGGER IF EXISTS fk_coordinacion_usuario;
DROP TRIGGER IF EXISTS fk_encargado_usuario;
DROP TRIGGER IF EXISTS fk_eventoespecial_ciclomateria;
DROP TRIGGER IF EXISTS fk_feriados_ciclo;
DROP TRIGGER IF EXISTS fk_grupo_ciclomateria;
DROP TRIGGER IF EXISTS fk_grupo_tipogrupo;
DROP TRIGGER IF EXISTS fk_local_tipolocal;
DROP TRIGGER IF EXISTS fk_materia_unidad;
DROP TRIGGER IF EXISTS fk_reserva_solicitud;
DROP TRIGGER IF EXISTS fk_solicitud_encargado;
DROP TRIGGER IF EXISTS fk_solicitud_usuario;
DROP TRIGGER IF EXISTS fk_usuario_unidad;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL LOCAL-TIPOLOCAL
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_local_tipolocal
BEFORE INSERT ON local
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDTIPOLOCAL FROM tipolocal WHERE IDTIPOLOCAL = NEW.IDTIPOLOCAL) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de local')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL RESERVA-SOLICITUD
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_reserva_solicitud
BEFORE INSERT ON reserva
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idsolicitud FROM solicitud WHERE idsolicitud = NEW.idsolicitud) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de local')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL COORDINACION-USUARIO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_coordinacion_usuario
BEFORE INSERT ON coordinacion
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idusuario FROM usuario WHERE idusuario = NEW.idusuario) IS NULL)
      THEN RAISE(ABORT, 'No existe el usuario.')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL COORDINACION-CICLOMATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_coordinacion_ciclomateria
BEFORE INSERT ON coordinacion
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(ABORT, 'La materia no se está impartiendo en ese ciclo.')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL CICLOMATERIA-MATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_ciclomateria_materia
BEFORE INSERT ON ciclomateria
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT codmateria FROM materia WHERE codmateria = NEW.codmateria) IS NULL)
      THEN RAISE(ABORT, 'No existe la materia.')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL CICLOMATERIA-CICLO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_ciclomateria_ciclo
BEFORE INSERT ON ciclomateria
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclo FROM ciclo WHERE idciclo = NEW.idciclo) IS NULL)
      THEN RAISE(ABORT, 'No existe el ciclo.')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL FERIADO-CICLO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_feriados_ciclo
BEFORE INSERT ON feriados
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclo FROM ciclo WHERE idciclo = NEW.idciclo) IS NULL)
      THEN RAISE(ABORT, 'No existe el ciclo.')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL GRUPO-TIPOGRUPO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_grupo_tipogrupo
BEFORE INSERT ON grupo
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idTipoGrupo FROM tipogrupo WHERE idTipoGrupo = NEW.idTipoGrupo) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de grupo.')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL GRUPO-CICLOMATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_grupo_ciclomateria
BEFORE INSERT ON grupo
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de grupo.')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL EVENTOESPECIAL-CICLOMATERIA
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_eventoespecial_ciclomateria
BEFORE INSERT ON eventoespecial
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idciclomateria FROM ciclomateria WHERE idciclomateria = NEW.idciclomateria) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de grupo.')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL SOLICITUD-ENCARGADO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_solicitud_encargado
BEFORE INSERT ON solicitud
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDENCARGADO FROM ENCARGADO WHERE IDENCARGADO = NEW.IDENCARGADO) IS NULL)
      THEN RAISE(ABORT, 'No existe el encargado')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL SOLICITUD-USUARIO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_solicitud_usuario
BEFORE INSERT ON solicitud
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUSUARIO FROM USUARIO WHERE IDUSUARIO = NEW.IDUSUARIO) IS NULL)
      THEN RAISE(ABORT, 'No existe el usuario')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL ENCARGADO-USUARIO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_encargado_usuario
BEFORE INSERT ON encargado
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUSUARIO FROM USUARIO WHERE IDUSUARIO = NEW.IDUSUARIO) IS NULL)
      THEN RAISE(ABORT, 'No existe el usuario')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL ACCESOUSUARIO-USUARIO
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_accesousuario_usuario
BEFORE INSERT ON accesousuario
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUSUARIO FROM USUARIO WHERE IDUSUARIO = NEW.IDUSUARIO) IS NULL)
      THEN RAISE(ABORT, 'No existe el usuario')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL ACCESOUSUARIO-OPERACIONCRUD
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_accesousuario_operacioncrud
BEFORE INSERT ON accesousuario
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDOPCION FROM OPERACIONCRUD WHERE IDOPCION = NEW.IDOPCION) IS NULL)
      THEN RAISE(ABORT, 'No existe la operacion')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL USUARIO-UNIDAD
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_usuario_unidad
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUNIDAD FROM UNIDAD WHERE IDUNIDAD = NEW.IDUNIDAD) IS NULL)
      THEN RAISE(ABORT, 'No existe la unidad')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL MATERIA-UNIDAD
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_materia_unidad
BEFORE INSERT ON materia
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDUNIDAD FROM UNIDAD WHERE IDUNIDAD = NEW.IDUNIDAD) IS NULL)
      THEN RAISE(ABORT, 'No existe la unidad')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL LOCAL-TIPOLOCAL
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_local_tipolocal
BEFORE INSERT ON local
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT IDTIPOLOCAL FROM tipolocal WHERE IDTIPOLOCAL = NEW.IDTIPOLOCAL) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de local')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL DetalleReserva-Dia
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_DetalleReserva_Dia
BEFORE INSERT ON dia
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idDia FROM DetalleReserva WHERE idDia = NEW.idDia) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de día')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL DetalleReserva-Grupo
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_DetalleReserva_Grupo
BEFORE INSERT ON grupo
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idGrupo FROM DetalleReserva WHERE idGrupo = NEW.idGrupo) IS NULL)
      THEN RAISE(ABORT, 'No existe el tipo de día')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL DetalleReserva-EventoEspecial
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_DetalleReserva_EventoEspecial
BEFORE INSERT ON eventoespecial
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT ideventoespecial FROM DetalleReserva WHERE ideventoespecial = NEW.ideventoespecial) IS NULL)
      THEN RAISE(ABORT, 'No existe evento especial')
END;
END;

--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL DetalleReserva-Horario
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_DetalleReserva_Horario
BEFORE INSERT ON horario
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idhorario FROM DetalleReserva WHERE idhorario = NEW.idhorario) IS NULL)
      THEN RAISE(ABORT, 'No existe ese horario')
END;
END;
--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL DetalleReserva-Local
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_DetalleReserva_Local
BEFORE INSERT ON Local
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idLocal FROM DetalleReserva WHERE idLocal = NEW.idLocal) IS NULL)
      THEN RAISE(ABORT, 'No existe ese local')
END;
END;


--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL TipoLocal-Encargado
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_TipoLocal_Encargado
BEFORE INSERT ON Encargado
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idEncargado FROM TipoLocal WHERE idEncargado = NEW.idLocal) IS NULL)
      THEN RAISE(ABORT, 'No existe ese tipo de local')
END;
END;


--==============================================================
-- TRIGGER INTEGRIDAD REFERENCIAL Reserva-DetalleReserva
--==============================================================
CREATE TRIGGER IF NOT EXISTS fk_Reserva_DetalleReserva
BEFORE INSERT ON DetalleReserva
FOR EACH ROW
BEGIN
      SELECT CASE
      WHEN ((SELECT idDetalleReserva FROM Reserva WHERE idDetalleReserva = NEW.idDetalleReserva) IS NULL)
      THEN RAISE(ABORT, 'No existe ese detalle de reserva')
END;
END;

--=============================================================
-- HABILITANDO LAS LLAVES FORÁNEAS
--=============================================================
--==============================================================
-- COMANDO A EJECUTAR EN ANDROID PARA LAS LLAVES FORÁNEAS
--==============================================================
PRAGMA foreign_keys = ON;
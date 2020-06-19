--==============================================================
-- TABLA ANDROID
--==============================================================

DROP TABLE IF EXISTS android_metadata;
CREATE TABLE android_metadata (locale TEXT DEFAULT 'en_US');
INSERT INTO android_metadata VALUES ('en_US');

--==============================================================
-- TABLAS CATÁLOGO
--==============================================================

-- DIA
DROP TABLE IF EXISTS DIA;
create table DIA (
    IDDIA INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBREDIA VARCHAR(25) not null
);

-- HORARIO
DROP TABLE IF EXISTS HORARIO;
create table HORARIO (
    IDHORA INTEGER PRIMARY KEY AUTOINCREMENT,
    HORAINICIO VARCHAR(5) not null,
    HORAFINAL VARCHAR(5) not null
);

-- OPCIONCRUD
DROP TABLE IF EXISTS OPCIONCRUD;
create table OPCIONCRUD (
    IDOPCION CHAR(3) not null,
    DESOPCION VARCHAR(30) not null,
    NUMCRUD INTEGER not null,
    primary key (IDOPCION)
);

--==============================================================
-- TABLAS ADMINISTRACIÓN ACADÉMICA
--==============================================================

-- CICLO
DROP TABLE IF EXISTS CICLO;
create table CICLO (
    IDCICLO INTEGER PRIMARY KEY AUTOINCREMENT,
    INICIO VARCHAR(10) not null,
    FIN VARCHAR(10) not null,
    NOMBRECICLO VARCHAR(20) not null,
    ESTADOCICLO smallint not null,
    INICIOPERIODOCLASE VARCHAR(10) not null,
    FINPERIODOCLASE VARCHAR(10) not null
);

-- FERIADO
DROP TABLE IF EXISTS FERIADO;
create table FERIADO (
    IDFERIADO INTEGER PRIMARY KEY AUTOINCREMENT,
    IDCICLO INTEGER,
    FECHAINICIOFERIADO VARCHAR(10) not null,
    FECHAFINFERIADO VARCHAR(10),
    NOMBREFERIADO VARCHAR(50) not null,
    DESCRIPCIONFERIADO VARCHAR(150) not null,
    BLOQUEARRESERVAS smallint not null,
    foreign key (IDCICLO) references CICLO (IDCICLO)
);

-- UNIDAD
DROP TABLE IF EXISTS UNIDAD;
create table UNIDAD (
    IDUNIDAD INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBREENT VARCHAR(15) not null,
    DESCRIPCIONENT VARCHAR(500)not null
);

-- MATERIA
DROP TABLE IF EXISTS MATERIA;
create table MATERIA (
    CODMATERIA VARCHAR(6) not null,
    IDUNIDAD INTEGER,
    NOMBREMAT VARCHAR(25) not null,
    MASIVA smallint not null,
    primary key (CODMATERIA),
    foreign key (IDUNIDAD) references UNIDAD (IDUNIDAD)
);

-- CICLOMATERIA 
DROP TABLE IF EXISTS CICLOMATERIA;
create table CICLOMATERIA (
    IDCICLOMATERIA INTEGER PRIMARY KEY AUTOINCREMENT,
    IDCICLO INTEGER,
    CODMATERIA VARCHAR(6),
    foreign key (IDCICLO) references CICLO (IDCICLO),
    foreign key (CODMATERIA) references MATERIA (CODMATERIA)
);

-- TIPOGRUPO 
DROP TABLE IF EXISTS TIPOGRUPO;
create table TIPOGRUPO (
    IDTIPOGRUPO INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBRETIPOGRUPO VARCHAR(25) not null
);

-- GRUPO
DROP TABLE IF EXISTS GRUPO;
create table GRUPO (
    IDGRUPO INTEGER PRIMARY KEY AUTOINCREMENT,
    IDTIPOGRUPO INTEGER,
    IDCICLOMATERIA INTEGER,
    NUMERO INTEGER not null,
    foreign key (IDTIPOGRUPO) references TIPOGRUPO (IDTIPOGRUPO),
    foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA)
);

--==============================================================
-- TABLAS USUARIO
--==============================================================

--ROL
DROP TABLE IF EXISTS ROL;
create table rol(
    IDROL INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBREROL VARCHAR(50) not null
);

-- ACCESOUSUARIO
DROP TABLE IF EXISTS ACCESOUSUARIO;
create table ACCESOUSUARIO (
    IDACCESOUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT,
    IDOPCION CHAR(3),
    IDROL INTEGER,
    foreign key (IDOPCION) references OPCIONCRUD (IDOPCION),
    foreign key (IDROL) references ROL (IDROL)
);

-- USUARIO
DROP TABLE IF EXISTS USUARIO;
create table USUARIO (
    IDUSUARIO CHAR(5) not null,
    IDUNIDAD INTEGER,
    IDROL INTEGER,
    NOMBREUSUARIO VARCHAR(30) not null,
    CLAVEUSUARIO CHAR(20) not null,
    NOMBREPERSONAL VARCHAR(30) not null,
    APELLIDOPERSONAL VARCHAR(30) not null,
    CORREOPERSONAL VARCHAR(50),
    primary key (IDUSUARIO),
    foreign key (IDUNIDAD) references UNIDAD (IDUNIDAD),
    foreign key (IDROL) references ROL (IDROL)
);

-- COORDINACION
DROP TABLE IF EXISTS COORDINACION;
create table COORDINACION (
    IDCOORDINACION INTEGER PRIMARY KEY AUTOINCREMENT,
    IDUSUARIO CHAR(5),
    IDCICLOMATERIA INTEGER,
    TIPOCOORDINACION VARCHAR(30) not null,
    foreign key (IDUSUARIO) references USUARIO (IDUSUARIO),
    foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA)
);

--==============================================================
-- TABLAS LOCALES
--==============================================================

-- TIPOLOCAL
DROP TABLE IF EXISTS TIPOLOCAL;
create table TIPOLOCAL (
    IDTIPOLOCAL INTEGER PRIMARY KEY AUTOINCREMENT,
    IDENCARGADO CHAR(5),
    NOMBRETIPO VARCHAR(25) not null,
    foreign key (IDENCARGADO) references USUARIO (IDUSUARIO)
);

-- LOCAL
DROP TABLE IF EXISTS LOCAL;
create table LOCAL (
    IDLOCAL INTEGER PRIMARY KEY AUTOINCREMENT,
    IDTIPOLOCAL INTEGER,
    NOMBRELOCAL VARCHAR(25) not null,
    CAPACIDAD INTEGER not null,
    foreign key (IDTIPOLOCAL) references TIPOLOCAL (IDTIPOLOCAL)
);

--==============================================================
-- TABLAS RESERVAS
--==============================================================

-- EVENTOESPECIAL
DROP TABLE IF EXISTS EVENTOESPECIAL;
create table EVENTOESPECIAL (
    IDEVENTOESPECIAL INTEGER PRIMARY KEY AUTOINCREMENT,
    IDCICLOMATERIA INTEGER,
    NOMBREEVENTO VARCHAR(100) not null,
    FECHAEVENTO VARCHAR(10) not null,
    DESCRIPCIONEVENTO VARCHAR(300),
    foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA)
);

-- DETALLERESERVA
DROP TABLE IF EXISTS DETALLERESERVA;
create table DETALLERESERVA (
    IDDETALLERESERVA INTEGER PRIMARY KEY AUTOINCREMENT,
    IDDIA INTEGER,
    IDHORA INTEGER,
    IDLOCAL INTEGER,
    IDEVENTOESPECIAL INTEGER,
    IDGRUPO INTEGER,
    ESTADORESERVA smallint not null,
    APROBADO smallint not null,
    CUPO INTEGER not null,
    INICIOPERIODORESERVA VARCHAR(10) not null,
    FINPERIODORESERVA VARCHAR(10)  not null,
    foreign key (IDDIA) references DIA (IDDIA),
    foreign key (IDHORA) references HORARIO (IDHORA),
    foreign key (IDLOCAL) references LOCAL (IDLOCAL),
    foreign key (IDEVENTOESPECIAL)references EVENTOESPECIAL (IDEVENTOESPECIAL),
    foreign key (IDGRUPO) references GRUPO (IDGRUPO)
);

-- SOLICITUD
DROP TABLE IF EXISTS SOLICITUD;
create table SOLICITUD (
    IDSOLICITUD INTEGER PRIMARY KEY AUTOINCREMENT,
    IDUSUARIO CHAR(5),
    IDENCARGADO CHAR(5),
    ASUNTOSOLICITUD VARCHAR(100) not null,
    TIPOSOLICITUD INTEGER not null,
    FECHAREALIZADA VARCHAR(10)  not null,
    ESTADOSOLICITUD smallint not null,
    FECHARESPUESTA VARCHAR(10),
    COMETARIO VARCHAR(500),
    NUEVOFINPERIODO VARCHAR(10),
    APROBADOTOTAL smallint,
    MOSTRARBOTON smallint,
    foreign key (IDUSUARIO) references USUARIO (IDUSUARIO),
    foreign key (IDENCARGADO) references USUARIO (IDUSUARIO)
);

-- RESERVA
DROP TABLE IF EXISTS RESERVA;
create table RESERVA (
    IDRESERVA INTEGER PRIMARY KEY AUTOINCREMENT,
    IDDETALLERESERVA INTEGER,
    IDSOLICITUD INTEGER,
    foreign key (IDDETALLERESERVA) references DETALLERESERVA (IDDETALLERESERVA),
    foreign key (IDSOLICITUD) references SOLICITUD (IDSOLICITUD)
);
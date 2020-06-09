DROP TABLE IF EXISTS android_metadata;
CREATE TABLE android_metadata (locale TEXT DEFAULT 'en_US');
INSERT INTO android_metadata VALUES ('en_US');

DROP TABLE IF EXISTS DIA;
create table DIA (
    IDDIA INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBREDIA VARCHAR(25) not null
);

DROP TABLE IF EXISTS OPCIONCRUD;
create table OPCIONCRUD (
    IDOPCION CHAR(3) not null,
    DESOPCION VARCHAR(30) not null,
    NUMCRUD INTEGER not null,
    primary key (IDOPCION)
);

DROP TABLE IF EXISTS UNIDAD;
create table UNIDAD (
    IDUNIDAD INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBREENT VARCHAR(15) not null,
    DESCRIPCIONENT VARCHAR(500)not null,
    PRIORIDAD INTEGER not null
);

DROP TABLE IF EXISTS ROL;
create table rol(
    IDROL INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBREROL VARCHAR(50) not null
);

DROP TABLE IF EXISTS USUARIO;
create table USUARIO (
    IDUSUARIO CHAR(2) not null,
    IDUNIDAD INTEGER,
    IDROL INTEGER,
    NOMBREUSUARIO VARCHAR(30) not null,
    CLAVEUSUARIO CHAR(5) not null,
    NOMBREPERSONAL VARCHAR(30) not null,
    APELLIDOPERSONAL VARCHAR(30) not null,
    CORREOPERSONAL VARCHAR(50),
    primary key (IDUSUARIO),
    foreign key (IDUNIDAD) references UNIDAD (IDUNIDAD),
    foreign key (IDROL) references ROL (IDROL)
);

DROP TABLE IF EXISTS ACCESOUSUARIO;
create table ACCESOUSUARIO (
    IDACCESOUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT,
    IDOPCION CHAR(3),
    IDROL INTEGER,
    foreign key (IDOPCION) references OPCIONCRUD (IDOPCION),
    foreign key (IDROL) references ROL (IDROL)
);

DROP TABLE IF EXISTS CICLO;
create table CICLO (
    IDCICLO INTEGER PRIMARY KEY AUTOINCREMENT,
    INICIO DATE not null,
    FIN DATE not null,
    NOMBRECICLO VARCHAR(20) not null,
    ESTADOCICLO smallint not null,
    INICIOPERIODOCLASE DATE not null,
    FINPERIODOCLASE DATE not null
);

DROP TABLE IF EXISTS MATERIA;
create table MATERIA (
    CODMATERIA VARCHAR(6) not null,
    IDUNIDAD INTEGER,
    NOMBREMAT VARCHAR(25) not null,
    MASIVA smallint not null,
    primary key (CODMATERIA),
    foreign key (IDUNIDAD) references UNIDAD (IDUNIDAD)
);

DROP TABLE IF EXISTS CICLOMATERIA;
create table CICLOMATERIA (
    IDCICLOMATERIA INTEGER PRIMARY KEY AUTOINCREMENT,
    IDCICLO INTEGER,
    CODMATERIA VARCHAR(6),
    foreign key (IDCICLO) references CICLO (IDCICLO),
    foreign key (CODMATERIA) references MATERIA (CODMATERIA)
);

DROP TABLE IF EXISTS COORDINACION;
create table COORDINACION (
    IDCOORDINACION INTEGER PRIMARY KEY AUTOINCREMENT,
    IDUSUARIO CHAR(2),
    IDCICLOMATERIA INTEGER,
    TIPOCOORDINACION VARCHAR(30) not null,
    foreign key (IDUSUARIO) references USUARIO (IDUSUARIO),
    foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA)
);

DROP TABLE IF EXISTS HORARIO;
create table HORARIO (
    IDHORA INTEGER PRIMARY KEY AUTOINCREMENT,
    HORAINICIO TIME not null,
    HORAFINAL TIME not null
);

DROP TABLE IF EXISTS TIPOLOCAL;
create table TIPOLOCAL (
    IDTIPOLOCAL INTEGER PRIMARY KEY AUTOINCREMENT,
    IDENCARGADO CHAR(2),
    NOMBRETIPO VARCHAR(25) not null,
    foreign key (IDENCARGADO) references USUARIO (IDUSUARIO)
);

DROP TABLE IF EXISTS "LOCAL";
create table "LOCAL" (
    IDLOCAL INTEGER PRIMARY KEY AUTOINCREMENT,
    IDTIPOLOCAL INTEGER,
    NOMBRELOCAL VARCHAR(25) not null,
    CAPACIDAD INTEGER not null,
    foreign key (IDTIPOLOCAL) references TIPOLOCAL (IDTIPOLOCAL)
);

DROP TABLE IF EXISTS EVENTOESPECIAL;
create table EVENTOESPECIAL (
    IDEVENTOESPECIAL INTEGER PRIMARY KEY AUTOINCREMENT,
    IDCICLOMATERIA INTEGER,
    NOMBREEVENTO VARCHAR(100) not null,
    FECHAEVENTO DATE not null,
    DESCRIPCIONEVENTO VARCHAR(300),
    foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA)
);

DROP TABLE IF EXISTS TIPOGRUPO;
create table TIPOGRUPO (
    IDTIPOGRUPO INTEGER PRIMARY KEY AUTOINCREMENT,
    NOMBRETIPOGRUPO VARCHAR(25) not null
);

DROP TABLE IF EXISTS GRUPO;
create table GRUPO (
    IDGRUPO INTEGER PRIMARY KEY AUTOINCREMENT,
    IDTIPOGRUPO INTEGER,
    IDCICLOMATERIA INTEGER,
    NUMERO INTEGER not null,
    foreign key (IDTIPOGRUPO) references TIPOGRUPO (IDTIPOGRUPO),
    foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA)
);

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
    INICIOPERIODORESERVA DATE not null,
    FINPERIODORESERVA DATE  not null,
    foreign key (IDDIA) references DIA (IDDIA),
    foreign key (IDHORA) references HORARIO (IDHORA),
    foreign key (IDLOCAL) references LOCAL (IDLOCAL),
    foreign key (IDEVENTOESPECIAL)references EVENTOESPECIAL (IDEVENTOESPECIAL),
    foreign key (IDGRUPO) references GRUPO (IDGRUPO)
);

DROP TABLE IF EXISTS FERIADOS;
create table FERIADOS (
    IDFERIADO INTEGER PRIMARY KEY AUTOINCREMENT,
    IDCICLO INTEGER,
    FECHAINICIOFERIADO DATE not null,
    FECHAFINFERIADO DATE not null,
    NOMBREFERIADO VARCHAR(50) not null,
    DESCRIPCIONFERIADO VARCHAR(500) not null,
    BLOQUEARRESERVAS smallint not null,
    foreign key (IDCICLO) references CICLO (IDCICLO)
);

DROP TABLE IF EXISTS SOLICITUD;
create table SOLICITUD (
    IDSOLICITUD INTEGER PRIMARY KEY AUTOINCREMENT,
    IDUSUARIO CHAR(2),
    IDENCARGADO CHAR(2),
    ASUNTOSOLICITUD VARCHAR(100) not null,
    TIPOSOLICITUD INTEGER not null,
    FECHAREALIZADA DATE  not null,
    ESTADOSOLICITUD smallint not null,
    FECHARESPUESTA DATE,
    COMETARIO VARCHAR(500),
    NUEVOFINPERIODO DATE,
    APROBADOTOTAL smallint,
    MOSTRARBOTON smallint,
    foreign key (IDUSUARIO) references USUARIO (IDUSUARIO),
    foreign key (IDENCARGADO) references USUARIO (IDUSUARIO)
);

DROP TABLE IF EXISTS RESERVA;
create table RESERVA (
    IDRESERVA INTEGER PRIMARY KEY AUTOINCREMENT,
    IDDETALLERESERVA INTEGER,
    IDSOLICITUD INTEGER,
    foreign key (IDDETALLERESERVA) references DETALLERESERVA (IDDETALLERESERVA),
    foreign key (IDSOLICITUD) references SOLICITUD (IDSOLICITUD)
);
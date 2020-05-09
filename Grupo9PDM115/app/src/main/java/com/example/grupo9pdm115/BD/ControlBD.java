package com.example.grupo9pdm115.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBD {
    // Manejo de la BD
    private final Context context; // Almacenara el context de nuestra Activity
    private DatabaseHelper DBHelper; // Nuestro Auxiliador de BD
    private SQLiteDatabase db; // Instancia de nuestra BD

    // Métodos
    // Constructor
    public ControlBD(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    // Clase DatabaseHelper
    private static class DatabaseHelper extends SQLiteOpenHelper {
        // Atributos
        private static final String BASE_DATOS = "reservalocales.s3db"; // Nombre de la BD
        private static final int VERSION = 1; // Número de versión

        // Métodos
        // Constructor
        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /* SUPONGO QUE HAY QUE AGREGAR LAS CONSULTAS DE CREATE DE TODAS LAS TABLAS //SÍ BRO*/

            try{
                db.execSQL("create table OPCIONCRUD (IDOPCION CHAR(3) not null,DESOPCION VARCHAR(30) not null, NUMCRUD INTEGER not null,primary key (IDOPCION));");
                db.execSQL("create table UNIDAD (IDUNIDAD INTEGER PRIMARY KEY AUTOINCREMENT, NOMBREENT VARCHAR(15) not null, DESCRIPCIONENT VARCHAR(500)not null, PRIORIDAD INTEGER not null);");
                db.execSQL("create table USUARIO (IDUSUARIO CHAR(2) not null,IDUNIDAD INTEGER,NOMBREUSUARIO VARCHAR(30) not null,CLAVEUSUARIO CHAR(5) not null,NOMBREPERSONAL VARCHAR(30) not null,APELLIDOPERSONAL VARCHAR(30) not null,CORREOPERSONAL VARCHAR(50),primary key (IDUSUARIO),foreign key (IDUNIDAD) references UNIDAD (IDUNIDAD));");
                db.execSQL("create table ACCESOUSUARIO (IDACCESOUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT,IDOPCION CHAR(3),IDUSUARIO CHAR(2),foreign key (IDOPCION) references OPCIONCRUD (IDOPCION),foreign key (IDUSUARIO) references USUARIO (IDUSUARIO));");
                db.execSQL("create table CICLO (IDCICLO INTEGER PRIMARY KEY AUTOINCREMENT,INICIO DATE not null,FIN DATE not null, NOMBRECICLO VARCHAR(20) not null, ESTADOCICLO smallint not null, INICIOPERIODOCLASE DATE not null,FINPERIODOCLASE DATE not null);");
                db.execSQL("create table MATERIA (CODMATERIA VARCHAR(6) not null,IDUNIDAD INTEGER,NOMBREMAT VARCHAR(25) not null, MASIVA smallint not null,primary key (CODMATERIA),foreign key (IDUNIDAD) references UNIDAD (IDUNIDAD));");
                db.execSQL("create table CICLOMATERIA (IDCICLOMATERIA INTEGER PRIMARY KEY AUTOINCREMENT,IDCICLO INTEGER,CODMATERIA VARCHAR(6),foreign key (IDCICLO) references CICLO (IDCICLO),foreign key (CODMATERIA) references MATERIA (CODMATERIA));");
                db.execSQL("create table COORDINACION (IDCOORDINACION INTEGER PRIMARY KEY AUTOINCREMENT,IDUSUARIO CHAR(2),IDCICLOMATERIA INTEGER,TIPOCOORDINACION VARCHAR(30) not null,foreign key (IDUSUARIO) references USUARIO (IDUSUARIO),foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA));");
                db.execSQL("create table DIA (IDDIA INTEGER PRIMARY KEY AUTOINCREMENT,NOMBREDIA VARCHAR(25) not null);");
                db.execSQL("create table HORARIO (IDHORA INTEGER PRIMARY KEY AUTOINCREMENT,HORAINICIO TIME not null,HORAFINAL TIME not null);");
                db.execSQL("create table ENCARGADO (IDENCARGADO INTEGER PRIMARY KEY AUTOINCREMENT,IDUSUARIO CHAR(2),foreign key (IDUSUARIO) references USUARIO (IDUSUARIO));");
                db.execSQL("create table TIPOLOCAL (IDTIPOLOCAL INTEGER PRIMARY KEY AUTOINCREMENT,IDENCARGADO INTEGER,NOMBRETIPO VARCHAR(25) not null,foreign key (IDENCARGADO) references ENCARGADO (IDENCARGADO));");
                db.execSQL("create table LOCAL (IDLOCAL INTEGER PRIMARY KEY AUTOINCREMENT,IDTIPOLOCAL INTEGER,NOMBRELOCAL VARCHAR(25) not null,CAPACIDAD INTEGER not null,foreign key (IDTIPOLOCAL) references TIPOLOCAL (IDTIPOLOCAL));");
                db.execSQL("create table EVENTOESPECIAL (IDEVENTOESPECIAL INTEGER PRIMARY KEY AUTOINCREMENT,IDCICLOMATERIA INTEGER,NOMBREEVENTO VARCHAR(100) not null,FECHAEVENTO DATE not null,DESCRIPCIONEVENTO VARCHAR(300),foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA));");
                db.execSQL("create table TIPOGRUPO (IDTIPOGRUPO INTEGER PRIMARY KEY AUTOINCREMENT,NOMBRETIPOGRUPO VARCHAR(25) not null);");
                db.execSQL("create table GRUPO (IDGRUPO INTEGER PRIMARY KEY AUTOINCREMENT,IDTIPOGRUPO INTEGER,IDCICLOMATERIA INTEGER,NUMERO INTEGER not null,foreign key (IDTIPOGRUPO) references TIPOGRUPO (IDTIPOGRUPO),foreign key (IDCICLOMATERIA) references CICLOMATERIA (IDCICLOMATERIA));");
                db.execSQL("create table DETALLERESERVA (IDDETALLERESERVA INTEGER PRIMARY KEY AUTOINCREMENT,IDDIA INTEGER, IDHORA INTEGER, IDLOCAL INTEGER, IDEVENTOESPECIAL INTEGER, IDGRUPO INTEGER, ESTADORESERVA smallint not null, APROBADO smallint not null, CUPO INTEGER not null, INICIOPERIODORESERVA DATE not null, FINPERIODORESERVA DATE  not null,foreign key (IDDIA) references DIA (IDDIA),foreign key (IDHORA) references HORARIO (IDHORA),foreign key (IDLOCAL) references LOCAL (IDLOCAL),foreign key (IDEVENTOESPECIAL)references EVENTOESPECIAL (IDEVENTOESPECIAL),foreign key (IDGRUPO) references GRUPO (IDGRUPO));");
                db.execSQL("create table FERIADOS (IDFERIADO INTEGER PRIMARY KEY AUTOINCREMENT,IDCICLO INTEGER,FECHAINICIOFERIADO DATE not null,FECHAFINFERIADO DATE not null,NOMBREFERIADO VARCHAR(50) not null,DESCRIPCIONFERIADO VARCHAR(500) not null,BLOQUEARRESERVAS smallint not null,foreign key (IDCICLO) references CICLO (IDCICLO));");
                db.execSQL("create table SOLICITUD (IDSOLICITUD INTEGER PRIMARY KEY AUTOINCREMENT,IDUSUARIO CHAR(2),IDENCARGADO INTEGER, ASUNTOSOLICITUD VARCHAR(100) not null, TIPOSOLICITUD INTEGER not null, FECHAREALIZADA DATE  not null, ESTADOSOLICITUD smallint not null, FECHARESPUESTA DATE, COMETARIO VARCHAR(500), NUEVOFINPERIODO DATE, APROBADOTOTAL smallint, MOSTRARBOTON smallint, foreign key (IDUSUARIO) references USUARIO (IDUSUARIO),foreign key (IDENCARGADO) references ENCARGADO (IDENCARGADO));");
                db.execSQL("create table RESERVA (IDRESERVA INTEGER PRIMARY KEY AUTOINCREMENT,IDDETALLERESERVA INTEGER,IDSOLICITUD INTEGER,foreign key (IDDETALLERESERVA) references DETALLERESERVA (IDDETALLERESERVA),foreign key (IDSOLICITUD) references SOLICITUD (IDSOLICITUD));");
            }catch(SQLException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }

    // Abrir la BD
    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }

    // Cerrar la BD
    public void cerrar(){
        DBHelper.close();
    }

    // Insertar
    public String insertar(String nombreTabla, ContentValues valores){
        String regInsertados = "Registro Insertado Nº= ";
        long contador = 0;

        contador=db.insert(nombreTabla, null, valores);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            regInsertados = regInsertados+contador;
        }

        return regInsertados;
    }
}
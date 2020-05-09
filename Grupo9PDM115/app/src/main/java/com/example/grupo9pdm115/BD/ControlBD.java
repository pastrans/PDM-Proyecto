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
                db.execSQL("CREATE TABLE alumno(carnet VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(30),apellido VARCHAR(30),sexo VARCHAR(1),matganadas INTEGER);");
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
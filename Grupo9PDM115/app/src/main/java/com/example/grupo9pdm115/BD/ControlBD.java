package com.example.grupo9pdm115.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.grupo9pdm115.Utilidades.FileHelper;

import java.io.IOException;
import java.io.InputStream;

public class ControlBD {
    // Manejo de la BD
    private final Context context; // Almacenara el context de nuestra Activity
    private DatabaseHelper DBHelper; // Nuestro Auxiliador de BD
    private SQLiteDatabase db; // Instancia de nuestra BD

    public SQLiteDatabase getDb(){
        return this.db;
    }

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
        private final Context contextDBH;

        // Métodos
        // Constructor
        public DatabaseHelper(Context context) {
            super(context, BASE_DATOS, null, VERSION);
            this.contextDBH = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                // Ejecutar script para crear bd
                InputStream is = contextDBH.getResources().getAssets().open("crear_bd.sql");

                String[] instrucciones = FileHelper.parseSqlFile(is);
                db.beginTransaction();
                try {
                    for (String instruccion : instrucciones) {
                        db.execSQL(instruccion);
                    }
                    db.execSQL("DROP TRIGGER IF EXISTS delete_cascade_solicitud;");
                    db.execSQL("CREATE TRIGGER delete_cascade_solicitud AFTER DELETE ON [SOLICITUD] BEGIN DELETE FROM RESERVA WHERE IDSOLICITUD = OLD.IDSOLICITUD; END");
                    db.execSQL("DROP TRIGGER IF EXISTS delete_cascade_reserva;");
                    db.execSQL("CREATE TRIGGER delete_cascade_reserva AFTER DELETE ON [RESERVA] BEGIN DELETE FROM DETALLERESERVA WHERE IDDETALLERESERVA = OLD.IDDETALLERESERVA; END");
                    db.execSQL("DROP TRIGGER IF EXISTS update_fechafinreserva;");
                    db.execSQL("CREATE TRIGGER update_fechafinreserva AFTER UPDATE ON [SOLICITUD] WHEN NEW.NUEVOFINPERIODO IS NOT NULL BEGIN UPDATE DETALLERESERVA SET FINPERIODORESERVA = NEW.NUEVOFINPERIODO WHERE IDDETALLERESERVA IN (SELECT IDDETALLERESERVA FROM RESERVA WHERE IDSOLICITUD = NEW.IDSOLICITUD); END");
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                // Llamando método para llenar bd
                llenarBD(db);

            } catch (SQLException | IOException ex) {
                ex.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            onCreate(db);
        }

        public void llenarBD(SQLiteDatabase db){
            try{
                InputStream is = contextDBH.getResources().getAssets().open("llenar_bd.sql");
                InputStream triggerSQL = contextDBH.getResources().getAssets().open("triggers.sql");

                String[] instrucciones = FileHelper.parseSqlFile(is);

                int size = triggerSQL.available();
                byte[] buffer = new byte[size];
                triggerSQL.read(buffer);
                String file = new String(buffer);
                String[] triggersInstrucciones = file.split("--FIN--");
                /*Log.v("Trigger: " , triggersInstrucciones[0]);
                Log.v("Trigger: " , triggersInstrucciones[1]);*/
                db.beginTransaction();
                try {
                    for (String instruccion : instrucciones) {
                        db.execSQL(instruccion);
                    }
                    for (int i = 0; i < triggersInstrucciones.length; i++){
                        db.execSQL(triggersInstrucciones[i]);
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
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

    // Métodos de inserción, actualización, eliminación y consulta
    // Inserción
    public long insertar2(String nombreTabla, ContentValues valores){
        long contador = 0;

        contador=db.insert(nombreTabla, null, valores);

        return contador;
    }

    // Actualización
    public int actualizar(String nombreTabla, ContentValues valores, String tablaPK, String valorPK){
        int contador = 0;

        contador = db.update(nombreTabla, valores, tablaPK + "= '" + valorPK + "'", null);

        return contador;
    }

    // Eliminación
    public int eliminar(String nombreTabla, String tablaPK, String valorPK){
        int contador=0;

        contador+=db.delete(nombreTabla, tablaPK + "= '" + valorPK + "'" , null);

        return contador;
    }


    // Consulta *** falta cuando tiene rablas relacionadas -llaves foráneas-
    public Cursor consultar(String nombreTabla, String[] camposTabla, String tablaPK, String valorPK){
        Cursor cursor = db.query(nombreTabla, camposTabla, tablaPK + " = '" + valorPK + "'",
                null, null, null, null, null);

        return cursor;
    }

    // Obtiene todos los registros de esa tabla
    public Cursor getAll(String nombreTabla, String[] camposTabla){
        Cursor cursor = db.query(nombreTabla, camposTabla, null, null,
                null, null, null, null);

        return cursor;
    }

    // Obtiene todos los registros de esa tabla
    public Cursor getAllFiltered(String nombreTabla, String[] camposTabla){
        Cursor cursor = db.query(nombreTabla, camposTabla, null, null,
                null, null, null, null);

        return cursor;
    }

    public Cursor consultar(String consulta){
        Cursor cursor = db.rawQuery(consulta, null );
        return cursor;
    }

    /*
    public String llenarBD(Context context){

    }*/
}
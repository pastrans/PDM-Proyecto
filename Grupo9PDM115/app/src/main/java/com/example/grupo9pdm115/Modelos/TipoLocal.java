package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;

import java.util.ArrayList;
import java.util.List;

public class TipoLocal {

    // Atributos para BD
    private final String nombreTabla = "tipolocal";
    private ContentValues valores = new ContentValues();
    private ControlBD helper;

    private int idTipoLocal;
    private int idEncargado;
    private String nombreTipo;

    public TipoLocal() {
    }

    public TipoLocal(int idEncargado, String nombreTipo) {
        this.idEncargado = idEncargado;
        this.nombreTipo = nombreTipo;
    }

    public int getIdTipoLocal() {
        return idTipoLocal;
    }

    public void setIdTipoLocal(int idTipoLocal) {
        this.idTipoLocal = idTipoLocal;
    }

    public int getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getNombreTabla() { return nombreTabla; }

    public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("idEncargado", getIdEncargado());
        valores.put("nombreTipo", getNombreTipo());
        return valores;
    }
    
    public List<TipoLocal> getTiposLocales(Context context){
        List<TipoLocal> listaTipoLocal = new ArrayList<TipoLocal>();
        helper = new ControlBD(context);
        helper.abrir();
        Cursor cursor = helper.getDb().rawQuery("SELECT idTipoLocal, nombreTipo FROM tipolocal", null);
        if(cursor.moveToFirst()){
            do{
                TipoLocal tipoLocal = new TipoLocal();
                tipoLocal.setIdTipoLocal(cursor.getInt(0));
                tipoLocal.setNombreTipo(cursor.getString(1));
                listaTipoLocal.add(tipoLocal);
            }while (cursor.moveToNext());
        }
        return listaTipoLocal;
    }

    public void insetarDatosPrueba(Context context){
        helper = new ControlBD(context);
        ContentValues values = new ContentValues();
        values.put("idUsuario", "A1");
        values.put("nombreUsuario", "juanperez");
        values.put("claveUsuario", "12345");
        values.put("nombrePersonal", "Juan");
        values.put("apellidoPersonal", "Pérez");
        values.put("correoPersonal", "juanperez@gmail.com");
        values.put("idUnidad", 1);
        helper.abrir();
        helper.getDb().insert("usuario", null, values);
        helper.cerrar();
        ContentValues valuesEncargado = new ContentValues();
        valuesEncargado.put("idUsuario", "A1");
        helper.abrir();
        helper.getDb().insert("encargado", null, valuesEncargado);
        helper.cerrar();
    }

    @Override
    public String toString(){
        return this.getNombreTipo();
    }

}

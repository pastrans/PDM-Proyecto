package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

import java.util.ArrayList;
import java.util.List;

public class TipoLocal extends TablaBD {

    // Atributos para BD
    private final String nombreTabla = "tipolocal";
    private ContentValues valores = new ContentValues();
    private ControlBD helper;

    private int idTipoLocal;
    private String idEncargado;
    private String nombreTipo;

    public TipoLocal() {
        setNombreLlavePrimaria("idTipoLocal");
        setCamposTabla(new String[]{"idTipoLocal", "idEncargado", "nombreTipo"});
        setNombreTabla("TipoLocal");
    }

    public TipoLocal(String idEncargado, String nombreTipo) {
        this.idEncargado = idEncargado;
        this.nombreTipo = nombreTipo;
    }

    public int getIdTipoLocal() {
        return idTipoLocal;
    }

    public void setIdTipoLocal(int idTipoLocal) {
        this.idTipoLocal = idTipoLocal;
    }

    public String getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(String idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getNombreTabla() { return nombreTabla; }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(this.getIdTipoLocal());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idTipoLocal", getIdTipoLocal());
        this.valoresCamposTabla.put("nombreTipo", getNombreTipo());
        this.valoresCamposTabla.put("idEncargado", getIdEncargado());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdTipoLocal(Integer.valueOf(arreglo[0]));
        setIdEncargado(arreglo[1]);
        setNombreTipo(arreglo[2]);
    }

    @Override
    public TipoLocal getInstanceOfModel(String[] arreglo) {
        TipoLocal tipoLocal = new TipoLocal();
        tipoLocal.setAttributesFromArray(arreglo);
        return tipoLocal;
    }

    /*public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("idEncargado", getIdEncargado());
        valores.put("nombreTipo", getNombreTipo());
        return valores;
    }*/


    @Override
    public String guardar(Context context) {
        String mensaje = "Se ha insertado el registro con éxito. ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombreTipo", getNombreTipo());
        this.valoresCamposTabla.put("idEncargado", getIdEncargado());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0) {
            mensaje= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }

        return mensaje;
    }

    /*public void insetarDatosPrueba(Context context){
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
    }*/

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
        helper.cerrar();
        return listaTipoLocal;
    }

    @Override
    public String toString(){
        return this.getNombreTipo();
    }

}

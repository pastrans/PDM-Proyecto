package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;

public class TipoLocal {

    // Atributos para BD
    private final String nombreTabla = "tipolocal";
    private ContentValues valores = new ContentValues();

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

}

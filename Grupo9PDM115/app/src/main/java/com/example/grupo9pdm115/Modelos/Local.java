package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;

public class Local {

    // Atributos para BD
    private final String nombreTabla = "local";
    private ContentValues valores = new ContentValues();

    private int idLocal;
    private int idTipoLocal;
    private String nombreLocal;
    private int capacidad;

    public Local(){

    }

    public Local(int idTipoLocal, String nombreLocal, int capacidad) {
        this.idTipoLocal = idTipoLocal;
        this.nombreLocal = nombreLocal;
        this.capacidad = capacidad;
    }

    public int getIdlocal() {
        return idLocal;
    }

    public void setIdlocal(int idlocal) {
        this.idLocal = idlocal;
    }

    public int getIdtipolocal() {
        return idTipoLocal;
    }

    public void setIdtipolocal(int idtipolocal) {
        this.idTipoLocal = idtipolocal;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    // Métodos para BD
    public String getNombreTabla() { return nombreTabla; }

    public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("idTipoLocal", getIdtipolocal());
        valores.put("nombreLocal", getNombreLocal());
        valores.put("capacidad", getCapacidad());

        return valores;
    }

}

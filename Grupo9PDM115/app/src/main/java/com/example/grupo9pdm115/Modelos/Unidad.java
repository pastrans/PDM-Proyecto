package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;

public class Unidad {

    // Atributos para BD
    private final String nombreTabla = "unidad";
    private ContentValues valores = new ContentValues();

    // Atributos
    private int idUnidad;
    private String   nombreent;
    private String  descripcionent;
    private int  prioridad;

    // -------------Constructor---------------
    public Unidad(int idUnidad, String nombreent, String descripcionent, int prioridad) {
        this.idUnidad = idUnidad;
        this.nombreent = nombreent;
        this.descripcionent = descripcionent;
        this.prioridad = prioridad;
    }
    // -------------Fin del Constructor---------------

    // -------------Métodos getter y setter---------------
    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombreent() {
        return nombreent;
    }

    public void setNombreent(String nombreent) {
        this.nombreent = nombreent;
    }

    public String getDescripcionent() {
        return descripcionent;
    }

    public void setDescripcionent(String descripcionent) {
        this.descripcionent = descripcionent;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
    public String getNombreTabla() {
        return nombreTabla;
    }
    // -------------Fin de Métodos getter y setter---------------

    // -------------Métodos para BD ------------------------------

    public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("codMateria", getIdUnidad());
        valores.put("idUnidad", getNombreent());
        valores.put("nombreMateria", getDescripcionent());
        valores.put("masiva", getPrioridad());

        return valores;
    }
    // -------------Fin de métodos para BD ------------------------------


}

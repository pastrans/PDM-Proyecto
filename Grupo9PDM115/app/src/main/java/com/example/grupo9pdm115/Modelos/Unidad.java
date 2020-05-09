package com.example.grupo9pdm115.Modelos;

public class Unidad {
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
    // -------------Fin de Métodos getter y setter---------------




}

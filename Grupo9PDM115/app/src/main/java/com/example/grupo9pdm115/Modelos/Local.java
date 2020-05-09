package com.example.grupo9pdm115.Modelos;

public class Local {

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
}

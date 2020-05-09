package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;

public class Feriado {

    // Atributos para BD
    private final String nombreTabla = "feriados";
    private ContentValues valores = new ContentValues();

    private int idFeriado;
    private int idCiclo;
    private String fechaInicioFeriado;
    private String fecchaFinFeriado;
    private String nombreFeriado;
    private String descripcionFeriado;
    private boolean bloquearReservas;

    public Feriado() {
    }

    public Feriado(int idCiclo, String fechaInicioFeriado, String fecchaFinFeriado, String nombreFeriado, String descripcionFeriado, boolean bloquearReservas) {
        this.idCiclo = idCiclo;
        this.fechaInicioFeriado = fechaInicioFeriado;
        this.fecchaFinFeriado = fecchaFinFeriado;
        this.nombreFeriado = nombreFeriado;
        this.descripcionFeriado = descripcionFeriado;
        this.bloquearReservas = bloquearReservas;
    }

    public int getIdFeriado() {
        return idFeriado;
    }

    public void setIdFeriado(int idFeriado) {
        this.idFeriado = idFeriado;
    }

    public int getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getFechaInicioFeriado() {
        return fechaInicioFeriado;
    }

    public void setFechaInicioFeriado(String fechaInicioFeriado) {
        this.fechaInicioFeriado = fechaInicioFeriado;
    }

    public String getFecchaFinFeriado() {
        return fecchaFinFeriado;
    }

    public void setFecchaFinFeriado(String fecchaFinFeriado) {
        this.fecchaFinFeriado = fecchaFinFeriado;
    }

    public String getNombreFeriado() {
        return nombreFeriado;
    }

    public void setNombreFeriado(String nombreFeriado) {
        this.nombreFeriado = nombreFeriado;
    }

    public String getDescripcionFeriado() {
        return descripcionFeriado;
    }

    public void setDescripcionFeriado(String descripcionFeriado) {
        this.descripcionFeriado = descripcionFeriado;
    }

    public boolean isBloquearReservas() {
        return bloquearReservas;
    }

    public void setBloquearReservas(boolean bloquearReservas) {
        this.bloquearReservas = bloquearReservas;
    }

    // Métodos para BD
    public String getNombreTabla() { return nombreTabla; }

    public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("idCiclo", getIdCiclo());
        valores.put("fechaInicioFeriado", getFechaInicioFeriado());
        valores.put("fecchaFinFeriado", getFecchaFinFeriado());
        valores.put("nombreFeriado", getNombreFeriado());
        valores.put("descripcionFeriado", getDescripcionFeriado());
        valores.put("bloquearReservas", isBloquearReservas());

        return valores;
    }

}

package com.example.grupo9pdm115.Modelos;

public class Materia {
    // Atributos
    private String codMateria;
    private int idUnidad;
    private String nombreMateria;
    private boolean masiva;

    // Métodos
    public Materia(){
    }
    
    public Materia(String codMateria, int idUnidad, String nombreMateria, boolean masiva) {
        this.codMateria = codMateria;
        this.idUnidad = idUnidad;
        this.nombreMateria = nombreMateria;
        this.masiva = masiva;
    }

    public String getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(String codMateria) {
        this.codMateria = codMateria;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public boolean isMasiva() {
        return masiva;
    }

    public void setMasiva(boolean masiva) {
        this.masiva = masiva;
    }
}

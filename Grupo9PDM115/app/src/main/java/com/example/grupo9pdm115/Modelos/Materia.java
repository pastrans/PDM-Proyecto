package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;

// Importación de ControlBD
import com.example.grupo9pdm115.BD.ControlBD;

public class Materia {
    // Atributos para BD
    private final String nombreTabla = "materia";
    private ContentValues valores = new ContentValues();

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

    public String getCodMateria() { return codMateria; }

    public void setCodMateria(String codMateria) { this.codMateria = codMateria; }

    public int getIdUnidad() { return idUnidad; }

    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }

    public String getNombreMateria() { return nombreMateria; }

    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public boolean isMasiva() { return masiva; }

    public void setMasiva(boolean masiva) { this.masiva = masiva; }

    // Métodos para BD
    public String getNombreTabla() { return nombreTabla; }

    public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("codMateria", getCodMateria());
        valores.put("idUnidad", getIdUnidad());
        valores.put("nombreMateria", getNombreMateria());
        valores.put("masiva", isMasiva());

        return valores;
    }
}

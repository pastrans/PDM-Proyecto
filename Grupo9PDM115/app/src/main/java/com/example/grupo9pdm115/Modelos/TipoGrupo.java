package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;

public class TipoGrupo {


    // Atributos para BD
    private final String nombreTabla = "tipoGrupo";
    private ContentValues valores = new ContentValues();

    // Atributos
    private String nombreTipoGrupo;
    private int  idTipoGrupo;

    public TipoGrupo() {
    }
    // -------------Constructor---------------
    public TipoGrupo(String nombreTipoGrupo,int idTipoGrupo ) {
        this.nombreTipoGrupo = nombreTipoGrupo;
        this.idTipoGrupo = idTipoGrupo;
    }
    // -------------Fin del Constructor---------------

    // -------------Métodos getter y setter---------------
    public String getNombreTipoGrupo() {
        return nombreTipoGrupo;
    }

    public void setNombreTipoGrupo(String nombreTipoGrupo) {
        this.nombreTipoGrupo = nombreTipoGrupo;
    }



    public int getIdTipoGrupo() {
        return idTipoGrupo;
    }

    public void setIdTipoGrupo(int idTipoGrupo) {
        this.idTipoGrupo = idTipoGrupo;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }
    // -------------Fin de Métodos getter y setter---------------

    // -------------Métodos para BD ------------------------------
    public ContentValues getValores() {
        // Agregando los valores de los atributos al content value
        //valores.put("tipoGrupo", getIdTipoGrupo());
        valores.put("nombreTipoGrupo", getNombreTipoGrupo());
        return valores;
    }
    // -------------Fin de métodos para BD ------------------------------


}

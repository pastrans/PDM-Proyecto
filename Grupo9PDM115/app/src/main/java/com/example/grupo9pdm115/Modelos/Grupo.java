package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;

public class Grupo {


    // Atributos para BD
    private final String nombreTabla = "grupo";
    private ContentValues valores = new ContentValues();


    // Atributos
    private int idGrupo;
    private int numero;
    private int idTipoGrupo;
    private int idCicloMateria;
    // -------------Constructor---------------
    public Grupo(int idGrupo, int numero) {


        this.idGrupo = idGrupo;
        this.numero = numero;
    }
    public Grupo() {

    }
    // -------------Fin del Constructor---------------
    // -------------Métodos getter y setter---------------
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombreTabla() { return nombreTabla; }

    public int getIdTipoGrupo() {
        return idTipoGrupo;
    }

    public void setIdTipoGrupo(int idTipoGrupo) {
        this.idTipoGrupo = idTipoGrupo;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

// -------------Fin de Métodos getter y setter---------------
    // -------------Métodos para BD ------------------------------

    public ContentValues getValores() {
//        valores.put("codMateria",getIdGrupo());
        valores.put("numero", getNumero());
        valores.put("idCicloMateria", getIdCicloMateria());
        valores.put("idTipoGrupo", getIdTipoGrupo());
        return valores;
    }
    // -------------Fin de métodos para BD ------------------------------



}


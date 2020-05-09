package com.example.grupo9pdm115.Modelos;

public class TipoGrupo {
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
    // -------------Fin de Métodos getter y setter---------------


}

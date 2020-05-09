package com.example.grupo9pdm115.Modelos;

public class Grupo {
    private int idGrupo;
    private int numero;

    // -------------Constructor---------------
    public Grupo(int idGrupo, int numero) {
        this.idGrupo = idGrupo;
        this.numero = numero;
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
// -------------Fin de Métodos getter y setter---------------



}


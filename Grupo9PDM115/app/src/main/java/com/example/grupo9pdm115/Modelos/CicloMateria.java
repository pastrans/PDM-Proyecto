package com.example.grupo9pdm115.Modelos;

public class CicloMateria {
    // Atributos
    private int idCicloMateria;
    private int idCiclo;
    private String codMateria;

    // Métodos
    public CicloMateria(){

    }

    public CicloMateria(int idCicloMateria, int idCiclo, String codMateria) {
        this.idCicloMateria = idCicloMateria;
        this.idCiclo = idCiclo;
        this.codMateria = codMateria;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

    public int getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(String codMateria) {
        this.codMateria = codMateria;
    }
}

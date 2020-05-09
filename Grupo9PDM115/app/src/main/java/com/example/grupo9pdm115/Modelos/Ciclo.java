package com.example.grupo9pdm115.Modelos;


public class Ciclo {
    // Atributos
    private int idCiclo;
    private String inicio;
    private String fin;
    private String nombreCiclo;
    private boolean estadoCiclo;
    private String inicioPeriodoClase;
    private String finPeriodoClase;

    // Métodos
    public Ciclo(){
    }

    public Ciclo(int idCiclo, String inicio, String fin, String nombreCiclo, boolean estadoCiclo, String inicioPeriodoClase, String finPeriodoClase) {
        this.idCiclo = idCiclo;
        this.inicio = inicio;
        this.fin = fin;
        this.nombreCiclo = nombreCiclo;
        this.estadoCiclo = estadoCiclo;
        this.inicioPeriodoClase = inicioPeriodoClase;
        this.finPeriodoClase = finPeriodoClase;
    }
    public int getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getNombreCiclo() {
        return nombreCiclo;
    }

    public void setNombreCiclo(String nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }

    public boolean isEstadoCiclo() {
        return estadoCiclo;
    }

    public void setEstadoCiclo(boolean estadoCiclo) {
        this.estadoCiclo = estadoCiclo;
    }

    public String getInicioPeriodoClase() {
        return inicioPeriodoClase;
    }

    public void setInicioPeriodoClase(String inicioPeriodoClase) {
        this.inicioPeriodoClase = inicioPeriodoClase;
    }

    public String getFinPeriodoClase() {
        return finPeriodoClase;
    }

    public void setFinPeriodoClase(String finPeriodoClase) {
        this.finPeriodoClase = finPeriodoClase;
    }

}

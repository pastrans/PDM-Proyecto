package com.example.grupo9pdm115.Modelos;

import com.example.grupo9pdm115.BD.TablaBD;

public class CicloMateria extends TablaBD {
    // Atributos
    private int idCicloMateria;
    private int idCiclo;
    private String codMateria;

    // Métodos
    public CicloMateria (){

        setNombreTabla("CICLOMATERIA");
        setNombreLlavePrimaria("IDCICLOMATERIA");
        setCamposTabla(new String[]{"IDCICLOMATERIA", "IDCICLO", "CODMATERIA"});
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

    @Override
    public String getValorLlavePrimaria() {
        return null;
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        this.valoresCamposTabla.put("idCiclo", getIdCiclo());
        this.valoresCamposTabla.put("codMateria", getCodMateria());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdCicloMateria(Integer.valueOf(arreglo[0]));
        setIdCiclo(Integer.valueOf(arreglo[1]));
        setCodMateria(arreglo[2]);
    }

    @Override
    public CicloMateria getInstanceOfModel(String[] arreglo) {
        CicloMateria cicloMateria = new CicloMateria();
        cicloMateria.setAttributesFromArray(arreglo);
        return cicloMateria;
    }
}

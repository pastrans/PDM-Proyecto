package com.example.grupo9pdm115.Modelos;

import com.example.grupo9pdm115.BD.TablaBD;

public class Dia extends TablaBD {
    // Atributos
    private int idDia;
    private String nombreDia;

    // Métodos
    // Constructores
    public Dia(){
        // **** BD: establecer los valores para trabajar con la tabla
        setNombreTabla("dia");
        setNombreLlavePrimaria("iddia");
        setCamposTabla(new String[]{"iddia", "nombredia"});
    }
    public Dia(int idDia, String nombreDia){
        this();
        this.setIdDia(idDia);
        this.setNombreDia(nombreDia);
    }

    // idDia
    public int getIdDia() { return idDia; }
    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }
    // nombreDia
    public String getNombreDia() { return nombreDia; }
    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(this.getIdDia());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("iddia", getIdDia());
        this.valoresCamposTabla.put("nombredia", getNombreDia());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdDia(Integer.parseInt(arreglo[0]));
        setNombreDia(arreglo[1]);
    }

    @Override
    public Dia getInstanceOfModel(String[] arreglo) {
        Dia dia = new Dia();
        dia.setAttributesFromArray(arreglo);
        return dia;
    }
}

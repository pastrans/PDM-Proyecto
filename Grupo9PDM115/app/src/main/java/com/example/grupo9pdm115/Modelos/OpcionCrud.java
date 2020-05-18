package com.example.grupo9pdm115.Modelos;

import android.content.Intent;

import com.example.grupo9pdm115.BD.TablaBD;

public class OpcionCrud extends TablaBD {

    private String idOpcion;
    private String descripcion;
    private int numCrud;

    public OpcionCrud() {
        setNombreTabla("opcioncrud");
        setNombreLlavePrimaria("idOpcion");
        setCamposTabla(new String[]{"idOpcion", "desopcion", "numCrud"});
    }

    public OpcionCrud(String idOpcion, String desOpcion, int numCrud){
        this();
        this.setIdOpcion(idOpcion);
        this.setDescripcion(desOpcion);
        this.setNumCrud(numCrud);
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumCrud() {
        return numCrud;
    }

    public void setNumCrud(int numCrud) {
        this.numCrud = numCrud;
    }

    @Override
    public String getValorLlavePrimaria() {
        return this.getIdOpcion();
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idOpcion", getIdOpcion());
        this.valoresCamposTabla.put("desopcion", getDescripcion());
        this.valoresCamposTabla.put("numcrud", getNumCrud());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdOpcion(arreglo[0]);
        setDescripcion(arreglo[1]);
        setNumCrud(Integer.parseInt(arreglo[2]));
    }

    @Override
    public OpcionCrud getInstanceOfModel(String[] arreglo) {
        OpcionCrud oc = new OpcionCrud();
        oc.setAttributesFromArray(arreglo);
        return oc;
    }
    

}

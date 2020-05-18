package com.example.grupo9pdm115.Modelos;

import com.example.grupo9pdm115.BD.TablaBD;

public class AccesoUsuario extends TablaBD {

    private int idAccesoUsuario;
    private String idOpcion;
    private String idUsuario;

    public AccesoUsuario() {
        setNombreTabla("accesousuario");
        setNombreLlavePrimaria("idAccesoUsuario");
        setCamposTabla(new String[]{"idAccesoUsuario", "idOpcion", "idUsuario"});
    }

    public AccesoUsuario(int idAccesoUsuario, String idOpcion, String idUsuario) {
        this();
        this.idAccesoUsuario = idAccesoUsuario;
        this.idOpcion = idOpcion;
        this.idUsuario = idUsuario;
    }

    public int getIdAccesoUsuario() {
        return idAccesoUsuario;
    }

    public void setIdAccesoUsuario(int idAccesoUsuario) {
        this.idAccesoUsuario = idAccesoUsuario;
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(getIdAccesoUsuario());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idAccesoUsuario", getIdAccesoUsuario());
        this.valoresCamposTabla.put("idOpcion", getIdOpcion());
        this.valoresCamposTabla.put("idUsuario", getIdUsuario());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdAccesoUsuario(Integer.parseInt(arreglo[0]));
        setIdOpcion(arreglo[1]);
        setIdUsuario(arreglo[2]);
    }

    @Override
    public AccesoUsuario getInstanceOfModel(String[] arreglo) {
        AccesoUsuario au = new AccesoUsuario();
        au.setAttributesFromArray(arreglo);
        return au;
    }
}

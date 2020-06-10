package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class Local extends TablaBD {

    // Atributos para BD
    private final String nombreTabla = "local";
    private ContentValues valores = new ContentValues();

    private int idLocal;
    private int idTipoLocal;
    private String nombreLocal;
    private int capacidad;

    public Local(){
        setNombreLlavePrimaria("idLocal");
        setNombreTabla("local");
        setCamposTabla(new String[]{"idLocal", "nombreLocal", "idTipoLocal", "capacidad"});
    }

    public Local(int idTipoLocal, String nombreLocal, int capacidad) {
        this.idTipoLocal = idTipoLocal;
        this.nombreLocal = nombreLocal;
        this.capacidad = capacidad;
    }

    public int getIdlocal() {
        return idLocal;
    }

    public void setIdlocal(int idlocal) {
        this.idLocal = idlocal;
    }

    public int getIdtipolocal() {
        return idTipoLocal;
    }

    public void setIdtipolocal(int idtipolocal) {
        this.idTipoLocal = idtipolocal;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(this.getIdlocal());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idLocal", getIdlocal());
        this.valoresCamposTabla.put("nombreLocal", getNombreLocal());
        this.valoresCamposTabla.put("idTipoLocal", getIdtipolocal());
        this.valoresCamposTabla.put("capacidad", getCapacidad());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdlocal(Integer.valueOf(arreglo[0]));
        setNombreLocal(arreglo[1]);
        setIdtipolocal(Integer.valueOf(arreglo[2]));
        setCapacidad(Integer.valueOf(arreglo[3]));
    }

    @Override
    public Local getInstanceOfModel(String[] arreglo) {
        Local local = new Local();
        local.setAttributesFromArray(arreglo);
        return local;
    }

    @Override
    public String guardar(Context context) {
        String mensaje = "Se ha insertado el registro con éxito. ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombreLocal", getNombreLocal());
        this.valoresCamposTabla.put("idTipoLocal", getIdtipolocal());
        this.valoresCamposTabla.put("capacidad", getCapacidad());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0) {
            mensaje= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }

        return mensaje;
    }

    /*public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("idTipoLocal", getIdtipolocal());
        valores.put("nombreLocal", getNombreLocal());
        valores.put("capacidad", getCapacidad());

        return valores;
    }*/

}

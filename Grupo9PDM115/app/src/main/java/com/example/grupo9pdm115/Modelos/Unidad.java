package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class Unidad extends TablaBD {

    // Atributos para BD
    //private final String nombreTabla = "unidad";
    //private ContentValues valores = new ContentValues();

    // Atributos
    private int idUnidad;
    private String   nombreent;
    private String  descripcionent;

    public Unidad(){
        setNombreTabla("unidad");
        setNombreLlavePrimaria("idunidad");
        setCamposTabla(new String[]{"idunidad", "nombreent","descripcionent"});
    }

    // -------------Constructor---------------
    public Unidad(int idUnidad, String nombreent, String descripcionent) {
        this.idUnidad = idUnidad;
        this.nombreent = nombreent;
        this.descripcionent = descripcionent;
    }
    // -------------Fin del Constructor---------------

    // -------------Métodos getter y setter---------------
    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombreent() {
        return nombreent;
    }

    public void setNombreent(String nombreent) {
        this.nombreent = nombreent;
    }

    public String getDescripcionent() {
        return descripcionent;
    }

    public void setDescripcionent(String descripcionent) {
        this.descripcionent = descripcionent;
    }


    //public String getNombreTabla() {
        //return nombreTabla;
    //}
    // -------------Fin de Métodos getter y setter---------------

    //---------------Sobrescritura de métodos
    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(this.getIdUnidad());
    }
    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idunidad", getIdUnidad());
        this.valoresCamposTabla.put("nombreent", getNombreent());
        this.valoresCamposTabla.put("descripcionent", getDescripcionent());
    }
    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdUnidad(Integer.parseInt(arreglo[0]));
        setNombreent(arreglo[1]);
        setDescripcionent(arreglo[2]);
    }
    @Override
    public Unidad getInstanceOfModel(String[] arreglo) {
        Unidad unidad = new Unidad();
        unidad.setAttributesFromArray(arreglo);
        return unidad;
    }
    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombreent", getNombreent());
        this.valoresCamposTabla.put("descripcionent", getDescripcionent());

        helper.abrir();
        control = helper.getDb().insert("unidad", null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            mensaje = mensaje+control;
        }

        return mensaje;
    }
    @Override
    public boolean consultar (Context context, String valorLlavePrimaria){
        boolean resultado = false;
        String[] valores = new String[getCamposTabla().length];
        ControlBD helper = new ControlBD(context);

        helper.abrir();
        Cursor cursor = helper.consultar(this.getNombreTabla(), this.getCamposTabla(),
                this.getNombreLlavePrimaria(), valorLlavePrimaria);


        if(cursor.moveToFirst()){
            for(int i = 0; i < getCamposTabla().length; i++){
                valores[i] = cursor.getString(i);
            }
            this.setAttributesFromArray(valores);
            resultado = true;
        }

        helper.cerrar();

        return resultado;
    }

}

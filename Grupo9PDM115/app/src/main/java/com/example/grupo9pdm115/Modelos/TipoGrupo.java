package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class TipoGrupo extends TablaBD {


    // Atributos para BD
    private final String nombreTabla = "tipoGrupo";
    private ContentValues valores = new ContentValues();

    // Atributos
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

    public String getNombreTabla() {
        return nombreTabla;
    }

    @Override
    public String getValorLlavePrimaria() {
        return null;
    }

    @Override
    public void setValoresCamposTabla() {

    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {

    }

    @Override
    public TablaBD getInstanceOfModel(String[] arreglo) {
        return null;
    }
    // -------------Fin de Métodos getter y setter---------------

    // -------------Métodos para BD ------------------------------
    public ContentValues getValores() {
        // Agregando los valores de los atributos al content value
        //valores.put("tipoGrupo", getIdTipoGrupo());
        valores.put("nombreTipoGrupo", getNombreTipoGrupo());
        return valores;
    }
    // -------------Fin de métodos para BD ------------------------------

    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombretipogrupo", getNombreTipoGrupo());

        helper.abrir();
        control = helper.getDb().insert("tipogrupo", null, valoresCamposTabla);
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
}
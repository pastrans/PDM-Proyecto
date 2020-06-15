package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class Coordinacion  extends TablaBD {

    // Atributos para BD
    private final String nombreTabla = "coordinacion";
    private ContentValues valores = new ContentValues();



    // Atributos
    private int idCoodinacion;
    private int idCicloMateria;
    private String idUsuario;
    private String tipoCoordinacion;

    @Override
    public String getNombreTabla() {
        return nombreTabla;
    }

    public ContentValues getValores() {
        return valores;
    }

    public void setValores(ContentValues valores) {
        this.valores = valores;
    }

    public int getIdCoodinacion() {
        return idCoodinacion;
    }

    public void setIdCoodinacion(int idCoodinacion) {
        this.idCoodinacion = idCoodinacion;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoCoordinacion() {
        return tipoCoordinacion;
    }

    public void setTipoCoordinacion(String tipoCoordinacion) {
        this.tipoCoordinacion = tipoCoordinacion;
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdCoodinacion(Integer.parseInt(arreglo[0]));
        setIdUsuario(arreglo[1]);
        setIdCicloMateria(Integer.parseInt(arreglo[2]));
        setTipoCoordinacion(arreglo[3]);

    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(getIdCoodinacion());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put(" idcoordinacion", getIdCoodinacion());
        this.valoresCamposTabla.put("idusuario", getIdUsuario());
        this.valoresCamposTabla.put("idciclomateria" , getIdCicloMateria());
        this.valoresCamposTabla.put("tipocoordinacion" , getTipoCoordinacion());

    }



    @Override
    public TablaBD getInstanceOfModel(String[] arreglo) {
        Coordinacion coordinacion = new Coordinacion();
        coordinacion.setAttributesFromArray(arreglo);
        return coordinacion;
    }


    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("idcoordinacion", getIdCoodinacion());
        this.valoresCamposTabla.put("idusuario", getIdUsuario());
        this.valoresCamposTabla.put("idciclomateria", getIdCicloMateria());
        this.valoresCamposTabla.put("tipocoordinacion", getTipoCoordinacion());


        helper.abrir();
        control = helper.getDb().insert("coordinacion", null, valoresCamposTabla);
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

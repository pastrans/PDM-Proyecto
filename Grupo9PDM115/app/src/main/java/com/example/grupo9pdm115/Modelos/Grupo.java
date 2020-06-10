package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class Grupo extends TablaBD {


    // Atributos para BD
    /*private final String nombreTabla = "grupo";
    private ContentValues valores = new ContentValues();*/


    // Atributos
    private int idGrupo;
    private int numero;
    private int idTipoGrupo;
    private int idCicloMateria;
    private CicloMateria cicloMateria;
    private TipoGrupo tipoGrupo;
    // -------------Constructor---------------
    public Grupo(int idGrupo, int numero) {
        this.idGrupo = idGrupo;
        this.numero = numero;
    }
    public Grupo() {
        setNombreTabla("grupo");
        setNombreLlavePrimaria("idGrupo");
        setCamposTabla(new String[]{"idGrupo", "idTipoGrupo", "idCicloMateria", "numero"});
    }
    // -------------Fin del Constructor---------------
    // -------------Métodos getter y setter---------------
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    //public String getNombreTabla() { return nombreTabla; }

    public CicloMateria getCicloMateria() {
        return cicloMateria;
    }

    public void setCicloMateria(CicloMateria cicloMateria) {
        this.cicloMateria = cicloMateria;
    }

    public TipoGrupo getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(TipoGrupo tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(getIdGrupo());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idGrupo", getIdGrupo());
        this.valoresCamposTabla.put("idTipoGrupo", getIdTipoGrupo());
        this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        this.valoresCamposTabla.put("numero", getNumero());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdGrupo(Integer.valueOf(arreglo[0]));
        setIdTipoGrupo(Integer.valueOf(arreglo[1]));
        setIdCicloMateria(Integer.valueOf(arreglo[2]));
        setNumero(Integer.valueOf(arreglo[3]));
    }

    @Override
    public Grupo getInstanceOfModel(String[] arreglo) {
        Grupo grupo = new Grupo();
        grupo.setAttributesFromArray(arreglo);
        return grupo;
    }

    public int getIdTipoGrupo() {
        return idTipoGrupo;
    }

    public void setIdTipoGrupo(int idTipoGrupo) {
        this.idTipoGrupo = idTipoGrupo;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

// -------------Fin de Métodos getter y setter---------------
    // -------------Métodos para BD ------------------------------

    /*public ContentValues getValores() {
        valores.put("idGrupo",getIdGrupo());
        valores.put("numero", getNumero());
        valores.put("idCicloMateria", getIdCicloMateria());
        valores.put("idTipoGrupo", getIdTipoGrupo());
        return valores;
    }*/
    // -------------Fin de métodos para BD ------------------------------

    @Override
    public String guardar(Context context) {
        String mensaje = "Se ha insertado el registro con éxito. ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("numero", getNumero());
        this.valoresCamposTabla.put("idTipoGrupo", getIdTipoGrupo());
        this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0) {
            mensaje= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        return mensaje;
    }

}


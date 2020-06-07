package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

import java.util.ArrayList;
import java.util.List;

public class Encargado extends TablaBD {

    private int idEncargado;
    private String idUsuario;

    public Encargado(){
        setNombreTabla("encargado");
        setNombreLlavePrimaria("idencargado");
        setCamposTabla(new String[]{"idencargado","idusuario"});
    }

    public Encargado(int idEncargado, String idUsuario) {
        this.idEncargado = idEncargado;
        this.idUsuario = idUsuario;
    }

    public int getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(this.getIdEncargado());
    }
    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idEncargado",getIdEncargado());
        this.valoresCamposTabla.put("idUsuario", getIdUsuario());
    }
    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdEncargado(Integer.parseInt(arreglo[0]));
        setIdUsuario(arreglo[1]);
    }
    @Override
    public Encargado getInstanceOfModel(String[] arreglo) {
        Encargado encargado = new Encargado();
        encargado.setAttributesFromArray(arreglo);
        return encargado;
    }




    /*public List<Encargado> getEncargados(Context context){
        helper = new ControlBD(context);
        List<Encargado> encargados = new ArrayList<Encargado>();
        helper.abrir();
        Cursor cursor = helper.getDb().rawQuery("SELECT encargado.idUsuario, usuario.nombrePersonal, usuario.apellidoPersonal, encargado.idEncargado FROM usuario, encargado WHERE usuario.idUsuario = encargado.idUsuario",null);
        if (cursor.moveToFirst()){
            do {
                Usuario usuario = new Usuario();
                Encargado encargado = new Encargado();
                usuario.setIdUsuario(cursor.getString(0));
                usuario.setNombrePersonal(cursor.getString(1));
                usuario.setApellidoPersonal(cursor.getString(2));
                encargado.setIdEncargado(cursor.getInt(3));
                encargado.setUsuario(usuario);
                encargados.add(encargado);
                //nombres.add(cursor.getString(1) + " " + cursor.getString(2));
            }while (cursor.moveToNext());
        }
        helper.cerrar();
        return encargados;
    }*/

}

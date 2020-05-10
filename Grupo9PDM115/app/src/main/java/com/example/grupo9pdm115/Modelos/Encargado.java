package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;

import java.util.ArrayList;
import java.util.List;

public class Encargado {

    private int idEncargado;
    private Usuario usuario;
    private ControlBD helper;

    public Encargado() {
    }

    public Encargado(int idEncargado, Usuario usuario) {
        this.idEncargado = idEncargado;
        this.usuario = usuario;
    }

    public int getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString(){
        return this.usuario.getNombrePersonal() + " " +this.usuario.getApellidoPersonal();
    }

    public List<Encargado> getEncargados(Context context){
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
    }

}

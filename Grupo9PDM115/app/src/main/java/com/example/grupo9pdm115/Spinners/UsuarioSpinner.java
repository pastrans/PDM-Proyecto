package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.Modelos.Usuario;

import java.util.ArrayList;

public class UsuarioSpinner {
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<String> contenidoUsuario;
    private Cursor cursor;

    public UsuarioSpinner(ControlBD helper){
        String sql = "SELECT * FROM USUARIO";
        cursor = helper.consultar(sql);
        Usuario usuario;
        listaUsuarios = new ArrayList<Usuario>();
        contenidoUsuario = new ArrayList<String>();
        contenidoUsuario.add("Seleccione un usuario");
        while (cursor.moveToNext()){
            if (!cursor.getString(3).equals("admin")){
                usuario = new Usuario();
                usuario.setIdUsuario(cursor.getString(0));
                contenidoUsuario.add(cursor.getString(5) + " " + cursor.getString(6));
                listaUsuarios.add(usuario);
            }
        }
    }
    public ArrayAdapter getAdapterUsuario(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_item, contenidoUsuario);
        return adapter;
    }
    public String getIdUsuario(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaUsuarios.get(pos).getIdUsuario();
    }
    public Usuario getUsuario(int id){
        Usuario u = new Usuario();
        u = listaUsuarios.get(id);
        return u;
    }
}

package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Unidad;

import java.util.ArrayList;

public class RolSpinner {
    private ArrayList<Rol> listaRol;
    private ArrayList<String> contenidoRol;
    private Cursor cursor;

    public RolSpinner(ControlBD helper){
        String sql = "SELECT * FROM ROL";
        cursor = helper.consultar(sql);
        Rol rol;
        listaRol = new ArrayList<Rol>();
        contenidoRol = new ArrayList<String>();
        contenidoRol.add("Seleccione un rol");
        while (cursor.moveToNext()){
            rol = new Rol();
            rol.setIdRol(cursor.getInt(0));
            rol.setNombreRol(cursor.getString(1));
            contenidoRol.add(cursor.getString(1));
            listaRol.add(rol);
        }
    }

    public ArrayAdapter getAdapterRol(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, contenidoRol);
        return adapter;
    }

    public int getIdRol(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaRol.get(pos).getIdRol();
    }

    public Rol getRol(int id){
        Rol r = new Rol();
        r = listaRol.get(id);
        return r;
    }
}

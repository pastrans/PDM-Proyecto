package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;

public class UsuarioUnidadSpinner {
    private ArrayList<Unidad> listaUnidad;
    private ArrayList<String> contenidoUnidad;
    private Cursor cursor;
    private ControlBD helper;

    public UsuarioUnidadSpinner(Context context){
        // Ejecutar consulta
        helper = new ControlBD(context);
        helper.abrir();
        String sql = "SELECT * FROM UNIDAD";
        cursor = helper.consultar(sql);

        Unidad unidad;
        listaUnidad = new ArrayList<Unidad>();
        contenidoUnidad = new ArrayList<String>();
        contenidoUnidad.add(context.getString(R.string.txtSelecUnidad)); //contenidoUnidad.add("Seleccione una unidad");
        while (cursor.moveToNext()){
            unidad = new Unidad();
            unidad.setIdUnidad(cursor.getInt(0));
            unidad.setNombreent(cursor.getString(1));
            contenidoUnidad.add(cursor.getString(1));
            listaUnidad.add(unidad);
        }

        helper.cerrar();
    }

    public ArrayAdapter getAdapterUnidad(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, contenidoUnidad);
        return adapter;
    }

    public int getIdUnidad(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaUnidad.get(pos).getIdUnidad();
    }

    public Unidad getUnidad(int id){
        Unidad u = new Unidad();
        u = listaUnidad.get(id);
        return u;
    }

}

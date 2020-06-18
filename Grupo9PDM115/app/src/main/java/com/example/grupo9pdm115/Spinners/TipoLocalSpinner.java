package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;

public class TipoLocalSpinner {
    private ArrayList<TipoLocal> listaTipoLocal;
    private ArrayList<String> contenidoTipoLocal;
    private Cursor cursor;
    private ControlBD helper;

    public TipoLocalSpinner(Context context){
        // Ejecutar consulta
        helper = new ControlBD(context);
        helper.abrir();
        String sql = "SELECT * FROM tipolocal";
        cursor = helper.consultar(sql);

        TipoLocal tipoLocal;
        listaTipoLocal = new ArrayList<TipoLocal>();
        contenidoTipoLocal = new ArrayList<String>();
        contenidoTipoLocal.add(context.getString(R.string.txtSelecTipoLocal)); // contenidoTipoLocal.add("Seleccione un tipo de local");
        while (cursor.moveToNext()){
            tipoLocal = new TipoLocal();
            tipoLocal.setIdTipoLocal(cursor.getInt(0));
            contenidoTipoLocal.add(cursor.getString(2));
            listaTipoLocal.add(tipoLocal);
        }

        helper.cerrar();
    }

    public ArrayAdapter getAdapterTipoLocal(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, contenidoTipoLocal);
        return adapter;
    }

    public int getIdTipoLocal(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaTipoLocal.get(pos).getIdTipoLocal();
    }

    public TipoLocal getTipoLocal(int id){
        TipoLocal tl = new TipoLocal();
        tl = listaTipoLocal.get(id);
        return tl;
    }

}

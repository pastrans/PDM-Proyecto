package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Dia;

import java.util.ArrayList;

public class DiaSpinner {
    private ArrayList<Dia> listaDia;
    private ArrayList<String> contenidoDia;
    private Cursor cursor;

    public DiaSpinner(ControlBD helper){
        String sql = "SELECT * FROM DIA";
        cursor = helper.consultar(sql);
        Dia dia;
        listaDia = new ArrayList<Dia>();
        contenidoDia = new ArrayList<String>();
        contenidoDia.add("Seleccione un día");
        while (cursor.moveToNext()){
            dia = new Dia();
            dia.setIdDia(cursor.getInt(0));
            dia.setNombreDia(cursor.getString(1));
            contenidoDia.add(cursor.getString(1));
            listaDia.add(dia);
        }
    }

    public ArrayAdapter getAdapterDia(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, contenidoDia);
        return adapter;
    }

    public int getIdDia(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaDia.get(pos).getIdDia();
    }

    public Dia getRol(int id){
        Dia d = new Dia();
        d = listaDia.get(id);
        return d;
    }
}

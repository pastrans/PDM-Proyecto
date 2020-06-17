package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.EventoEspecial;

import java.util.ArrayList;

public class EventoEspecialSpinner {
    private ArrayList<EventoEspecial> listaEventoEspecial;
    private ArrayList<String> contenidoEventoEspecial;
    private Cursor cursor;

    public EventoEspecialSpinner(ControlBD helper){
        String sql = "SELECT * FROM EVENTOESPECIAL";
        cursor = helper.consultar(sql);
        EventoEspecial eventoEspecial;
        listaEventoEspecial = new ArrayList<EventoEspecial>();
        contenidoEventoEspecial = new ArrayList<String>();
        contenidoEventoEspecial.add("Seleccione un evento especial");
        while (cursor.moveToNext()){
            eventoEspecial = new EventoEspecial();
            eventoEspecial.setIdEventoEspecial(cursor.getInt(0));
            eventoEspecial.setIdCicloMateria(cursor.getInt(1));
            eventoEspecial.setNombreEvento(cursor.getString(2));
            eventoEspecial.setFechaEvento(cursor.getString(3));
            eventoEspecial.setDescripcionEvento(cursor.getString(4));
            contenidoEventoEspecial.add(cursor.getString(2));
            listaEventoEspecial.add(eventoEspecial);
        }
    }

    public ArrayAdapter getAdapterEventoEspecial(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, contenidoEventoEspecial);
        return adapter;
    }

    public int getIdEventoEspecial(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaEventoEspecial.get(pos).getIdEventoEspecial();
    }

    public EventoEspecial getEventoEspecial(int id){
        EventoEspecial ee = new EventoEspecial();
        id = id - 1;
        ee = listaEventoEspecial.get(id);
        return ee;
    }
}

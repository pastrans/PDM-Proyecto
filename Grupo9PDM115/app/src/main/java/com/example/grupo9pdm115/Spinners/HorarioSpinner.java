package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Horario;

import java.util.ArrayList;

public class HorarioSpinner {
    private ArrayList<Horario> listaHorario;
    private ArrayList<String> contenidoHorario;
    private Cursor cursor;

    public HorarioSpinner(ControlBD helper){
        String sql = "SELECT * FROM HORARIO";
        cursor = helper.consultar(sql);
        Horario horario;
        listaHorario = new ArrayList<Horario>();
        contenidoHorario = new ArrayList<String>();
        contenidoHorario.add("Seleccione una hora");
        while (cursor.moveToNext()){
            horario = new Horario();
            horario.setIdHora(cursor.getInt(0));
            horario.setHoraInicio(cursor.getString(1));
            horario.setHoraFinal(cursor.getString(2));
            contenidoHorario.add(cursor.getString(1) + " - " + cursor.getString(2));
            listaHorario.add(horario);
        }
    }

    public ArrayAdapter getAdapterHorario(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, contenidoHorario);
        return adapter;
    }

    public int getIdHorario(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaHorario.get(pos).getIdHora();
    }

    public Horario getHorario(int id){
        Horario h = new Horario();
        id = id - 1;
        h = listaHorario.get(id);
        return h;
    }

}

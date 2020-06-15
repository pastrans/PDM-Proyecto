package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CicloSpinnerAdapter {
    private List<Ciclo> listaCiclos;
    private ArrayList<String> contenidoSpinner;
    private Cursor cursor;

    // Constructor
    public CicloSpinnerAdapter (Context context){
        Ciclo ciclo = new Ciclo();
        listaCiclos = ciclo.getAll(context);

        contenidoSpinner = new ArrayList<String>();
        contenidoSpinner.add("Seleccione un ciclo");
        for(Ciclo cicloLista : listaCiclos){
            contenidoSpinner.add(cicloLista.getNombreCiclo());
        }
    }

    public ArrayAdapter getAdapterCiclo(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_dropdown_item, contenidoSpinner);
        return adapter;
    }

    public int getIdCiclo(int posicion){
        int pos = posicion - 1;
        return listaCiclos.get(pos).getIdCiclo();
    }

    public Ciclo getCiclo(int posicion){
        Ciclo c = listaCiclos.get(posicion - 1);
        return c;
    }
}

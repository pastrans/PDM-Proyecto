package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.Modelos.Ciclo;

import java.util.ArrayList;
import java.util.List;

public class CicloSpinnerHelper {
    private List<Ciclo> listaCiclos;
    private ArrayList<String> contenidoSpinner;

    // Constructor
    public CicloSpinnerHelper(Context context){
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
        // Si está seleccionado la opción por defecto "Seleccione un ciclo" cuyo índice es 0 se
        // devolverá -1
        if(posicion == 0)
            return -1;
        // Si está bien retornar el id del ciclo
        return listaCiclos.get(posicion - 1).getIdCiclo();
    }

    public Ciclo getCiclo(int posicion){
        // Si está seleccionado la opción por defecto "Seleccione un ciclo" cuyo índice es 0 se
        // devolverá null
        if(posicion == 0)
            return null;
        // Si está bien retornar el ciclo
        return listaCiclos.get(posicion - 1);
    }
}

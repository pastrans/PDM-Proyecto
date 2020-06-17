package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.Adapters.UnidadAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Unidad;

import java.util.ArrayList;

public class UnidadSpinner {

    //Inicio Atributos
    private ArrayList<Unidad> listUnidad;
    private  ArrayList<String> contenidoUnidad; //para tipo de grupo
    private Cursor cursor ;
    //Fin Atributos


    // Inicio de los constructores
    public UnidadSpinner(ControlBD helpercontexto) {
        //Código para mostar las listas de String
        String Consulta = "SELECT * FROM UNIDAD";
        cursor = helpercontexto.consultar(Consulta);
        Unidad unidad;
        listUnidad = new ArrayList<Unidad>();
        contenidoUnidad = new ArrayList<String>();
        contenidoUnidad.add("Seleccione"); // genera un desfase con respecto a la lista de objetos de TipoGrupo
        while (cursor.moveToNext()) {
            unidad = new Unidad();
            unidad.setIdUnidad(cursor.getInt(0));
            unidad.setNombreent(cursor.getString(1));
            contenidoUnidad.add(cursor.getString(1)); // llenamos la lista
            listUnidad.add(unidad);

        }


    }
    //Inicio de métodos
    public ArrayAdapter getAdapterUnidad (Context ctx){
        ArrayAdapter adapter = new ArrayAdapter<String>( ctx, android.R.layout.simple_spinner_dropdown_item, contenidoUnidad);
        // aquí cargamos lo que queremos mostrar en nuestro caso es la lista de contenido
        return adapter ;
    }

    public int getIdUnidad(int posicion){
        int pos = 0;
        pos = posicion-1 ; // se resta 1 por el desfase generado con la lista de contendio
        return listUnidad.get(pos).getIdUnidad();
    }
    
    public int buscarUnidad(int idUnidad){
        int  posicion=0;
        for (int i = 0; i<listUnidad.size()  ;i++){
            Log.i("UnidadSpinner", "Comparamos con "+listUnidad.get(i).getIdUnidad() );
            if(listUnidad.get(i).getIdUnidad() == idUnidad){
                posicion=i + 1;
                Log.i("UnidadSpinner", "Está en la posición:  "+posicion );
            }
        }

        return posicion;
    }

    //Fin de métodos
}

package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;

import java.util.ArrayList;

public class CicloSpinner {
    //Inicio Atributos
    private ArrayList<Ciclo> listCiclo;
    private  ArrayList<String> contenidoCiclo; //para tipo de grupo
    private Cursor cursor ;
    //Fin Atributos

    // Inicio de los constructores
    public CicloSpinner(ControlBD helpercontexto) {
        //Código para mostar las listas de String
        String Consulta = "select  idciclo, nombreciclo from ciclo";
        cursor = helpercontexto.consultar(Consulta);
        Ciclo ciclo;
        listCiclo = new ArrayList<Ciclo>();
        contenidoCiclo = new ArrayList<String>();
        contenidoCiclo.add("Seleccione"); // genera un desfase con respecto a la lista de objetos
        while (cursor.moveToNext()) {
            ciclo = new Ciclo();
            ciclo.setIdCiclo(cursor.getInt(0));
            ciclo.setNombreCiclo(cursor.getString(1));
            contenidoCiclo.add(cursor.getString(1)); // llenamos la lista
            listCiclo.add(ciclo);

        }
    }

    //Inicio de métodos
    public ArrayAdapter getAdapterCiclo (Context ctx){
        ArrayAdapter adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, contenidoCiclo);
        // aquí cargamos lo que queremos mostrar en nuestro caso es la lista de contenido
        return adapter ;
    }

    public int getIdCiclo(int posicion){
        int pos = 0;
        pos = posicion-1 ; // se resta 1 por el desfase generado con la lista de contendio
        return listCiclo.get(pos).getIdCiclo();
    }

    public int buscarCiclo(int idUnidad){
        int  posicion=0;
        for (int i = 0; i< listCiclo.size()  ; i++){
            Log.i("CicloSpinner", "Comparamos con "+ listCiclo.get(i).getIdCiclo() );
            if(listCiclo.get(i).getIdCiclo() == idUnidad){
                posicion=i + 1;
                Log.i("CicloSpinner", "Está en la posición:  "+posicion );
            }
        }

        return posicion;
    }

    //Fin de métodos

}

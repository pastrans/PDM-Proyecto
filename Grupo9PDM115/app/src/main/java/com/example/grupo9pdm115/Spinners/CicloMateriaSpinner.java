package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;

import java.util.ArrayList;

public class CicloMateriaSpinner {
    private ArrayList<CicloMateria> listCicloMateria;
    private  ArrayList<String> contenidoMateria;// para Materia
    private Cursor cursor ;

    public CicloMateriaSpinner(ControlBD helpercontexto, String ahora){
        //código para llenar las listas de CilcoMateria y contenido
        String Consulta = "SELECT cm.IDCICLOMATERIA,cm.IDCICLO, cm.CODMATERIA, m.NOMBREMAT FROM ciclomateria cm, materia m, ciclo c where m.CODMATERIA = cm.CODMATERIA and c.IDCICLO = cm.IDCICLO AND '" + ahora + "' BETWEEN c.INICIO and c.FIN";
        cursor = helpercontexto.consultar(Consulta);
        CicloMateria cicloMateria;
        listCicloMateria = new ArrayList<CicloMateria>();
        contenidoMateria = new ArrayList<String>();
        contenidoMateria.add("Seleccione"); // genera un desfase con respecto a la lista de objetos de materia
        while (cursor.moveToNext()) {
            cicloMateria = new CicloMateria();
            cicloMateria.setIdCicloMateria(cursor.getInt(0));
            cicloMateria.setIdCiclo(cursor.getInt(1));
            cicloMateria.setCodMateria(cursor.getString(2));
            contenidoMateria.add(cursor.getString(3));
            listCicloMateria.add(cicloMateria);
        }
    }

    public ArrayAdapter getAdapterMateria (Context ctx){
        ArrayAdapter adapter = new ArrayAdapter<String>( ctx.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,contenidoMateria);
        // aquí cargamos lo que queremos mostrar en nuestro caso es la lista de contenidoMateria
        return adapter ;
    }

    public int getIdCicloMateria(int posicion){
        int pos = 0;
        pos = posicion-1 ; // se resta 1 por el desfase generado con la lista de contendio
        return listCicloMateria.get(pos).getIdCicloMateria();
    }

}

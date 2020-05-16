package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.TipoGrupo;

import java.util.ArrayList;

public class NuevoGrupoSpinners {




    //Inicio Atributos
    private  ArrayList<TipoGrupo> lisTipoGrupo;
    private  ArrayList<String> contenido;
    private ControlBD helper;
    private Cursor cursor ;
    //Fin Atributps


    // Inicio de los constructores

    public NuevoGrupoSpinners(ControlBD helpercontexto) {
        String Consulta = "SELECT * FROM TIPOGRUPO";
        cursor = helpercontexto.consultar(Consulta);
        TipoGrupo tipoGrupo;
        lisTipoGrupo = new ArrayList<TipoGrupo>();
        contenido = new ArrayList<String>();
        while(cursor.moveToNext()){
            tipoGrupo= new TipoGrupo();
            tipoGrupo.setIdTipoGrupo(cursor.getInt(0));
            tipoGrupo.setNombreTipoGrupo(cursor.getString(1));
            lisTipoGrupo.add(tipoGrupo);
        }
        contenido.add("Seleccione");
        for (int i=0; i< lisTipoGrupo.size(); i++){
            contenido.add(lisTipoGrupo.get(i).getNombreTipoGrupo());
        }
    }


    // Fin de los constructores

    //Incio de Métodos Getter y setter

    //Fin de Métodos Getter y setter

    //Inicio de métodos
    public ArrayAdapter getAdapterTipoGrupo ( Context ctx){
        ArrayAdapter adapter = new ArrayAdapter<String>( ctx.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,contenido);
        return adapter ;
    }

    public int getIdTipoGrupo(int posicion){
        int pos = 0;
        pos = posicion-1 ;
        return lisTipoGrupo.get(pos).getIdTipoGrupo();
    }
    //Fin de métodos
}

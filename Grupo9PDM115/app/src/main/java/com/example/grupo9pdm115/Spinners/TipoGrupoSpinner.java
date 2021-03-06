package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;

public class TipoGrupoSpinner {

    private ArrayList<TipoGrupo> lisTipoGrupo;
    private  ArrayList<String> contenidoTipoGrupo; //para tipo de grupo
    private Cursor cursor ;
    private ControlBD helper;

    public TipoGrupoSpinner(Context context){
        /// Ejecutar consulta
        helper = new ControlBD(context);
        helper.abrir();
        String Consulta = "SELECT * FROM TIPOGRUPO";
        cursor = helper.consultar(Consulta);

        TipoGrupo tipoGrupo;
        lisTipoGrupo = new ArrayList<TipoGrupo>();
        contenidoTipoGrupo = new ArrayList<String>();
        contenidoTipoGrupo.add(context.getString(R.string.txtSelecTipo)); // contenidoTipoGrupo.add("Seleccione");
        while (cursor.moveToNext()) {
            tipoGrupo = new TipoGrupo();
            tipoGrupo.setIdTipoGrupo(cursor.getInt(0));
            tipoGrupo.setNombreTipoGrupo(cursor.getString(1));
            contenidoTipoGrupo.add(cursor.getString(1)); // llenamos la lista
            lisTipoGrupo.add(tipoGrupo);
        }

        helper.cerrar();
    }

    public ArrayAdapter getAdapterTipoGrupo (Context ctx){
        ArrayAdapter adapter = new ArrayAdapter<String>( ctx, android.R.layout.simple_spinner_dropdown_item,contenidoTipoGrupo);
        // aquí cargamos lo que queremos mostrar en nuestro caso es la lista de contenido
        return adapter ;
    }

    public int getIdTipoGrupo(int posicion){
        int pos = 0;
        pos = posicion-1 ; // se resta 1 por el desfase generado con la lista de contendio
        return lisTipoGrupo.get(pos).getIdTipoGrupo();
    }

}

package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.TipoGrupo;

import java.util.ArrayList;

public class GrupoSpinner {
    private ArrayList<Grupo> listaGrupo;
    private ArrayList<String> contenidoGrupo;
    private Cursor cursor;

    public GrupoSpinner(ControlBD helper, Context context){
        String sql = "SELECT g.IDGRUPO, g.IDTIPOGRUPO, g.IDCICLOMATERIA, g.NUMERO FROM GRUPO g, CICLOMATERIA cm, MATERIA m WHERE g.IDCICLOMATERIA = cm.IDCICLOMATERIA AND m.CODMATERIA = cm.CODMATERIA ORDER BY m.NOMBREMAT;";
        cursor = helper.consultar(sql);
        Grupo grupo;
        listaGrupo = new ArrayList<Grupo>();
        contenidoGrupo = new ArrayList<String>();
        contenidoGrupo.add("Seleccione un grupo");
        while (cursor.moveToNext()){
            grupo = new Grupo();
            grupo.setIdGrupo(cursor.getInt(0));
            grupo.setIdTipoGrupo(cursor.getInt(1));
            grupo.setIdCicloMateria(cursor.getInt(2));
            grupo.setNumero(cursor.getInt(3));
            CicloMateria cicloMateria = new CicloMateria();
            TipoGrupo tipoGrupo = new TipoGrupo();
            Materia materia = new Materia();
            cicloMateria.consultar(context, String.valueOf(grupo.getIdCicloMateria()));
            tipoGrupo.consultar(context, String.valueOf(grupo.getIdTipoGrupo()));
            materia.consultar(context, String.valueOf(cicloMateria.getCodMateria()));
            String nombre = materia.getNombreMateria() + " - " + tipoGrupo.getNombreTipoGrupo() + " " + String.valueOf(grupo.getNumero());
            contenidoGrupo.add(nombre);
            listaGrupo.add(grupo);
        }
    }

    public ArrayAdapter getAdapterGrupo(Context context){
        ArrayAdapter adapter = new ArrayAdapter<String>(context.getApplicationContext(), android.R.layout.simple_spinner_item, contenidoGrupo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public int getIdGrupo(int posicion){
        int pos = 0;
        pos = posicion - 1;
        return listaGrupo.get(pos).getIdGrupo();
    }

    public Grupo getRol(int id){
        Grupo g = new Grupo();
        g = listaGrupo.get(id);
        return g;
    }
}

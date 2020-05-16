package com.example.grupo9pdm115.Spinners;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.TipoGrupo;

import java.util.ArrayList;

public class NuevoGrupoSpinners {

    //Inicio Atributos
    private  ArrayList<TipoGrupo> lisTipoGrupo;
    private  ArrayList<CicloMateria> listCicloMateria;
    private  ArrayList<String> contenidoTipoGrupo; //para tipo de grupo
    private  ArrayList<String> contenidoMateria;// para Materia
    private Cursor cursor ;
    //Fin Atributos


    // Inicio de los constructores
    public NuevoGrupoSpinners(ControlBD helpercontexto) {
        //Código para mostar las listas de String
        String Consulta = "SELECT * FROM TIPOGRUPO";
        cursor = helpercontexto.consultar(Consulta);
        TipoGrupo tipoGrupo;
        lisTipoGrupo = new ArrayList<TipoGrupo>();
        contenidoTipoGrupo = new ArrayList<String>();
        contenidoTipoGrupo.add("Seleccione"); // genera un desfase con respecto a la lista de objetos de TipoGrupo
        while (cursor.moveToNext()) {
            tipoGrupo = new TipoGrupo();
            tipoGrupo.setIdTipoGrupo(cursor.getInt(0));
            tipoGrupo.setNombreTipoGrupo(cursor.getString(1));
            contenidoTipoGrupo.add(cursor.getString(1)); // llenamos la lista
            lisTipoGrupo.add(tipoGrupo);

        }

        //código para llenar las listas de CilcoMateria y contenido
        Consulta = "SELECT cm.IDCICLOMATERIA,cm.IDCICLO, cm.CODMATERIA, m.NOMBREMAT FROM ciclomateria cm, materia m where m.CODMATERIA = cm.CODMATERIA\n";
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
        //Fin del código para llenar las listas de Materias y contenido
        // Fin de los constructores
    }
    //Inicio de métodos
    public ArrayAdapter getAdapterTipoGrupo ( Context ctx){
        ArrayAdapter adapter = new ArrayAdapter<String>( ctx.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,contenidoTipoGrupo);
        // aquí cargamos lo que queremos mostrar en nuestro caso es la lista de contenido
        return adapter ;
    }
    public ArrayAdapter getAdapterMateria ( Context ctx){
        ArrayAdapter adapter = new ArrayAdapter<String>( ctx.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,contenidoMateria);
        // aquí cargamos lo que queremos mostrar en nuestro caso es la lista de contenidoMateria
        return adapter ;
    }

    public int getIdTipoGrupo(int posicion){
        int pos = 0;
        pos = posicion-1 ; // se resta 1 por el desfase generado con la lista de contendio
        return lisTipoGrupo.get(pos).getIdTipoGrupo();
    }

    public int getIdCicloMateria(int posicion){
        int pos = 0;
        pos = posicion-1 ; // se resta 1 por el desfase generado con la lista de contendio
        return listCicloMateria.get(pos).getIdCicloMateria();
    }
    //Fin de métodos

}

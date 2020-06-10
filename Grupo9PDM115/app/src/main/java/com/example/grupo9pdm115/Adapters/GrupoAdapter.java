package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GrupoAdapter extends ArrayAdapter<Grupo> {

    ControlBD helper;

    public GrupoAdapter(Context context, List<Grupo> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        helper = new ControlBD(getContext().getApplicationContext());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_grupo, parent, false);
        }
        TextView txtNombreGrupoLV = (TextView) convertView.findViewById(R.id.txtNombreGrupoLV);
        TextView txtNombreMateriaLV = (TextView) convertView.findViewById(R.id.txtNombreMateriaGrupoLV);
        TextView txtTipoGrupoLV = (TextView) convertView.findViewById(R.id.txtTipoGrupoLV);
        Grupo grupo = getItem(position);
        helper.abrir();
        Cursor cursor = helper.consultar("SELECT MAT.NOMBREMAT, MAT.CODMATERIA, TG.NOMBRETIPOGRUPO FROM MATERIA MAT, TIPOGRUPO TG, CICLOMATERIA CM, GRUPO GP WHERE MAT.CODMATERIA = CM.CODMATERIA AND TG.IDTIPOGRUPO = GP.IDTIPOGRUPO AND CM.IDCICLOMATERIA = GP.IDCICLOMATERIA AND GP.IDGRUPO=" + grupo.getIdGrupo());
        if (cursor.moveToNext()) {
            txtNombreGrupoLV.setText("Número de grupo: " + String.valueOf(grupo.getNumero()));
            txtNombreMateriaLV.setText("Materia: " + String.valueOf(cursor.getString(0) + " - " + cursor.getString(1)));
            txtTipoGrupoLV.setText("Tipo de grupo: " + String.valueOf(cursor.getString(2)));
        }
        helper.cerrar();
        return  convertView;
    }

}

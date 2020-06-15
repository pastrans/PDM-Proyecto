package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

import java.util.List;

public class CicloMateriaAdapter  extends ArrayAdapter<CicloMateria> {
    public CicloMateriaAdapter (Context context, List<CicloMateria> objects) {super(context, 0,objects);}
    Materia materia;
    Ciclo   ciclo;
    Unidad unidad;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_ciclo_materia,
                    parent,
                    false);
        }

        // Referencias UI - list_item_materia-
        TextView codMateria = (TextView) convertView.findViewById(R.id.txtCodMateria);
        TextView nombreUnidad = (TextView) convertView.findViewById(R.id.txtUnidad);
        TextView ciclonombre = (TextView) convertView.findViewById(R.id.txtnombreCiclo);



        // Dia actual
        CicloMateria cmActual = getItem(position);
        materia = new Materia();
        ciclo = new Ciclo();
        unidad = new Unidad();
        // Setup view
        // llenamos los campos de materia
        //Log.i("CicloMateriaAdapter", "codigo materia: "+cmActual.getCodMateria());
        materia.consultar(getContext(),cmActual.getCodMateria());
        codMateria.setText(materia.getCodMateria());
        // llenamos los campos de unidad
        unidad.consultar(getContext(), Integer.toString(materia.getIdUnidad()));
        nombreUnidad.setText(unidad.getNombreent());
        // llenamos los campos de ciclo
        ciclo.consultar(getContext(), Integer.toString(cmActual.getIdCiclo()));
        ciclonombre.setText(ciclo.getNombreCiclo());


        return convertView;
    }
}

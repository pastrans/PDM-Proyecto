package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.R;

import java.util.List;

public class MateriaAdapter extends ArrayAdapter<Materia> {
    public MateriaAdapter (Context context, List<Materia> objects) {super(context, 0,objects);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_materia,
                    parent,
                    false);
        }

        // Referencias UI - list_item_materia-
        TextView codMateria = (TextView) convertView.findViewById(R.id.txtCodMateria);
        TextView nombre = (TextView) convertView.findViewById(R.id.txtnombre);
        TextView idUnidad = (TextView) convertView.findViewById(R.id.txtIdUnidad);
        TextView masividad = (TextView) convertView.findViewById(R.id.txtMasividad);

        // Dia actual
        Materia materia = getItem(position);

        // Setup view
        codMateria.setText(materia.getCodMateria());
        nombre.setText(materia.getNombreMateria());
        idUnidad.setText(materia.getIdUnidad());
        if(materia.isMasiva())
            masividad.setText("Masiva");
        else
            masividad.setText("No masiva");
        return convertView;
    }
}

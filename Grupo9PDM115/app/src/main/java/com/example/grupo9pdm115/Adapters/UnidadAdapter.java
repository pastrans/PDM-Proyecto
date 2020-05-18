package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

import java.util.List;

public class UnidadAdapter extends ArrayAdapter<Unidad> {
    public UnidadAdapter (Context context, List<Unidad> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_unidad,
                    parent,
                    false);
        }

        // Referencias UI - list_item_dia -
        TextView nombreUnidad = (TextView) convertView.findViewById(R.id.txtNombreUnidad);
        TextView descripcion = (TextView) convertView.findViewById(R.id.txtDescripcion);
        TextView prioridad = (TextView) convertView.findViewById(R.id.txtPrioridad);

        // Unidad actual
        Unidad unidad = getItem(position);

        // Setup view
        nombreUnidad.setText(unidad.getNombreent());
        descripcion.setText(unidad.getDescripcionent());
        prioridad.setText(unidad.getPrioridad());


        return convertView;
    }
}

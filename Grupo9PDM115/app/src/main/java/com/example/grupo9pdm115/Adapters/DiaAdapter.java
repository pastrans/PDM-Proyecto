package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.R;

import java.util.List;

public class DiaAdapter extends ArrayAdapter<Dia> {

    public DiaAdapter (Context context, List<Dia> objects) {
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
                    R.layout.list_item_dia,
                    parent,
                    false);
        }

        // Referencias UI -list_item_dia
        TextView nombreDia = (TextView) convertView.findViewById(R.id.txtNombreDia);

        // Dia actual
        Dia dia = getItem(position);

        // Setup
        nombreDia.setText(dia.getNombreDia());

        return convertView;
    }
}

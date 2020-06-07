package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.R;
import java.util.List;

public class FeriadoAdapter  extends ArrayAdapter<Feriado> {
    public FeriadoAdapter (Context context, List<Feriado> objects){super(context, 0,objects);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_feriado,
                    parent,
                    false);
        }

        // Referencias UI - list_item_ciclo -
        TextView nombreFeriado = (TextView) convertView.findViewById(R.id.txtNombreFeriado);
        TextView descripcion = (TextView) convertView.findViewById(R.id.txtDescripcion);
        TextView inicio = (TextView) convertView.findViewById(R.id.txtInicioFeriado);
        TextView fin = (TextView) convertView.findViewById(R.id.txtFinFeriado);


        // Feriado actual
        Feriado feriado = getItem(position);

        // Setup view
        nombreFeriado.setText(feriado.getNombreFeriado());
        inicio.setText(feriado.getFechaInicioFeriado());
        fin.setText(feriado.getFechaFinFeriado());
        descripcion.setText(feriado.getDescripcionFeriado());

        return convertView;
    }
}
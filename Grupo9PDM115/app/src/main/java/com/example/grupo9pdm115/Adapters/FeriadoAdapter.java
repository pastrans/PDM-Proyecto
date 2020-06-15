package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Ciclo;
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
        TextView txtNombreFeriado = (TextView) convertView.findViewById(R.id.txtNombreFeriado);
        TextView txtDescripcionFeriado = (TextView) convertView.findViewById(R.id.txtDescripcionFeriado);
        TextView txtNombreCiclo = (TextView) convertView.findViewById(R.id.txtNombreCiclo);
        TextView txtInicioFeriado = (TextView) convertView.findViewById(R.id.txtInicioFeriado);
        TextView txtFinFeriado = (TextView) convertView.findViewById(R.id.txtFinFeriado);
        TextView txtBloquearReservas = (TextView) convertView.findViewById(R.id.txtBloquearReservas);

        // Feriado actual
        Feriado feriado = getItem(position);
        // Ciclo relacionado
        Ciclo ciclo = new Ciclo();
        ciclo.consultar(getContext().getApplicationContext(), String.valueOf(feriado.getIdCiclo()));

        // Setup view
        txtNombreFeriado.setText(feriado.getNombreFeriado());
        txtDescripcionFeriado.setText(feriado.getDescripcionFeriado());
        txtNombreCiclo.setText(ciclo.getNombreCiclo());
        txtInicioFeriado.setText(feriado.getFechaInicioFeriadoToLocal());
        txtFinFeriado.setText(feriado.getFechaFinFeriadoToLocal());
        txtBloquearReservas.setText(feriado.getBloquearReservasToText());

        return convertView;
    }
}
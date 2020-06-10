package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.R;
import java.util.List;

public class CicloAdapter extends ArrayAdapter<Ciclo> {
    public CicloAdapter (Context context, List<Ciclo> objects) {super(context, 0,objects);}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_ciclo,
                    parent,
                    false);
        }

        // Referencias UI - list_item_ciclo -
        TextView nombreCiclo = (TextView) convertView.findViewById(R.id.txtNombreCiclo);
        TextView inicio = (TextView) convertView.findViewById(R.id.txtInicio);
        TextView fin = (TextView) convertView.findViewById(R.id.txtFin);
        TextView estadoCiclo = (TextView) convertView.findViewById(R.id.txtEstadoCiclo);
        TextView inicioPeriodoClase = (TextView) convertView.findViewById(R.id.txtInicioPeriodoClase);
        TextView finPeriodoClase = (TextView) convertView.findViewById(R.id.txtFinPeriodoClase);

        // Dia actual
        Ciclo ciclo = getItem(position);

        // Setup view
        nombreCiclo.setText(ciclo.getNombreCiclo());
        inicio.setText(ciclo.getInicio());
        fin.setText(ciclo.getFin());

        // Modificando el valor para mostrar estado en string y cambiando el color del background al activo
        if(ciclo.isEstadoCiclo()){
            estadoCiclo.setText("Activo");
            convertView.setBackgroundColor(Color.parseColor("#B2fF59"));
        }
        else
            estadoCiclo.setText("Inactivo");

        inicioPeriodoClase.setText(ciclo.getInicioPeriodoClase());
        finPeriodoClase.setText(ciclo.getFinPeriodoClase());

        return convertView;
    }
}

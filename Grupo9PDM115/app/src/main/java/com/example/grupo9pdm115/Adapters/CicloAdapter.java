package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CicloAdapter extends ArrayAdapter<Ciclo> {
    private Context context;

    public CicloAdapter (Context context, List<Ciclo> objects) {
        super(context, 0,objects);
        this.context = context;
    }

    @NotNull
    public Context getContext(){
        return this.context;
    }

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
        TextView txtNombreCiclo = (TextView) convertView.findViewById(R.id.txtNombreCiclo);
        TextView txtInicio = (TextView) convertView.findViewById(R.id.txtInicio);
        TextView txtFin = (TextView) convertView.findViewById(R.id.txtFin);
        TextView txtEstadoCiclo = (TextView) convertView.findViewById(R.id.txtEstadoCiclo);
        TextView txtInicioPeriodoClase = (TextView) convertView.findViewById(R.id.txtInicioPeriodoClase);
        TextView txtFinPeriodoClase = (TextView) convertView.findViewById(R.id.txtFinPeriodoClase);

        // Ciclo actual
        Ciclo ciclo = getItem(position);

        // Setup view
        txtNombreCiclo.setText(ciclo.getNombreCiclo());
        txtInicio.setText(ciclo.getInicioToLocal());
        txtFin.setText(ciclo.getFinToLocal());

        // Modificando el valor para mostrar estado en string y cambiando el color del background al activo
        if(ciclo.isEstadoCiclo()){
            txtEstadoCiclo.setText("Activo");
            // convertView.setBackgroundColor(Color.parseColor("#B2fF59"));
            //convertView.setBackgroundColor(Color.parseColor("#90caf9"));
            /*
            final TypedValue value = new TypedValue ();
            context.getTheme().resolveAttribute (R.attr.colorButtonNormal, value, true);
            convertView.setBackgroundColor(value.data);
             */
            TypedValue typedValue = new TypedValue();

            TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
            int color = a.getColor(0, 0);
            convertView.setBackgroundColor(color);
            a.recycle();
        }
        else
            txtEstadoCiclo.setText("Inactivo");

        txtInicioPeriodoClase.setText(ciclo.getInicioPeriodoClaseToLocal());
        txtFinPeriodoClase.setText(ciclo.getFinPeriodoClaseToLocal());

        return convertView;
    }
}

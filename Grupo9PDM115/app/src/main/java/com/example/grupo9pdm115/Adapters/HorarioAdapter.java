package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.R;

import java.util.List;

public class HorarioAdapter extends ArrayAdapter<Horario> {
    public HorarioAdapter(Context ctx, List<Horario> objects){
        super(ctx,0,objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_horario, parent, false);
        }
        TextView txtIdHorario = (TextView) convertView.findViewById(R.id.txtIdHorario);
        TextView txtHoraInicio = (TextView) convertView.findViewById(R.id.txtHoraInicio);
        TextView txtHoraFin = (TextView) convertView.findViewById(R.id.txtHoraFin);
        Horario hora = getItem(position);
        txtIdHorario.setText(Integer.toString(hora.getIdHora()));
        txtHoraInicio.setText(hora.getHoraInicio());
        txtHoraFin.setText(hora.getHoraFinal());
        return  convertView;
    }
}

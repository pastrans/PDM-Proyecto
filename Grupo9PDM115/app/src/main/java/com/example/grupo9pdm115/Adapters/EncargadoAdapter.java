package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.R;

import java.util.List;

public class EncargadoAdapter extends ArrayAdapter<Encargado> {
    public EncargadoAdapter(Context ctx , List<Encargado> objects){
        super(ctx, 0,objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_encargado, parent, false);
        }
        //TextView txtNomUsu = (TextView) convertView.findViewById(R.id.txtNombreUsuario);
        TextView txtIdUsu = (TextView) convertView.findViewById(R.id.txtIdUsuario);
        //TextView txtIdEncargado = (TextView) convertView.findViewById(R.id.txtIdEncargado);
        Encargado enc = getItem(position);
        //txtNomUsu.setText(enc.getUsuario().getNombreUsuario());
        txtIdUsu.setText(enc.getIdUsuario());
        //txtIdEncargado.setText(Integer.toString(enc.getIdEncargado()));
        return  convertView;
    }

}

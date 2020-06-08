package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

import java.util.List;

public class RolAdapter extends ArrayAdapter<Rol> {

    public RolAdapter(Context context, List<Rol> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_rol, parent, false);
        }
        TextView txtNombreRol = (TextView) convertView.findViewById(R.id.txtNombreRolList);
        Rol rol = getItem(position);
        txtNombreRol.setText(rol.getNombreRol());
        return  convertView;
    }

}

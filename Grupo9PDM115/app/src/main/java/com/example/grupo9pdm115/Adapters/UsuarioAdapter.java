package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    public UsuarioAdapter(Context context, List<Usuario> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_usuario, parent, false);
        }
        TextView txtNombreCompletoPersonal = (TextView) convertView.findViewById(R.id.txtNombreCompletoPersonal);
        TextView txtNombreUsuario = (TextView) convertView.findViewById(R.id.txtNombreUsuarioList);
        TextView txtCorreoUsuaario = (TextView) convertView.findViewById(R.id.txtCorreoPersonalList);
        Usuario usuario = getItem(position);
        txtNombreCompletoPersonal.setText(usuario.getNombrePersonal() + " " + usuario.getApellidoPersonal());
        txtNombreUsuario.setText(usuario.getNombreUsuario());
        txtCorreoUsuaario.setText(usuario.getCorreoPersonal());
        return  convertView;
    }
}

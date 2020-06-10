package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

import java.util.List;

public class TipoLocalAdapter extends ArrayAdapter<TipoLocal> {
    public TipoLocalAdapter(Context context, List<TipoLocal> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_tipo_local, parent, false);
        }
        TextView txtNombreTipo = (TextView) convertView.findViewById(R.id.txtNombreTipoList);
        TextView txtEncargadoLocal = (TextView) convertView.findViewById(R.id.txtEncargadoTipoLista);
        TipoLocal tipoLocal = getItem(position);
        Usuario usuario = new Usuario();
        usuario.consultar(getContext().getApplicationContext(), tipoLocal.getIdEncargado());
        txtNombreTipo.setText("Nombre: " + tipoLocal.getNombreTipo());
        txtEncargadoLocal.setText("Encargado: " + usuario.getNombrePersonal() + " " + usuario.getApellidoPersonal());
        return  convertView;
    }
}

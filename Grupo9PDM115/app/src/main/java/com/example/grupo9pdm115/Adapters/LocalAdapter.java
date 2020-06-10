package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;

import java.util.List;

public class LocalAdapter extends ArrayAdapter<Local> {
    public LocalAdapter(Context context, List<Local> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_local, parent, false);
        }
        TextView txtNombreLocal = (TextView) convertView.findViewById(R.id.txtNombreLocalList);
        TextView txtCapacidad = (TextView) convertView.findViewById(R.id.txtCapacidadList);
        TextView txtTipoLocal = (TextView) convertView.findViewById(R.id.txtTipoLocalListLocal);
        Local local = getItem(position);
        TipoLocal tipoLocal = new TipoLocal();
        tipoLocal.consultar(getContext().getApplicationContext(), String.valueOf(local.getIdtipolocal()));
        txtNombreLocal.setText("Nombre: " + local.getNombreLocal());
        txtCapacidad.setText("Capacidad: " +String.valueOf(local.getCapacidad()));
        txtTipoLocal.setText("Tipo de local: " +  tipoLocal.getNombreTipo());
        return  convertView;
    }
}

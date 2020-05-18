package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

import java.util.List;

public class TipoGrupoAdapter extends ArrayAdapter<TipoGrupo> {

    public TipoGrupoAdapter (Context context, List<TipoGrupo> objects) { super(context, 0, objects); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_tipo_grupo,
                    parent,
                    false);
        }

        // Referencias UI - list_item_tipo_grupo
        TextView nombreTipoGrupo = (TextView) convertView.findViewById(R.id.txtNombreTipoGrupo);

        // TipoGrupo actual
        TipoGrupo tipogrupo = getItem(position);

        // Setup view
        nombreTipoGrupo.setText(tipogrupo.getNombreTipoGrupo());


        return convertView;
    }
}

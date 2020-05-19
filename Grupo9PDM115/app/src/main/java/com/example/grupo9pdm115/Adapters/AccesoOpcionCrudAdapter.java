package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grupo9pdm115.Modelos.OpcionCrud;
import com.example.grupo9pdm115.R;

import java.util.List;

public class AccesoOpcionCrudAdapter extends ArrayAdapter<OpcionCrud> {

    public AccesoOpcionCrudAdapter(@NonNull Context context,  @NonNull List<OpcionCrud> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (null == convertView){
            convertView = inflater.inflate(R.layout.list_opcion_crud, parent, false);
        }
        CheckBox chkOpcionCrud = (CheckBox) convertView.findViewById(R.id.chkOpcionCrud);
        OpcionCrud oc = getItem(position);
        chkOpcionCrud.setText(oc.getIdOpcion() + " - " + oc.getDescripcion());
        return convertView;
    }
}

package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Coordinacion;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

import java.util.List;

public class CoordinacionAdapter  extends ArrayAdapter<Coordinacion> {
    public CoordinacionAdapter(Context context, List<Coordinacion> objects) {super(context, 0,objects);}
    CicloMateria cm;
    Usuario usuario;
    Materia materia;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.list_item_coordinacion,
                    parent,
                    false);
        }

        // Referencias UI - list_item_materia-
        TextView codMateria = (TextView) convertView.findViewById(R.id.txtCodMateria);
        TextView nombreUsuario = (TextView) convertView.findViewById(R.id.txtnombreUsuario);



        // Dia actual
        Coordinacion coordinacionActual = getItem(position);
        cm = new CicloMateria();
        usuario = new Usuario();
        materia = new Materia();
        // Setup view
        // llenamos los campos

        cm.consultar(getContext(),Integer.toString(coordinacionActual.getIdCicloMateria()));
        codMateria.setText( cm.getCodMateria()+ " " + coordinacionActual.getTipoCoordinacion());
        // llenamos los campos de unidad
        usuario.consultar(getContext(),coordinacionActual.getIdUsuario());
        nombreUsuario.setText("Coordinador: " + usuario.getNombrePersonal());


        return convertView;
    }
}

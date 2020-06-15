package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Utilidades.FechasHelper;

import java.util.List;

public class SolicitudAdapter extends ArrayAdapter<Solicitud> {

    public SolicitudAdapter(Context context, List<Solicitud> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_solicitud, parent, false);
        }
        TextView txtAsunto = (TextView) convertView.findViewById(R.id.txtAsuntoGSolicitud);
        TextView txtFechaSolicitud = (TextView) convertView.findViewById(R.id.txtFechaRealizadaGSolicitud);
        TextView txtFechaRespuesta = (TextView) convertView.findViewById(R.id.txtFechaRespuestaGSolicitud);
        TextView txtEstado = (TextView) convertView.findViewById(R.id.txtEstadoSolicitudG);
        TextView txtAprobado = (TextView) convertView.findViewById(R.id.txtAprobadoTotalSolicitudG);
        Solicitud solicitud = getItem(position);
        txtAsunto.setText(solicitud.getAsuntoSolicitud());
        txtFechaSolicitud.setText(FechasHelper.cambiarFormatoIsoALocal(solicitud.getFechaRealizada()));
        txtFechaRespuesta.setText(solicitud.getFechaRespuesta());
        if(txtFechaRespuesta.getText().toString().trim().equals(""))
            txtFechaRespuesta.setText("Sin fecha información");
        else
            txtFechaRespuesta.setText(FechasHelper.cambiarFormatoIsoALocal(solicitud.getFechaRespuesta()));
        if(solicitud.isEstadoSolicitud())
            txtEstado.setText("Revisada");
        else
            txtEstado.setText("Sin revisión");
        if(solicitud.isAprobadoTotal())
            txtAprobado.setText("Aprobada totalmente");
        else if(solicitud.isAprobadoTotal() && solicitud.isEstadoSolicitud())
            txtAprobado.setText("Aprobada parcialmente");
        else
            txtAprobado.setText("Sin información");
        return  convertView;
    }

}

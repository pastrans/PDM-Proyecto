package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.Modelos.EventoEspecial;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.R;

import java.util.List;

public class DetalleReservaAdapter extends ArrayAdapter<DetalleReserva> {

    private String horaFinal;

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public DetalleReservaAdapter(Context context, List<DetalleReserva> objects, String horaFinal){
        super(context, 0, objects);
        this.horaFinal = horaFinal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_detalle_reserva, parent, false);
        }

        TextView txtMateria = (TextView) convertView.findViewById(R.id.txtMateriaGDetalleR);
        TextView txtLocal = (TextView) convertView.findViewById(R.id.txtLocalGDetalleR);
        TextView txtEstado = (TextView) convertView.findViewById(R.id.txtEstadoReservaGDetalleR);
        TextView txtAprobado = (TextView) convertView.findViewById(R.id.txtAprobadoGDetalleR);
        TextView txtDiaHora = (TextView) convertView.findViewById(R.id.txtDiaHoraDetalleRG);

        int idEventoEspecial = 0, idGrupo = 0, idDia, idHora, idLocal;

        DetalleReserva detalleReserva = getItem(position);
        Materia materia = new Materia();
        CicloMateria cicloMateria = new CicloMateria();
        Dia dia = new Dia();
        Grupo grupo = new Grupo();
        Horario horario = new Horario();
        EventoEspecial eventoEspecial = new EventoEspecial();
        Local local = new Local();

        idDia = detalleReserva.getIdDia();
        idLocal = detalleReserva.getIdLocal();
        idHora = detalleReserva.getIdHora();
        idEventoEspecial = detalleReserva.getIdEventoEspecial();
        idGrupo = detalleReserva.getIdGrupo();
        if(detalleReserva.getIdEventoEspecial() != 0){
            eventoEspecial.consultar(getContext().getApplicationContext(), String.valueOf(idEventoEspecial));
            cicloMateria.consultar(getContext().getApplicationContext(), String.valueOf(eventoEspecial.getIdCicloMateria()));
        }
        if(detalleReserva.getIdGrupo() != 0){
            grupo.consultar(getContext().getApplicationContext(), String.valueOf(idGrupo));
            cicloMateria.consultar(getContext().getApplicationContext(), String.valueOf(grupo.getIdCicloMateria()));
        }
        dia.consultar(getContext().getApplicationContext(), String.valueOf(idDia));
        if(idLocal != 0){
            local.consultar(getContext().getApplicationContext(), String.valueOf(idLocal));
            txtLocal.setText(local.getNombreLocal());
        }else{
            txtLocal.setText("Sin asignar");
        }
        horario.consultar(getContext().getApplicationContext(), String.valueOf(idHora));
        materia.consultar(getContext().getApplicationContext(), cicloMateria.getCodMateria());

        txtMateria.setText(materia.getCodMateria() + " " + materia.getNombreMateria());
        if(this.horaFinal.equals(""))
            txtDiaHora.setText(dia.getNombreDia() + " | " + horario.getHoraInicio() + " - " + horario.getHoraFinal());
        else
            txtDiaHora.setText(dia.getNombreDia() + " | " + horario.getHoraInicio() + " - " + horaFinal);
        if(detalleReserva.isEstadoReserva())
            txtEstado.setText("Activa");
        else
            txtEstado.setText("Inactiva");
        if(detalleReserva.isAprobado())
            txtAprobado.setText("Aprobada");
        else
            txtAprobado.setText("No aprobada");
        return  convertView;
    }

}

package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.Modelos.EventoEspecial;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Comun.DetallesQR;

import java.util.List;

public class EventosQRAdapter extends ArrayAdapter<DetallesQR> {
    private String horaFinal;

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public EventosQRAdapter(Context context, List<DetallesQR> objects){
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(null == convertView){
            convertView = inflater.inflate(R.layout.list_item_evento_qr, parent, false);
        }

        TextView txtMateria = (TextView) convertView.findViewById(R.id.txtMateriaGDetalleQR);
        TextView txtLocal = (TextView) convertView.findViewById(R.id.txtLocalGDetalleQR);
        TextView txtHora = (TextView) convertView.findViewById(R.id.txtDiaHoraDetalleQR);

        int idEventoEspecial = 0, idGrupo = 0, idDia, idHora, idLocal;
        String prefijo = "";

        DetallesQR detalleReserva = getItem(position);
        Materia materia = new Materia();
        CicloMateria cicloMateria = new CicloMateria();
        Dia dia = new Dia();
        Grupo grupo = new Grupo();
        Horario horario = new Horario();
        EventoEspecial eventoEspecial = new EventoEspecial();
        Local local = new Local();

        idDia = detalleReserva.getDetalleReserva().getIdDia();
        idLocal = detalleReserva.getDetalleReserva().getIdLocal();
        idHora = detalleReserva.getDetalleReserva().getIdHora();
        idEventoEspecial = detalleReserva.getDetalleReserva().getIdEventoEspecial();
        idGrupo = detalleReserva.getDetalleReserva().getIdGrupo();
        if(detalleReserva.getDetalleReserva().getIdEventoEspecial() != 0){
            eventoEspecial.consultar(getContext().getApplicationContext(), String.valueOf(idEventoEspecial));
            cicloMateria.consultar(getContext().getApplicationContext(), String.valueOf(eventoEspecial.getIdCicloMateria()));
            prefijo = eventoEspecial.getNombreEvento() + " - ";
        }
        if(detalleReserva.getDetalleReserva().getIdGrupo() != 0){
            grupo.consultar(getContext().getApplicationContext(), String.valueOf(idGrupo));
            cicloMateria.consultar(getContext().getApplicationContext(), String.valueOf(grupo.getIdCicloMateria()));
            TipoGrupo tipoGrupo = new TipoGrupo();
            tipoGrupo.consultar(getContext(), String.valueOf(grupo.getIdTipoGrupo()));
            prefijo = tipoGrupo.getNombreTipoGrupo()  + " "  + String.valueOf(grupo.getNumero()) +" - ";
        }
        dia.consultar(getContext().getApplicationContext(), String.valueOf(idDia));
        local.consultar(getContext().getApplicationContext(), String.valueOf(idLocal));
        txtLocal.setText(local.getNombreLocal());

        horario.consultar(getContext().getApplicationContext(), String.valueOf(idHora));
        materia.consultar(getContext().getApplicationContext(), cicloMateria.getCodMateria());

        txtMateria.setText(prefijo + materia.getCodMateria() + " " + materia.getNombreMateria());
        if(idEventoEspecial != 0)
            txtHora.setText(horario.getHoraInicio() + " - " + detalleReserva.getHoraFinal());
        else
            txtHora.setText(horario.getHoraInicio() + " - " + horario.getHoraFinal());
        return  convertView;
    }
}

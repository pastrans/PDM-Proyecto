package com.example.grupo9pdm115.Activities.Feriado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Comun.EventoFeriado;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ConsultarFeriadoEnCalendario extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CFE"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_feriado_en_calendario);



        // Inicializando calendario
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        agregarEventos(); // Agregando eventos al calendario

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                if(eventDay instanceof EventoFeriado){
                    Toast.makeText(getApplicationContext(), ((EventoFeriado) eventDay).getFeriado().getNombreFeriado(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para agregar eventos al calendario
    public void agregarEventos(){
        List<EventDay> events = new ArrayList<>();
        List<Feriado> feriados = new Feriado().getAll(this);

        // Días con evento desabilitados
        //List<Calendar> calendars = new ArrayList<>();

        try {
            for(Feriado feriado : feriados){
                // Si las reservas son permitidas, punto verde, si no, punto rojo
                int dibujo = feriado.isBloquearReservas() ? R.drawable.ic_dot_red : R.drawable.ic_dot_green;

                Date fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse(feriado.getFechaInicioFeriado());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fechaInicio);
                events.add(new EventoFeriado(calendar, dibujo, feriado));
                //calendars.add(calendar);

                // Si es feriado con múltiples fechas
                if(!feriado.getFechaFinFeriado().equals("")){
                    Date fechaSiguiente = fechaInicio;
                    Date fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse(feriado.getFechaFinFeriado());

                    while(fechaSiguiente.compareTo(fechaFin) != 0){
                        Calendar calendarSiguiente = Calendar.getInstance();
                        calendarSiguiente.setTime(fechaSiguiente);
                        calendarSiguiente.add(Calendar.DATE, 1);
                        events.add(new EventoFeriado(calendarSiguiente, dibujo, feriado));
                        fechaSiguiente = calendarSiguiente.getTime();
                        //calendars.add(calendarSiguiente);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Agregando los eventos al calendario
        calendarView.setEvents(events);
    }
}

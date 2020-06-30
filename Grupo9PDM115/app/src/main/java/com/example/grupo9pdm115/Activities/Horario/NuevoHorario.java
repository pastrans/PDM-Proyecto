package com.example.grupo9pdm115.Activities.Horario;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NuevoHorario extends CyaneaAppCompatActivity implements View.OnClickListener{
    ControlBD helper;
    EditText editHInicio,editHFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "IHO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_horario);
        helper = new ControlBD(this);
        editHInicio = (EditText) findViewById(R.id.editHoraInicio);
        editHFinal = (EditText) findViewById(R.id.editHoraFinal);
        editHInicio.setOnClickListener(this);
        editHFinal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final EditText editHora = (EditText) v;
        String horaEnEditTextUnida = ((EditText) v).getText().toString();
        String[] horaEnEditTextSeparada = horaEnEditTextUnida.split(":");
        final Calendar c = Calendar.getInstance();

        // Si el EditText tiene una hora colocar esa hora, de lo contrario, colocar la hora actual
        int hora = horaEnEditTextUnida.equals("") ? c.get(Calendar.HOUR_OF_DAY) : Integer.parseInt(horaEnEditTextSeparada[0]);
        int minuto = horaEnEditTextUnida.equals("") ? c.get(Calendar.MINUTE) : Integer.parseInt(horaEnEditTextSeparada[1]);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                editHora.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, hora, minuto,true);
        timePickerDialog.show();
    }

    public void agregarHorario(View v) throws ParseException {
        Horario horario = new Horario();
        horario.setHoraInicio(editHInicio.getText().toString());
        horario.setHoraFinal(editHFinal.getText().toString());
        validarHora(horario.getHoraInicio(),horario.getHoraFinal(),horario);
    }

    public void validarHora(String horai, String horaf, Horario horario) throws ParseException {
        String regInsertados;
        SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
        if (horario.getHoraInicio().isEmpty()) {
            regInsertados = "Hora inicio está vacío";
        } else {
            if (horario.getHoraFinal().isEmpty()) {
                regInsertados = "Hora final está vacío";
            } else {
                Date t1 = sdformat.parse(horai);
                Date t2 = sdformat.parse(horaf);
                if (t1.compareTo(t2)==0){
                    regInsertados = "Las horas son iguales";
                }else{
                    if (t1.compareTo(t2) > 0){
                        regInsertados = "Las hora inicial es mayor que la hora final";
                    }
                    else{
                        regInsertados = horario.guardar(this);
                        limpiar();
                    }
                }
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }

    private void limpiar() {
        editHInicio.setText("");
        editHFinal.setText("");
    }

    //Limpiar campos
    public void btnLimpiarTextoNHorario(View v) {
        limpiar();
    }
}

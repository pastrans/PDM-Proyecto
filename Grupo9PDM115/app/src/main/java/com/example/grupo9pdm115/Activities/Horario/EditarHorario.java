package com.example.grupo9pdm115.Activities.Horario;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditarHorario extends AppCompatActivity implements View.OnClickListener{

    EditText editHInicio,editHFinal;
    Horario horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "EHO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_horario);
        horario = new Horario();
        editHInicio = (EditText) findViewById(R.id.editHoraInicio);
        editHFinal = (EditText) findViewById(R.id.editHoraFinal);
        editHInicio.setOnClickListener(this);
        editHFinal.setOnClickListener(this);

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            editHInicio.setText(getIntent().getStringExtra("horainicio"));
            editHFinal.setText(getIntent().getStringExtra("horafinal"));
            horario.setIdHora(getIntent().getIntExtra("idhorario",0));
        }
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

    // Método para actualizar Horario
    public void actualizarH(View v) throws ParseException {
        String horai = editHInicio.getText().toString();
        horario.setHoraInicio(horai);
        String horaf = editHFinal.getText().toString();
        horario.setHoraFinal(horaf);
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
                        regInsertados = horario.actualizar(this);
                        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }

    //Limpiar campos
    public void btnLimpiarTextoEHorario(View v) {
        editHInicio.setText("");
        editHFinal.setText("");
    }

}

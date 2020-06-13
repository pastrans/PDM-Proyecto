package com.example.grupo9pdm115.Activities.Horario;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditarHorario extends AppCompatActivity implements View.OnClickListener{
    Button btnHoraInicio,btnHoraFinal;
    EditText editHInicio,editHFinal;
    int Hinicio, Hfinal,Minicio, Mfinal;
    Horario horario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_horario);
        horario = new Horario();
        editHInicio = (EditText) findViewById(R.id.editHoraInicio);
        editHFinal = (EditText) findViewById(R.id.editHoraFinal);
        btnHoraInicio = (Button) findViewById(R.id.btnHInicio);
        btnHoraFinal = (Button) findViewById(R.id.btnHFinal);
        btnHoraInicio.setOnClickListener(this);
        btnHoraFinal.setOnClickListener(this);

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            editHInicio.setText(getIntent().getStringExtra("horainicio"));
            editHFinal.setText(getIntent().getStringExtra("horafinal"));
            horario.setIdHora(getIntent().getIntExtra("idhorario",0));
        }
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
                    }
                }
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View v) {
        if (v==btnHoraInicio){
            final Calendar c = Calendar.getInstance();
            Hinicio=c.get(Calendar.HOUR_OF_DAY);
            Minicio=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    editHInicio.setText(hourOfDay+":"+minute);
                }
            },Hinicio,Minicio,false);
            timePickerDialog.show();
        }
        if (v==btnHoraFinal){
            final Calendar c = Calendar.getInstance();
            Hfinal=c.get(Calendar.HOUR_OF_DAY);
            Mfinal=c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    editHFinal.setText(hourOfDay+":"+minute);

                }
            },Hfinal,Mfinal,false);
            timePickerDialog.show();
        }
    }
    //Limpiar campos
    public void btnLimpiarTextoEHorario(View v) {
        editHInicio.setText("");
        editHFinal.setText("");
    }
}

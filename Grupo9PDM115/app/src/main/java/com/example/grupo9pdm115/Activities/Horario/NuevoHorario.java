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

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

import java.util.Calendar;

public class NuevoHorario extends Activity implements View.OnClickListener{
    ControlBD helper;
    Button btnHoraInicio,btnHoraFinal;
    EditText editHInicio,editHFinal;
    private int Hinicio, Hfinal,Minicio, Mfinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_horario);
        helper = new ControlBD(this);
        btnHoraInicio = (Button) findViewById(R.id.btnHInicio);
        btnHoraFinal = (Button) findViewById(R.id.btnHFinal);
        editHInicio = (EditText) findViewById(R.id.editHoraInicio);
        editHFinal = (EditText) findViewById(R.id.editHoraFinal);
        btnHoraInicio.setOnClickListener(this);
        btnHoraFinal.setOnClickListener(this);

    }
    public void agregarHorario(View v){
        Horario horario = new Horario();
        horario.setHoraInicio(editHInicio.getText().toString());
        horario.setHoraFinal(editHFinal.getText().toString());
        String regInsertados = horario.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    // Método para regresar al activity anterior
    public void regresar(View v){
        super.onBackPressed();
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


}

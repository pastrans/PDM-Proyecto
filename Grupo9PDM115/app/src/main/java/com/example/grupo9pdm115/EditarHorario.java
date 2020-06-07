package com.example.grupo9pdm115;

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

import java.util.Calendar;

public class EditarHorario extends Activity implements View.OnClickListener{
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
    public void actualizarH(View v) {
        //horario.setIdHora(horario.getIdHora());
        String horai = editHInicio.getText().toString();
        horario.setHoraInicio(horai);
        String horaf = editHFinal.getText().toString();
        horario.setHoraFinal(horaf);
        String estado = horario.actualizar(this);
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    // Método para regresar al activity anterior
    public void regresar(View v) {
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

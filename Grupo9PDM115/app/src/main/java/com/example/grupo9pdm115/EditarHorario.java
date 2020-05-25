package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Unidad;

public class EditarHorario extends AppCompatActivity {
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

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            editHInicio.setText(getIntent().getStringExtra("horainicio"));
            editHFinal.setText(getIntent().getStringExtra("horafinal"));
            horario.setIdHora(getIntent().getIntExtra("idhorario",0));
        }
    }
    // Método para actualizar Horario
    public void actualizar(View v) {
        String horai = editHInicio.getText().toString();
        horario.setHoraInicio(horai);
        String horaf = editHFinal.getText().toString();
        horario.setHoraInicio(horaf);
        horario.actualizar(this);
    }

    // Método para regresar al activity anterior
    public void regresar(View v) {
        super.onBackPressed();
    }
}

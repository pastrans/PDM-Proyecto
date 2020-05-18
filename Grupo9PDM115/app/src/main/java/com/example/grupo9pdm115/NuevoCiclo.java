package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;

public class NuevoCiclo extends Activity {
    //Declarando
    ControlBD helper;
    EditText editNombreCiclo;
    DatePicker inicioCicloDatePicker, finCicloDatePicker, inicioPeriodoClaseDatePicker,finPeriodoClaseDatePicker ;
    RadioButton estadoRadioButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ciclo);
        helper = new ControlBD(this);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        inicioCicloDatePicker = (DatePicker) findViewById(R.id.inicioCicloDatePicker);
        finCicloDatePicker = (DatePicker) findViewById(R.id.finCicloDatePicker);
        inicioPeriodoClaseDatePicker = (DatePicker) findViewById(R.id.inicioPeriodoClaseDatePicker);
        finPeriodoClaseDatePicker = (DatePicker) findViewById(R.id.finPeriodoClaseDatePicker);
        estadoRadioButton1 = (RadioButton) findViewById(R.id.estadoRadioButton1);
    }

    //Metodo para insertar ciclo
    public void insertarCiclo(View v){
        //Obteniendo valores elementos
        String nombreCliclo = editNombreCiclo.getText().toString();


        //Instanciando ciclo para guardar
        Ciclo ciclo = new Ciclo();
        ciclo.setNombreCiclo(nombreCliclo);
        String regInsertados = ciclo.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    // Método para regresar al activity anterior
    public void btnRegresarNCiclo(View v){
        super.onBackPressed();
    }
}

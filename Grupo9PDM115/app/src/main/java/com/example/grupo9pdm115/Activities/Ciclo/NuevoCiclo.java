package com.example.grupo9pdm115.Activities.Ciclo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.R;


import java.util.Calendar;

public class NuevoCiclo extends AppCompatActivity implements View.OnClickListener{
    //Declarando
    EditText editNombreCiclo, editInicioCiclo, editFinCiclo, editInicioClases, editFinClases;
    Button btnInicioCiclo, btnFinCiclo, btnInicioClases, btnFinClases;
    private int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ciclo);

        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        editInicioCiclo = (EditText) findViewById(R.id.editInicioCiclo);
        editFinCiclo = (EditText) findViewById(R.id.editFinCiclo);
        editInicioClases = (EditText) findViewById(R.id.editInicioClases);
        editFinClases = (EditText) findViewById(R.id.editFinClases);
        btnInicioCiclo = (Button) findViewById(R.id.btnInicioCiclo);
        btnFinCiclo = (Button) findViewById(R.id.btnFinCiclo);
        btnInicioClases = (Button) findViewById(R.id.btnInicioClases);
        btnFinClases = (Button) findViewById(R.id.btnFinClases);

        btnInicioCiclo.setOnClickListener(this);
        btnFinCiclo.setOnClickListener(this);
        btnInicioClases.setOnClickListener(this);
        btnFinClases.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        EditText ed = null;
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);

        if(v==btnInicioCiclo){
            ed = editInicioCiclo;
        }
        if(v==btnFinCiclo){
            ed = editFinCiclo;
        }
        if(v==btnInicioClases){
           ed = editInicioClases;
        }
        if(v==btnFinClases){
            ed = editFinClases;
        }

        final EditText finalEd = ed;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                finalEd.setText(String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
            }
        },anio,mes,dia);

        datePickerDialog.show();
    }

    // Método para insertar ciclo
    public void agregarCiclo(View v){
        //Obteniendo valores elementos
        String nombreCiclo = editNombreCiclo.getText().toString();
        String inicioCiclo = editInicioCiclo.getText().toString();
        String finCiclo = editFinCiclo.getText().toString();
        String inicioClases = editInicioClases.getText().toString();
        String finClases = editFinClases.getText().toString();

        //Instanciando ciclo para guardar
        Ciclo ciclo = new Ciclo();
        ciclo.setNombreCiclo(nombreCiclo);
        ciclo.setInicio(inicioCiclo);
        ciclo.setFin(finCiclo);
        ciclo.setEstadoCiclo(false); // Se almacena como inactivo por defecto
        ciclo.setInicioPeriodoClase(inicioClases);
        ciclo.setFinPeriodoClase(finClases);
        String regInsertados = ciclo.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    // Método para regresar al activity anterior
    public void regresar(View v){
        super.onBackPressed();
    }

    //Limpiar campos
    public void limpiarTexto(View v) {
        editNombreCiclo.setText("");
        editInicioCiclo.setText("");
        editFinCiclo.setText("");
        editInicioClases.setText("");
        editFinClases.setText("");
    }
}

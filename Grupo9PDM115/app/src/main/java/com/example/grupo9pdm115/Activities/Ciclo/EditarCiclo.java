package com.example.grupo9pdm115.Activities.Ciclo;


import android.app.Activity;
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

public class EditarCiclo extends AppCompatActivity implements View.OnClickListener {
    //Declarando
    Ciclo ciclo;
    EditText editNombreCiclo, editInicioCiclo, editFinCiclo, editInicioClases, editFinClases;
    Button btnInicioCiclo, btnFinCiclo, btnInicioClases, btnFinClases;
    private int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ciclo);
        ciclo = new Ciclo();

        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        editInicioCiclo = (EditText) findViewById(R.id.editInicioCiclo);
        editFinCiclo = (EditText) findViewById(R.id.editFinCiclo);
        editInicioClases = (EditText) findViewById(R.id.editInicioClases);
        editFinClases = (EditText) findViewById(R.id.editFinClases);
        btnInicioCiclo = (Button) findViewById(R.id.btnInicioCiclo);
        btnFinCiclo = (Button) findViewById(R.id.btnFinCiclo);
        btnInicioClases = (Button) findViewById(R.id.btnInicioClases);
        btnFinClases = (Button) findViewById(R.id.btnFinClases);

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            ciclo.setIdCiclo(getIntent().getIntExtra("idciclo", 0));
            editNombreCiclo.setText(getIntent().getStringExtra("nombreciclo"));
            editInicioCiclo.setText(getIntent().getStringExtra("iniciociclo"));
            editFinCiclo.setText(getIntent().getStringExtra("finciclo"));
            ciclo.setEstadoCiclo(getIntent().getStringExtra("estadociclo"));
            editInicioClases.setText(getIntent().getStringExtra("inicioclases"));
            editFinClases.setText(getIntent().getStringExtra("finclases"));
        }

        btnInicioCiclo.setOnClickListener(this);
        btnFinCiclo.setOnClickListener(this);
        btnInicioClases.setOnClickListener(this);
        btnFinClases.setOnClickListener(this);
    }

    // Método para actualizar ciclo
    public void actualizarCiclo(View v) {
        ciclo.setNombreCiclo(editNombreCiclo.getText().toString());
        ciclo.setInicio(editInicioCiclo.getText().toString());
        ciclo.setFin(editFinCiclo.getText().toString());
        ciclo.setInicioPeriodoClase(editInicioClases.getText().toString());
        ciclo.setFinPeriodoClase(editFinClases.getText().toString());
        String estado = ciclo.actualizar(this);
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
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


    // Método para regresar al activity anterior
    public void regresar(View v) {
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


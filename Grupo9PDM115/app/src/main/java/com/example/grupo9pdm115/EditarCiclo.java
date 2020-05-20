package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Ciclo;

import java.util.Calendar;

public class EditarCiclo extends Activity implements View.OnClickListener {
    //Declarando
    EditText editNombreCiclo, editFinCiclo;
    Button btnFinCiclo;
    Ciclo ciclo;
    private int diafc, mesfc, anofc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ciclo);

        ciclo = new Ciclo();
        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        editFinCiclo = (EditText) findViewById(R.id.editFinCiclo);
        btnFinCiclo = (Button) findViewById(R.id.btnFinCiclo);

        btnFinCiclo.setOnClickListener(this);

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            editNombreCiclo.setText(getIntent().getStringExtra("nombreciclo"));
            editFinCiclo.setText(getIntent().getStringExtra("finciclo"));
            ciclo.setIdCiclo(getIntent().getIntExtra("idciclo", 0));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnFinCiclo) {

            final Calendar c = Calendar.getInstance();
            diafc = c.get(Calendar.DAY_OF_MONTH);
            mesfc = c.get(Calendar.MONTH);
            anofc = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    editFinCiclo.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }
            }, anofc, mesfc, diafc);
            datePickerDialog.show();
        }
    }

    // Método para actualizar día
    public void btnEditarECiclo(View v) {
        String nombreCiclo = editNombreCiclo.getText().toString();
        ciclo.setNombreCiclo(nombreCiclo);
        String finCiclo = editFinCiclo.getText().toString();
        ciclo.setFin(finCiclo);
        String estado = ciclo.actualizar(this);
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    // Método para regresar al activity anterior
    public void btnRegresarECiclo(View v) {
        super.onBackPressed();
    }

    //Limpiar campos
    public void btnLimpiarTextoECiclo(View v) {
        editNombreCiclo.setText("");
        editFinCiclo.setText("");
    }
}


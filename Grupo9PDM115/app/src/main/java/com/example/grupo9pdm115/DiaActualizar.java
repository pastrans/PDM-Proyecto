package com.example.grupo9pdm115;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Dia;

public class DiaActualizar extends Activity {
    // Declarando
    EditText editNombreDia;
    Dia dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_actualizar);

        dia = new Dia();
        editNombreDia = (EditText) findViewById(R.id.editNombreDia);

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            editNombreDia.setText(getIntent().getStringExtra("nombredia"));
            dia.setIdDia(getIntent().getIntExtra("iddia", 0));
        }
    }

    // Método para actualizar día
    public void actualizarDia(View v) {
        String nombreDia = editNombreDia.getText().toString();
        dia.setNombreDia(nombreDia);
        String estado = dia.actualizar(this);
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    // Método para regresar al activity anterior
    public void regresar(View v) {
        super.onBackPressed();
    }
}

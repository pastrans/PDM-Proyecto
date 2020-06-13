package com.example.grupo9pdm115.Activities.Dia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.R;

public class EditarDia extends AppCompatActivity {
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
        String estado;
        String nombreDia = editNombreDia.getText().toString();
        dia.setNombreDia(nombreDia);
        if(dia.getNombreDia().isEmpty()){
            estado="Nombre está vacío";
        }
        else{
            estado = dia.actualizar(this);
        }
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    //Limpiar campos
    public void btnLimpiarTextoEDia(View v) {
        editNombreDia.setText("");
    }
}

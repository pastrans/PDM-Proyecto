package com.example.grupo9pdm115;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Dia;

public class DiaInsertar extends Activity {
    // Declarando
    EditText editNombreDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_insertar);

        editNombreDia = (EditText) findViewById(R.id.editNombreDia);
    }

    // Método para insertar día
    public void insertarDia(View v){
        // Obteniendo valores elementos
        String nombreDia = editNombreDia.getText().toString();

        // Instanciando dia para guardar
        Dia dia = new Dia();
        dia.setNombreDia(nombreDia);
        String regInsertados = dia.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    // Método para regresar al activity anterior
    public void regresar(View v){
        super.onBackPressed();
    }
}

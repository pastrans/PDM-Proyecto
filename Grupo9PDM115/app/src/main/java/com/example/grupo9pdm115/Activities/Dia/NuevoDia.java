package com.example.grupo9pdm115.Activities.Dia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.R;

public class NuevoDia extends CyaneaAppCompatActivity {
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
        String regInsertados;
        String nombreDia = editNombreDia.getText().toString();

        // Instanciando dia para guardar
        Dia dia = new Dia();
        dia.setNombreDia(nombreDia);
        if(dia.getNombreDia().isEmpty()){
            regInsertados = "Nombre está vacío";
        }
        else{
            regInsertados = dia.guardar(this);
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    //Limpiar campos
    public void btnLimpiarTextoNDía(View v) {
        editNombreDia.setText("");
    }
}

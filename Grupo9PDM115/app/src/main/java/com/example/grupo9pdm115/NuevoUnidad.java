package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Unidad;

public class NuevoUnidad extends AppCompatActivity {

    ControlBD helper;
    EditText nombreUnidad;
    EditText descripcionUnidad;
    EditText prioridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_unidad);
        helper = new ControlBD(this);
        nombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        descripcionUnidad = (EditText) findViewById(R.id.txtdescripcionEdit);
        prioridad = (EditText) findViewById(R.id.editPrioridad);
    }

    public void agregarUnidad(View v){
        String reginsertados;
        Unidad unidad = new Unidad();
        unidad.setNombreent(nombreUnidad.getText().toString());
        unidad.setDescripcionent(descripcionUnidad.getText().toString());
        unidad.setPrioridad(Integer.parseInt(prioridad.getText().toString()));
        helper.abrir();
        reginsertados = helper.insertar(unidad.getNombreTabla(), unidad.getValores());
        helper.cerrar();
        Toast.makeText(this, reginsertados, Toast.LENGTH_SHORT).show();
    }

}

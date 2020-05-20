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

    //ControlBD helper;
    EditText nombreUnidad;
    EditText descripcionUnidad;
    EditText prioridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_unidad);
        //helper = new ControlBD(this);
        nombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        descripcionUnidad = (EditText) findViewById(R.id.editDescripcion);
        prioridad = (EditText) findViewById(R.id.editPrioridad);
    }

    public void agregarUnidad(View v){
        String reginsertados;
        String nombreU = nombreUnidad.getText().toString();
        String descripcion = descripcionUnidad.getText().toString();
        Integer priori = Integer.parseInt(prioridad.getText().toString());

        Unidad unidad = new Unidad();
        unidad.setNombreent(nombreU);
        unidad.setDescripcionent(descripcion);
        unidad.setPrioridad(priori);
        String regInsertados = unidad.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        //helper.abrir();
        //reginsertados = helper.insertar(unidad.getNombreTabla(), unidad.getValores());
        //helper.cerrar();
        //Toast.makeText(this, reginsertados, Toast.LENGTH_SHORT).show();
    }
    // Método para regresar al activity anterior
    public void regresar(View v){
        super.onBackPressed();
    }

}

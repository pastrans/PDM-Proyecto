package com.example.grupo9pdm115.Activities.Unidad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

public class NuevoUnidad extends AppCompatActivity {
    EditText nombreUnidad;
    EditText descripcionUnidad;
    EditText prioridad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_unidad);
        nombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        descripcionUnidad = (EditText) findViewById(R.id.editDescripcion);
        prioridad = (EditText) findViewById(R.id.editPrioridad);
    }

    public void agregarUnidad(View v){
        String regInsertados;
        String nombreU = nombreUnidad.getText().toString();
        String descripcion = descripcionUnidad.getText().toString();
        int priori = Integer.parseInt(prioridad.getText().toString());
        Unidad unidad = new Unidad();
        unidad.setNombreent(nombreU);
        unidad.setDescripcionent(descripcion);
        unidad.setPrioridad(priori);
        if(unidad.getNombreent().isEmpty()){
            regInsertados= "El nombre esta vacio";
        }else{
            if(unidad.getDescripcionent().isEmpty()){
                regInsertados= "La descripción esta vacia";
            }else{
                if(unidad.getPrioridad() == 0 ){
                    regInsertados= "Ingrese prioridad mayor a cero";
                }else{
                    regInsertados = unidad.guardar(this);
                }
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void btnLimpiarTextoNUnidad(View v){
        nombreUnidad.setText("");
        descripcionUnidad.setText("");
        prioridad.setText("");

    }


}

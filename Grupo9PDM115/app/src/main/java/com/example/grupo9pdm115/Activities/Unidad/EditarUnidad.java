package com.example.grupo9pdm115.Activities.Unidad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

public class EditarUnidad extends AppCompatActivity {
    EditText nombreent;
    EditText descripcion;
    EditText prioridad;
    Unidad unidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_unidad);
        unidad = new Unidad();
        nombreent = (EditText) findViewById(R.id.editNombreUnidad);
        descripcion = (EditText) findViewById(R.id.editDescripcion);
        prioridad = (EditText) findViewById(R.id.editPrioridad);

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            unidad.setIdUnidad(getIntent().getIntExtra("idunidad", 0));
            nombreent.setText(getIntent().getStringExtra("nombreent"));
            descripcion.setText(getIntent().getStringExtra("descripcionent"));
            prioridad.setText(Integer.toString(getIntent().getIntExtra("prioridad",0)));
        }
    }
    // Método para actualizar
    public void actualizarU(View v) {
        String estado;
        String nombreuni = nombreent.getText().toString();
        unidad.setNombreent(nombreuni);
        String desc  = descripcion.getText().toString();
        unidad.setDescripcionent(desc);
        int prio = Integer.parseInt(prioridad.getText().toString().trim());
        unidad.setPrioridad(prio);
        if(unidad.getNombreent().isEmpty()){
            estado= "El nombre esta vacio";
        }else{
            if(unidad.getDescripcionent().isEmpty()){
                estado = "La descripción esta vacia";
            }else{
                if(unidad.getPrioridad() == 0 ){
                    estado= "Ingrese prioridad mayor a cero";
                }else{
                    estado = unidad.actualizar(this);
                }
            }
        }
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }
    public void btnLimpiarTextoNUnidad(View c){
        nombreent.setText("");
        descripcion.setText("");
        prioridad.setText("");
    }

}

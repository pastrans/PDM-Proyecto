package com.example.grupo9pdm115.Activities.Unidad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

public class EditarUnidad extends Activity {
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
    // Método para actualizar día
    public void actualizar(View v) {
        String nombreuni = nombreent.getText().toString();
        unidad.setNombreent(nombreuni);
        String desc  = descripcion.getText().toString();
        unidad.setDescripcionent(desc);
        int prio = Integer.parseInt(prioridad.getText().toString().trim());
        unidad.setPrioridad(prio);
        String estado = unidad.actualizar(this);
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    // Método para regresar al activity anterior
    public void regresar(View v) {
        super.onBackPressed();
    }
}

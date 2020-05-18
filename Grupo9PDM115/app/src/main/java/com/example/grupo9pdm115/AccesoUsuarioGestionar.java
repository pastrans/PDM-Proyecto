package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccesoUsuarioGestionar extends AppCompatActivity {

    Button nuevoAcceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_acceso_usuario);
        nuevoAcceso = (Button) findViewById(R.id.btnNuevoAcceso);
    }

    public void nuevoAcceso(View v){
        Intent inte = new Intent(this, AccesoUsuarioNuevo.class);
        startActivity(inte);
    }

}

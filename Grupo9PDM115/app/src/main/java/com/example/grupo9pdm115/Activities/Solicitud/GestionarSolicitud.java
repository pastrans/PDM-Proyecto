package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grupo9pdm115.R;

public class GestionarSolicitud extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_solicitud);
    }
    public void btnNuevoGSolicitud(View v){
        Intent inte = new Intent(this, NuevoSolicitud.class);
        startActivity(inte);
    }
}

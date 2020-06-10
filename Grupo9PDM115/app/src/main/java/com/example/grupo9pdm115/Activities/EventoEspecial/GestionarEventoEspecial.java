package com.example.grupo9pdm115.Activities.EventoEspecial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grupo9pdm115.R;

public class GestionarEventoEspecial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_evento_especial);
    }
    public void btnNuevoEventoEspecial(View v){
        Intent inte = new Intent(this, NuevoEventoEspecial.class);
        startActivity(inte);
    }
}

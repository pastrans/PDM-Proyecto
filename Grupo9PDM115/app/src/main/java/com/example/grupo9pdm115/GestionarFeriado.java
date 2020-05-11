package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GestionarFeriado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_feriado);
    }

    public void nuvoFeriadoActivity(View v){
        Intent inte = new Intent(this, NuevoFeriado.class);
        startActivity(inte);
    }

}

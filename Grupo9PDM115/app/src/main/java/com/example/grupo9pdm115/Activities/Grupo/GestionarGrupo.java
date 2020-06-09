package com.example.grupo9pdm115.Activities.Grupo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grupo9pdm115.R;

public class GestionarGrupo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_grupo);
    }
    public void btnNuevoGrupo(View v){
        Intent inte = new Intent(this, NuevoGrupo.class);
        startActivity(inte);
    }
}

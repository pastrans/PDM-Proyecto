package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

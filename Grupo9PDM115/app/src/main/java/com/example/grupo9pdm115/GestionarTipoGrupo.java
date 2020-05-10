package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GestionarTipoGrupo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tipo_grupo);
    }

    public void nuevoTipoGrupo(View v){
        Intent inte = new Intent(this, NuevoTipoGrupo.class);
        startActivity(inte);
    }
}

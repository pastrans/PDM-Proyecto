package com.example.grupo9pdm115.Activities.TipoLocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grupo9pdm115.R;

public class GestionarTipoLocal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tipo_local);
    }

    public void agregarTipoLocal (View v){
        Intent inte = new Intent(this, NuevoTipoLocal.class);
        startActivity(inte);
    }

}

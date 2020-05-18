package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GestionarMateria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_materia);
    }

    public void btnNuevoGMateria(View view){
        Intent inte = new Intent(this, GestionarUsuario.class);
        startActivity(inte);
    }

}

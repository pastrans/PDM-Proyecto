package com.example.grupo9pdm115.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

public class ErrorDeUsuario extends CyaneaAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_de_usuario);
    }

    public void irALogin(View v){
        // Código para cerrar sesión
        Sesion.setLooggedIn(this, false);
        Sesion.setNombreUsuario(this, "");
        Sesion.setAccesoUsuario(this, null);
        Sesion.setIdUsuario(this, null);
        Intent intent = new Intent(this, IniciarSesion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
        startActivity(intent);
        finish();
    }
}

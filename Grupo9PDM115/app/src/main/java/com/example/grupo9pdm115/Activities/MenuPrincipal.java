package com.example.grupo9pdm115.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.grupo9pdm115.R;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    LinearLayout layoutUsuario, layoutOpcionesUsuario;
    boolean opcionesUsuario;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        layoutUsuario = (LinearLayout) findViewById(R.id.layoutUsuario);
        layoutOpcionesUsuario = (LinearLayout) findViewById(R.id.layoutOpcionesUsuario);
        opcionesUsuario = false;

        layoutUsuario.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(!opcionesUsuario){
            opcionesUsuario = true;
            layoutOpcionesUsuario.setVisibility(View.VISIBLE);
        }
        else{
            opcionesUsuario = false;
            layoutOpcionesUsuario.setVisibility(View.GONE);
        }
    }
}

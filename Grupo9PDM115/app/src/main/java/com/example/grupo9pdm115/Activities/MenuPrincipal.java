package com.example.grupo9pdm115.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.grupo9pdm115.R;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    public final int[] idGruposOpciones = {R.id.layoutOpcionesUsuario, R.id.layoutOpcionesControlAcademico,
            R.id.layoutOpcionesControlLocales};

    LinearLayout layoutControlUsuario, layoutOpcionesUsuario, layoutControlAcademico;

    boolean opcionesUsuario;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        layoutControlUsuario = (LinearLayout) findViewById(R.id.layoutControlUsuario);
        layoutControlAcademico = (LinearLayout) findViewById(R.id.layoutControlAcademico);
        layoutOpcionesUsuario = (LinearLayout) findViewById(R.id.layoutOpcionesUsuario);

        layoutControlUsuario.setOnClickListener(this);
        layoutControlAcademico.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.layoutControlUsuario:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesUsuario);
                break;
            case R.id.layoutControlAcademico:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesControlAcademico);
                break;
            case R.id.layoutControlLocales:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesControlLocales);
                break;
        }

        /*
        if(v == layoutUsuario)
            cambiarVisibilidad(layoutOpcionesUsuario);
            layoutOpcionesUsuario.setVisibility((layoutOpcionesUsuario.isShown() ? View.GONE : View.VISIBLE));

         */


        /*
        if(!opcionesUsuario){
            opcionesUsuario = true;
            layoutOpcionesUsuario.setVisibility(View.VISIBLE);
        }
        else{
            opcionesUsuario = false;
            layoutOpcionesUsuario.setVisibility(View.GONE);
        }
         */
    }

    public void cambiarVisibilidadView(int idView){
        View v = (View) findViewById(idView);
        v.setVisibility(v.isShown() ? View.GONE : View.VISIBLE);
    }

    // Método que muestra u oculta un grupo de opciones y oculta el resto, si estaban visibles
    public void cambiarVisibilidadGrupoDeOpciones(int idView){
        for(int id : idGruposOpciones){
            // Cambiar la visibilidad del grupo seleccionado
            if(id == idView)
                cambiarVisibilidadView(idView);
            // Modificar visibilidad del resto de grupos a GONE
            else
                findViewById(id).setVisibility(View.GONE);
        }
    }
}

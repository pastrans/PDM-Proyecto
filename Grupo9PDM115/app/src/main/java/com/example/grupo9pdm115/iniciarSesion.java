package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;

import java.util.Set;

public class IniciarSesion extends AppCompatActivity {

    EditText nombreUsuario, claveUsuario;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        helper = new ControlBD(this);
        nombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        claveUsuario = (EditText) findViewById(R.id.txtClaveUsuario);
    }

    public void ingresar(View v){
        Sesion sesion = new Sesion();
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario.getText().toString());
        usuario.setClaveUsuario(claveUsuario.getText().toString());
        if (sesion.iniciarSesion(usuario, this)){
            Sesion.setLooggedIn(getApplicationContext(), true);
            Sesion.setNombreUsuario(getApplicationContext(), usuario.getNombreUsuario());
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
        }
        else
            Toast.makeText(this, "El usuario no existe o la contraseña es incorrecta", Toast.LENGTH_SHORT).show();
    }

}

package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.AccesoUsuario;
import com.example.grupo9pdm115.Modelos.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AccesoUsuarioNuevo extends AppCompatActivity {

    List<AccesoUsuario> listaPermisos;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_acceso_usuario);
        listaPermisos = new ArrayList<AccesoUsuario>();
        usuario = new Usuario();
    }

    public void agregarLista(View v){
        AccesoUsuario ac = new AccesoUsuario();

    }

}

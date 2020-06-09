package com.example.grupo9pdm115.Activities.Encargado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.EncargadoUsuarioSpinner;

public class NuevoEncargado extends AppCompatActivity {
    ControlBD helper;
    Spinner spinnerUsuario;
    EncargadoUsuarioSpinner spinnerEncargadoUsuarioAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_encargado);
        helper = new ControlBD(this);
        spinnerUsuario = (Spinner) findViewById(R.id.UsuarioSpinner);
        helper.abrir();
        spinnerEncargadoUsuarioAdpter = new EncargadoUsuarioSpinner(helper);
        helper.cerrar();
        spinnerUsuario.setAdapter(spinnerEncargadoUsuarioAdpter.getAdapterUsuario(getApplicationContext()));
        spinnerEncargadoUsuarioAdpter.getAdapterUsuario(getApplicationContext()).notifyDataSetChanged();
    }
    public void btnAgregarNEncargado(View v){
        String regInsertados;
        Encargado encargado = new Encargado();
        int posicionUsuario = 0, idUsuario = 0;
        posicionUsuario = spinnerUsuario.getSelectedItemPosition();
        //usuario.setNombreUsuario(nombreUsuario.getText().toString());
        //usuario.setIdUsuario(String.valueOf(usuario.countUsuario(this, usuario)) + nombreUsuario.getText().toString().toUpperCase().charAt(0));
        if (posicionUsuario != 0)
            encargado.setIdUsuario(Integer.toString(spinnerEncargadoUsuarioAdpter.getIdUsuario(posicionUsuario)));
        regInsertados = encargado.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    public void btnLimpiarNUsuario(View v){
        spinnerUsuario.setSelection(0);
    }
}

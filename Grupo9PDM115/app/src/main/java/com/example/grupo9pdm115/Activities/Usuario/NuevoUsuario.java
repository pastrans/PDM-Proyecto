package com.example.grupo9pdm115.Activities.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.UsuarioUnidadSpinner;

public class NuevoUsuario extends AppCompatActivity {

    ControlBD helper;
    EditText nombreUsuario, claveUsuario, nombrePersonal, apellidoPersonal, correoPersonal;
    Spinner spinnerUnidad;
    UsuarioUnidadSpinner spinnerUsuarioUnidadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        if(!Sesion.getAccesoUsuario(getApplicationContext(), "IUS")){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
            finish();
        }

        helper = new ControlBD(this);
        nombreUsuario = (EditText) findViewById(R.id.editNombreUsuario);
        claveUsuario = (EditText) findViewById(R.id.editClaveUsuario);
        nombrePersonal = (EditText) findViewById(R.id.editNombrePersona);
        apellidoPersonal = (EditText) findViewById(R.id.editApellidoPersona);
        correoPersonal = (EditText) findViewById(R.id.editCorreoPersona);
        spinnerUnidad = (Spinner) findViewById(R.id.spinnerUnidadNuevoUsuario);
        helper.abrir();
        spinnerUsuarioUnidadAdapter = new UsuarioUnidadSpinner(helper);
        helper.cerrar();
        spinnerUnidad.setAdapter(spinnerUsuarioUnidadAdapter.getAdapterUnidad(getApplicationContext()));
        spinnerUsuarioUnidadAdapter.getAdapterUnidad(getApplicationContext()).notifyDataSetChanged();
    }

    public void btnAgregarNUsuario(View v){
        String regInsertados;
        Usuario usuario = new Usuario();
        int posicionUnidad = 0, idUnidad = 0;
        posicionUnidad = spinnerUnidad.getSelectedItemPosition();
        usuario.setNombreUsuario(nombreUsuario.getText().toString());
        usuario.setNombrePersonal(nombrePersonal.getText().toString());
        usuario.setClaveUsuario(claveUsuario.getText().toString());
        usuario.setApellidoPersonal(apellidoPersonal.getText().toString());
        usuario.setCorreoPersonal(correoPersonal.getText().toString());
        usuario.setIdUsuario(String.valueOf(usuario.countUsuario(this, usuario)) + nombreUsuario.getText().toString().toUpperCase().charAt(0));
        if (posicionUnidad != 0)
            usuario.setIdUnidad(spinnerUsuarioUnidadAdapter.getIdUnidad(posicionUnidad));
        regInsertados = usuario.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void btnLimpiarNUsuario(View v){
        nombreUsuario.setText("");
        nombrePersonal.setText("");
        apellidoPersonal.setText("");
        correoPersonal.setText("");
        spinnerUnidad.setSelection(0);
        claveUsuario.setText("");
    }

}
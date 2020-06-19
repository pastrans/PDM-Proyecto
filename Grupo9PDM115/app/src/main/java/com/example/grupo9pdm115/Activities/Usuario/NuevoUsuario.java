package com.example.grupo9pdm115.Activities.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.RolSpinner;
import com.example.grupo9pdm115.Spinners.UsuarioUnidadSpinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuevoUsuario extends AppCompatActivity {

    EditText nombreUsuario, claveUsuario, nombrePersonal, apellidoPersonal, correoPersonal;
    Spinner spinnerUnidad, spinnerRol;
    UsuarioUnidadSpinner spinnerUsuarioUnidadAdapter;
    RolSpinner spinnerRolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "IUS"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        if(!Sesion.getAccesoUsuario(getApplicationContext(), "IUS")){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
            finish();
        }

        nombreUsuario = (EditText) findViewById(R.id.editNombreUsuario);
        claveUsuario = (EditText) findViewById(R.id.editClaveUsuario);
        nombrePersonal = (EditText) findViewById(R.id.editNombrePersona);
        apellidoPersonal = (EditText) findViewById(R.id.editApellidoPersona);
        correoPersonal = (EditText) findViewById(R.id.editCorreoPersona);

        // Spinners
        spinnerUnidad = (Spinner) findViewById(R.id.spinnerUnidadNuevoUsuario);
        spinnerRol = (Spinner) findViewById(R.id.spinnerRolNuevoUsuario);
        // Instanciar adapters
        spinnerUsuarioUnidadAdapter = new UsuarioUnidadSpinner(this);
        spinnerRolAdapter = new RolSpinner(this);
        // Setear adapters
        spinnerUnidad.setAdapter(spinnerUsuarioUnidadAdapter.getAdapterUnidad(this));
        spinnerRol.setAdapter(spinnerRolAdapter.getAdapterRol(this));
        //spinnerUsuarioUnidadAdapter.getAdapterUnidad(this);
        //spinnerUnidad.setSelection(1);
    }

    public void btnAgregarNUsuario(View v){
        String regInsertados;
        Usuario usuario = new Usuario();
        int posicionUnidad = 0, idUnidad = 0, posicionRol;
        posicionUnidad = spinnerUnidad.getSelectedItemPosition();
        posicionRol = spinnerRol.getSelectedItemPosition();
        usuario.setNombreUsuario(nombreUsuario.getText().toString());
        usuario.setNombrePersonal(nombrePersonal.getText().toString());
        usuario.setClaveUsuario(claveUsuario.getText().toString());
        usuario.setApellidoPersonal(apellidoPersonal.getText().toString());
        usuario.setCorreoPersonal(correoPersonal.getText().toString());
        if(!ValidarCorreo(correoPersonal.getText().toString())){
            Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (posicionUnidad != 0)
            usuario.setIdUnidad(spinnerUsuarioUnidadAdapter.getIdUnidad(posicionUnidad));
        if(posicionRol != 0)
            usuario.setIdRol(spinnerRolAdapter.getIdRol(posicionRol));
        String verifcar = verificarDatos(usuario);
        if (!verifcar.equals("")){
            Toast.makeText(this, verifcar, Toast.LENGTH_SHORT).show();
            return;
        }
        String idUsuario = "";
        int numUsuario = 0;
        numUsuario = usuario.countUsuario(this, usuario);
        if(numUsuario < 10)
            idUsuario = usuario.getNombrePersonal().toUpperCase().substring(0, 1) + usuario.getApellidoPersonal().toUpperCase().substring(0, 1) + String.format("%03d", numUsuario + 1);
        else if(numUsuario > 10 && numUsuario < 100)
            idUsuario = usuario.getNombrePersonal().toUpperCase().substring(0, 1) + usuario.getApellidoPersonal().toUpperCase().substring(0, 1) + String.format("%02d", numUsuario + 1);
        else if (numUsuario > 100)
            idUsuario = usuario.getNombrePersonal().toUpperCase().substring(0, 1) + usuario.getApellidoPersonal().toUpperCase().substring(0, 1) + String.valueOf(numUsuario + 1);
        usuario.setIdUsuario(idUsuario);
        //Toast.makeText(this, String.valueOf(usuario.countUsuario(this, usuario)), Toast.LENGTH_SHORT).show();
        regInsertados = usuario.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        limpiar();
    }

    private void limpiar() {
        nombreUsuario.setText("");
        nombrePersonal.setText("");
        apellidoPersonal.setText("");
        correoPersonal.setText("");
        spinnerUnidad.setSelection(0);
        spinnerRol.setSelection(0);
        claveUsuario.setText("");
    }

    public void btnLimpiarNUsuario(View v){
        limpiar();
    }

    private boolean ValidarCorreo(String email){
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar

        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            //System.out.println("El email ingresado es válido.");
            return Boolean.TRUE;
        } else {
           // System.out.println("El email ingresado es inválido.");
            return Boolean.FALSE;
        }

    }
    public void btnRegresarNUsuario(View v){
        finish();
    }

    public String verificarDatos(Usuario usuario){
        if (usuario.getNombreUsuario().equals(""))
            return "Se requiere de un nombre de usuario";
        if (usuario.verificar(1, getApplicationContext()))
            return "Ya existe el nombre de usuario";
        if (usuario.getClaveUsuario().equals(""))
            return "Se requiere una clave para el usuario";
        if(usuario.getClaveUsuario().length() < 4)
            return "La contraseña debe tener mínimo 5 caractéres";
        if(usuario.getNombrePersonal().equals(""))
            return "Se requiere del nombre de la persona";
        if(usuario.getApellidoPersonal().equals(""))
            return "Se requiere del apellido de la persona";
        if(usuario.getCorreoPersonal().equals(""))
            return "Se requiere de un correo para el usuario";
        if(usuario.verificar(2, getApplicationContext()))
            return "El correo ya está registrado";
        if(usuario.getIdUnidad() == 0)
            return "Seleccione una unidad al usuario";
        if(usuario.getIdRol() == 0)
            return "Seleccione un rol al usuario";
        return "";
    }

}

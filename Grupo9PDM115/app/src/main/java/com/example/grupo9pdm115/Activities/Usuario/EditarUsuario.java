package com.example.grupo9pdm115.Activities.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.RolSpinner;
import com.example.grupo9pdm115.Spinners.UsuarioUnidadSpinner;

public class EditarUsuario extends AppCompatActivity {

    Usuario usuario;
    UsuarioUnidadSpinner usuarioUnidadSpinnerAdapter;
    RolSpinner rolSpinnerAdapter;
    EditText editNombreUsuario, editNombrePersona, editApellidoPersona, editCorreoPersona, editClaveUsuario;
    Spinner spinnerEditarUnidadUsuario, spinnerEditarRolUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "EUS"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        usuario = new Usuario();
        editNombreUsuario = (EditText) findViewById(R.id.editNombreUsuario);
        editNombrePersona = (EditText) findViewById(R.id.editNombrePersona);
        editApellidoPersona = (EditText) findViewById(R.id.editApellidoPersona);
        editCorreoPersona = (EditText) findViewById(R.id.editCorreoPersona);
        editClaveUsuario = (EditText) findViewById(R.id.editClaveUsuario);

        // Spinners
        spinnerEditarUnidadUsuario = (Spinner) findViewById(R.id.spinnerEditarUnidadUsuario);
        spinnerEditarRolUsuario = (Spinner) findViewById(R.id.spinnerEditarRolUsuario);
        // Instanciando adapters
        usuarioUnidadSpinnerAdapter = new UsuarioUnidadSpinner(this);
        rolSpinnerAdapter = new RolSpinner(this);
        // Seteando adapters
        spinnerEditarUnidadUsuario.setAdapter(usuarioUnidadSpinnerAdapter.getAdapterUnidad(this));
        spinnerEditarRolUsuario.setAdapter(rolSpinnerAdapter.getAdapterRol(this));

        if (getIntent().getExtras() != null){
            editNombreUsuario.setText(getIntent().getStringExtra("nombreUsuario"));
            editNombrePersona.setText(getIntent().getStringExtra("nombrePersona"));
            editApellidoPersona.setText(getIntent().getStringExtra("apellidoPersona"));
            editCorreoPersona.setText(getIntent().getStringExtra("correoPersona"));
            usuario.setIdUsuario(getIntent().getStringExtra("idUsuario"));
            usuario.setClaveUsuario(getIntent().getStringExtra("claveUsuario"));
            int idUnidad = getIntent().getIntExtra("unidad", 0);
            int idRol = getIntent().getIntExtra("rol", 0);
            for (int i = 1; i < spinnerEditarUnidadUsuario.getAdapter().getCount(); i++){
                int idUnidadItem = usuarioUnidadSpinnerAdapter.getIdUnidad(i);
                if (idUnidadItem == idUnidad)
                    spinnerEditarUnidadUsuario.setSelection(i);
            }
            for (int i = 1; i < spinnerEditarRolUsuario.getAdapter().getCount(); i++){
                int idRolItem = rolSpinnerAdapter.getIdRol(i);
                if (idRolItem == idRol)
                    spinnerEditarRolUsuario.setSelection(i);
            }
        }

        spinnerEditarUnidadUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), String.valueOf(spinnerEditarUnidadUsuario.getSelectedItemId()), Toast.LENGTH_SHORT).show();
                //Toast.makeText(parent.getContext(), String.valueOf(usuarioUnidadSpinnerAdapter.getUnidad(position).getNombreent()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void btnEditarEUsuario(View v){
        String resultado;
        int pos = spinnerEditarUnidadUsuario.getSelectedItemPosition();
        int posRol = spinnerEditarRolUsuario.getSelectedItemPosition();
        usuario.setNombreUsuario(editNombreUsuario.getText().toString());
        usuario.setNombrePersonal(editNombrePersona.getText().toString());
        usuario.setApellidoPersonal(editApellidoPersona.getText().toString());
        usuario.setCorreoPersonal(editCorreoPersona.getText().toString());
        usuario.setIdUnidad(usuarioUnidadSpinnerAdapter.getIdUnidad(pos));
        usuario.setIdRol(rolSpinnerAdapter.getIdRol(posRol));
        String verifcar = verificarDatos(usuario);
        if (!verifcar.equals("")){
            Toast.makeText(this, verifcar, Toast.LENGTH_SHORT).show();
            return;
        }
        resultado = usuario.actualizar(this);
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void btnRegresarEUsuario(View v){
        finish();
    }

    public void btnLimpiarEUsuario(View v){
        editNombreUsuario.setText("");
        editNombrePersona.setText("");
        editApellidoPersona.setText("");
        editCorreoPersona.setText("");
        spinnerEditarUnidadUsuario.setSelection(0);
        spinnerEditarRolUsuario.setSelection(0);
        editClaveUsuario.setText("");
    }

    public String verificarDatos(Usuario usuario){
        if (usuario.getNombreUsuario().equals(""))
            return "Se requiere de un nombre de usuario";
        if (usuario.verificar(1, getApplicationContext()) && !usuario.getNombreUsuario().equals(getIntent().getStringExtra("nombreUsuario")))
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
        if(usuario.verificar(2, getApplicationContext()) && !usuario.getCorreoPersonal().equals(getIntent().getStringExtra("correoPersona")))
            return "El correo ya está registrado";
        if(usuario.getIdUnidad() == 0)
            return "Seleccione una unidad al usuario";
        if(usuario.getIdRol() == 0)
            return "Seleccione un rol al usuario";
        return "";
    }

}

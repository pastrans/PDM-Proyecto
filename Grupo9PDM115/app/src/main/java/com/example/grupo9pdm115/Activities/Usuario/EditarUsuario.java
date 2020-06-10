package com.example.grupo9pdm115.Activities.Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.RolSpinner;
import com.example.grupo9pdm115.Spinners.UsuarioUnidadSpinner;

public class EditarUsuario extends AppCompatActivity {

    ControlBD helper;
    Usuario usuario;
    UsuarioUnidadSpinner usuarioUnidadSpinnerAdapter;
    RolSpinner rolSpinnerAdapter;
    EditText editNombreUsuario, editNombrePersona, editApellidoPersona, editCorreoPersona, editClaveUsuario;
    Spinner spinnerEditarUnidadUsuario, spinnerEditarRolUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);
        usuario = new Usuario();
        editNombreUsuario = (EditText) findViewById(R.id.editNombreUsuario);
        editNombrePersona = (EditText) findViewById(R.id.editNombrePersona);
        editApellidoPersona = (EditText) findViewById(R.id.editApellidoPersona);
        editCorreoPersona = (EditText) findViewById(R.id.editCorreoPersona);
        editClaveUsuario = (EditText) findViewById(R.id.editClaveUsuario);
        spinnerEditarUnidadUsuario = (Spinner) findViewById(R.id.spinnerEditarUnidadUsuario);
        spinnerEditarRolUsuario = (Spinner) findViewById(R.id.spinnerEditarRolUsuario);
        helper = new ControlBD(this);
        helper.abrir();
        usuarioUnidadSpinnerAdapter = new UsuarioUnidadSpinner(helper);
        rolSpinnerAdapter = new RolSpinner(helper);
        helper.cerrar();
        spinnerEditarUnidadUsuario.setAdapter(usuarioUnidadSpinnerAdapter.getAdapterUnidad(getApplicationContext()));
        spinnerEditarRolUsuario.setAdapter(rolSpinnerAdapter.getAdapterRol(getApplicationContext()));

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
        resultado = usuario.actualizar(this);
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
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

}

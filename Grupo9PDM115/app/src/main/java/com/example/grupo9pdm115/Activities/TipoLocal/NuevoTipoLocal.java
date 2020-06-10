package com.example.grupo9pdm115.Activities.TipoLocal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.UsuarioAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.UsuarioSpinner;

import java.util.List;

public class NuevoTipoLocal extends AppCompatActivity {

    ControlBD helper;
    EditText edtNombreTipo;
    Spinner encargadoSpinner;
    TipoLocal tipoLocalClass;
    Encargado encargadoClass;
    UsuarioSpinner usuarioSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tipo_local);
        helper = new ControlBD(this);
        edtNombreTipo = (EditText) findViewById(R.id.editNombre);
        encargadoSpinner = (Spinner) findViewById(R.id.encargadoSpinner);
        tipoLocalClass = new TipoLocal();
        encargadoClass = new Encargado();
        helper.abrir();
        usuarioSpinnerAdapter = new UsuarioSpinner(helper);
        helper.cerrar();
        encargadoSpinner.setAdapter(usuarioSpinnerAdapter.getAdapterUsuario(getApplicationContext()));

    }

    public void btnAgregarNTipoLocal(View v){
        String regInsertados;
        TipoLocal tipoLocal = new TipoLocal();
        int posUsuario = encargadoSpinner.getSelectedItemPosition();
        tipoLocal.setNombreTipo(edtNombreTipo.getText().toString());
        tipoLocal.setIdEncargado(usuarioSpinnerAdapter.getIdUsuario(posUsuario));
        regInsertados = tipoLocal.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void btnLimpiarNTipoLocal(View v){
        edtNombreTipo.setText("");
        encargadoSpinner.setSelection(0);
    }

    public void btnRegresarNTipoLocal(View v){
        finish();
    }

}

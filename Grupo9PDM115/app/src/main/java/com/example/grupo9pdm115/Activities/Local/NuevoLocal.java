package com.example.grupo9pdm115.Activities.Local;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.TipoLocalAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.TipoLocalSpinner;

import java.util.List;

public class NuevoLocal extends AppCompatActivity {

    EditText nombreLocal;
    EditText capcidad;
    Spinner tipoLocalSpinner;
    ControlBD helper;
    TipoLocal tipoLocalClass;
    TipoLocalSpinner tipoLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_local);
        helper = new ControlBD(this);
        tipoLocalClass = new TipoLocal();
        tipoLocalSpinner = (Spinner) findViewById(R.id.tipoLocalSpinner);
        nombreLocal = (EditText) findViewById(R.id.editNombreLocal);
        capcidad = (EditText) findViewById(R.id.editCapacidad);
        helper.abrir();
        tipoLocalAdapter = new TipoLocalSpinner(helper);
        helper.cerrar();
        tipoLocalSpinner.setAdapter(tipoLocalAdapter.getAdapterTipoLocal(getApplicationContext()));
    }

    public void btnAgregarNLocal(View v){
        String regInsertados;
        Local local = new Local();
        int posTipoLocal = tipoLocalSpinner.getSelectedItemPosition();
        local.setNombreLocal(nombreLocal.getText().toString());
        local.setCapacidad(Integer.parseInt(capcidad.getText().toString()));
        local.setIdtipolocal(tipoLocalAdapter.getIdTipoLocal(posTipoLocal));
        helper.abrir();
        regInsertados = local.guardar(this);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    public void btnLimpiarNLocal(View v){
        nombreLocal.setText("");
        capcidad.setText("0");
        tipoLocalSpinner.setSelection(0);
    }

    public void btnRegresarNLocal(View v){
        finish();
    }

}

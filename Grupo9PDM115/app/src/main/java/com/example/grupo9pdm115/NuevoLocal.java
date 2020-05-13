package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.TipoLocal;

import java.util.List;

public class NuevoLocal extends AppCompatActivity {

    EditText nombreLocal;
    EditText capcidad;
    Spinner tipoLocalSpinner;
    ControlBD helper;
    TipoLocal tipoLocalClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_local);
        helper = new ControlBD(this);
        tipoLocalClass = new TipoLocal();
        tipoLocalSpinner = (Spinner) findViewById(R.id.tipoLocalSpinner);
        nombreLocal = (EditText) findViewById(R.id.editNombreLocal);
        capcidad = (EditText) findViewById(R.id.editCapacidad);
        llenarTipoLocalSpinner(this);

    }

    private void llenarTipoLocalSpinner(Context context){
        List<TipoLocal> listTipoLocal = tipoLocalClass.getTiposLocales(context);
        ArrayAdapter<TipoLocal> dataAdapter = new ArrayAdapter<TipoLocal>(this, android.R.layout.simple_spinner_item, listTipoLocal);
        tipoLocalSpinner.setAdapter(dataAdapter);
        tipoLocalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoLocal tipo = (TipoLocal) parent.getSelectedItem();
                Toast.makeText(parent.getContext(), "Seleccionado " + tipo.getNombreTipo() + " con ID: " + tipo.getIdTipoLocal(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void agregarLocal(View v){
        String regInsertados;
        Local local = new Local();
        TipoLocal tpLocal = (TipoLocal) tipoLocalSpinner.getSelectedItem();
        local.setNombreLocal(nombreLocal.getText().toString());
        local.setCapacidad(Integer.parseInt(capcidad.getText().toString()));
        local.setIdtipolocal(tpLocal.getIdTipoLocal());
        helper.abrir();
        regInsertados = helper.insertar(local.getNombreTabla(), local.getValores());
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

}

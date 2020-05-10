package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.Modelos.TipoLocal;

import java.util.List;

public class NuevoTipoLocal extends AppCompatActivity {

    ControlBD helper;
    EditText nombreTipo;
    Spinner encargadoSpinner;
    TipoLocal tipoLocalClass;
    Encargado encargadoClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tipo_local);
        helper = new ControlBD(this);
        nombreTipo = (EditText) findViewById(R.id.nombreTipoLocal);
        encargadoSpinner = (Spinner) findViewById(R.id.encargadoSpinner);
        tipoLocalClass = new TipoLocal();
        encargadoClass = new Encargado();
        llenarEncargados();
        Toast.makeText(this, "Datos de prueba cargados", Toast.LENGTH_SHORT).show();
    }

    public void agregarTipoLocal(View v){
        String regInsertados;
        TipoLocal tipoLocal = new TipoLocal();
        Encargado encargado = (Encargado) encargadoSpinner.getSelectedItem();
        tipoLocal.setNombreTipo(nombreTipo.getText().toString());
        tipoLocal.setIdEncargado(encargado.getIdEncargado());
        helper.abrir();
        regInsertados = helper.insertar(tipoLocal.getNombreTabla(), tipoLocal.getValores());
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    private void llenarEncargados(){
        List<Encargado> encargados = encargadoClass.getEncargados(this);
        ArrayAdapter<Encargado> dataAdapter = new ArrayAdapter<Encargado>(this, android.R.layout.simple_spinner_item, encargados);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        encargadoSpinner.setAdapter(dataAdapter);
        encargadoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Encargado encargado = (Encargado) parent.getSelectedItem();
                Toast.makeText(parent.getContext(), "Se ha seleccionado: " + encargado.getIdEncargado(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}

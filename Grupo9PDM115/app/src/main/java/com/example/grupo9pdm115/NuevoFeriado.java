package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.Feriado;

import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

public class NuevoFeriado extends AppCompatActivity {

    EditText nombrFeriado;
    EditText descripcionFeriado;
    EditText fechaInicioFeriado;
    EditText fechaFinFeriado;
    Spinner cicloFeriado;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_feriado);
        nombrFeriado = (EditText) findViewById(R.id.edtNombreFeriado);
        descripcionFeriado = (EditText) findViewById(R.id.edtDescripcionFeriado);
        fechaInicioFeriado = (EditText) findViewById(R.id.fechaInicioFeriado);
        fechaFinFeriado = (EditText) findViewById(R.id.fechaFinFeriado);
        cicloFeriado = (Spinner) findViewById(R.id.spinnerCicloFeriadoNuevo);
        helper = new ControlBD(this);
        llenarCicloSpinner(this);
    }

    public void llenarCicloSpinner(Context context){
        Feriado feriado = new Feriado();
        List<Ciclo> listaCiclos = feriado.getCiclos(context);
        ArrayAdapter<Ciclo> dataAdapter = new ArrayAdapter<Ciclo>(this, android.R.layout.simple_spinner_item, listaCiclos);
        cicloFeriado.setAdapter(dataAdapter);
        cicloFeriado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ciclo ciclo = (Ciclo) parent.getSelectedItem();
                Toast.makeText(parent.getContext(), "Seleccionado el :" + ciclo.getNombreCiclo(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void agregarFeriado(View v){
        Feriado feriado = new Feriado();
        Ciclo ciclo = (Ciclo) cicloFeriado.getSelectedItem();
        String regInsertados;
        feriado.setNombreFeriado(nombrFeriado.getText().toString());
        feriado.setDescripcionFeriado(descripcionFeriado.getText().toString());
        feriado.setFechaInicioFeriado(fechaInicioFeriado.getText().toString());
        feriado.setFecchaFinFeriado(fechaFinFeriado.getText().toString());
        feriado.setIdCiclo(ciclo.getIdCiclo());
        feriado.setBloquearReservas(true);
        helper.abrir();
        regInsertados = helper.insertar(feriado.getNombreTabla(), feriado.getValores());
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "El ciclo es: " + ciclo.getIdCiclo(), Toast.LENGTH_SHORT).show();
    }

}

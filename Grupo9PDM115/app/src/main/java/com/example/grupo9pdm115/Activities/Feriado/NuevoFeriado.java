package com.example.grupo9pdm115.Activities.Feriado;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.CicloAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.CicloSpinnerAdapter;

//import java.text.ParseException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NuevoFeriado extends AppCompatActivity implements View.OnClickListener {
    //Declarando
    EditText editNombreFeriado, editDescripcionFeriado, editInicioFeriado, editFinFeriado;
    Spinner spnCicloFeriado;
    CicloSpinnerAdapter cicloSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "IFE"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_feriado);
        editNombreFeriado = (EditText) findViewById(R.id.editNombreFeriado);
        editDescripcionFeriado = (EditText) findViewById(R.id.editDescripcionFeriado);
        editInicioFeriado = (EditText) findViewById(R.id.editInicioFeriado);
        editFinFeriado = (EditText) findViewById(R.id.editFinFeriado);
        spnCicloFeriado = (Spinner) findViewById(R.id.spinnerCicloFeriadoNuevo);

        editInicioFeriado.setOnClickListener(this);
        editFinFeriado.setOnClickListener(this);

        llenarCicloSpinner();
    }

    //Metodo para fechas
    @Override
    public void onClick(View v) {
        final EditText ed = (EditText) v;
        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int anio = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ed.setText(String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year));
            }
        }, anio, mes, dia);

        datePickerDialog.show();
    }

    public void llenarCicloSpinner(){
        Ciclo ciclo = new Ciclo();
        CicloAdapter cicloAdapter = new CicloAdapter(this, ciclo.getAll(this));
        cicloSpinnerAdapter = new CicloSpinnerAdapter(this);
        //spnCicloFeriado.setAdapter(cicloSpinnerAdapter.getAdapterCiclo(this));
        spnCicloFeriado.setAdapter(cicloAdapter);
    }

    //Metodo para insertar feriado
    public void agregarFeriado(View v) throws ParseException {
        //Obteniendo Valores

        String nombreFeriado = editNombreFeriado.getText().toString();
        String descripcionFeriado= editDescripcionFeriado.getText().toString();
        String inicioFeriado = editInicioFeriado.getText().toString();
        String finFeriado = editFinFeriado.getText().toString();
        //Instanciando feriado para guardar
        Feriado feriado = new Feriado();
        Ciclo ciclo = new Ciclo();
        feriado.setNombreFeriado(nombreFeriado);
        feriado.setDescripcionFeriado(descripcionFeriado);
        feriado.setFechaInicioFeriado(inicioFeriado);
        feriado.setFechaFinFeriado(finFeriado);
        feriado.setIdCiclo(ciclo.getIdCiclo());
        feriado.setBloquearReservas(true);
        validarfecha(feriado.getFechaInicioFeriado(),feriado.getFechaFinFeriado(), feriado);
    }

    public void validarfecha(String fechai, String fechaf, Feriado feriado) throws ParseException {
        String regInsertados;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
        if(feriado.getNombreFeriado().isEmpty())
        {
            regInsertados = "Nombre está vacio";
        }
        else{
            if(feriado.getDescripcionFeriado().isEmpty()){
                regInsertados = "Descripción está vacio";
            }
            else{
                Date d1 = sdformat.parse(fechai);
                Date d2 = sdformat.parse(fechaf);
                if (d1.compareTo(d2)==0){
                    regInsertados = "Las fechas son iguales";
                }else{
                    if (d1.compareTo(d2) > 0){
                        regInsertados = "Las fecha inicial es mayor que la fecha final";
                    }
                    else{
                        regInsertados = feriado.guardar(this);
                    }
                }
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    //Limpiar campos
    public void limpiarTexto(View v) {
        editNombreFeriado.setText("");
        editDescripcionFeriado.setText("");
        editInicioFeriado.setText("");
        editFinFeriado.setText("");
    }

    /*    public void validarfecha(String fechai, String fechaf, Feriado feriado) throws ParseException {
        String regInsertados;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy/MM/dd");
        if(feriado.getNombreFeriado().isEmpty())
        {
            regInsertados = "Nombre está vacio";
        }
        else{
            if(feriado.getDescripcionFeriado().isEmpty()){
                regInsertados = "Descripción está vacio";
            }
            else{
                Date d1 = sdformat.parse(fechai);
                Date d2 = sdformat.parse(fechaf);
                if (d1.compareTo(d2)==0){
                    regInsertados = "Las fechas son iguales";
                }else{
                    if (d1.compareTo(d2) > 0){
                        regInsertados = "Las fecha inicial es mayor que la fecha final";
                    }
                    else{
                        regInsertados = feriado.guardar(this);
                    }
                }
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }*/

}

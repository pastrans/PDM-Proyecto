package com.example.grupo9pdm115.Activities.Ciclo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Utilidades.FechasHelper;


import java.util.Calendar;

public class NuevoCiclo extends AppCompatActivity implements View.OnClickListener{
    //Declarando
    EditText editNombreCiclo, editInicioCiclo, editFinCiclo, editInicioClases, editFinClases;
    private int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ICL"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ciclo);

        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        editInicioCiclo = (EditText) findViewById(R.id.editInicioCiclo);
        editFinCiclo = (EditText) findViewById(R.id.editFinCiclo);
        editInicioClases = (EditText) findViewById(R.id.editInicioClases);
        editFinClases = (EditText) findViewById(R.id.editFinClases);

        editInicioCiclo.setOnClickListener(this);
        editFinCiclo.setOnClickListener(this);
        editInicioClases.setOnClickListener(this);
        editFinClases.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        EditText ed = null;
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);

        if(v==editInicioCiclo){
            ed = editInicioCiclo;
        }
        if(v==editFinCiclo){
            ed = editFinCiclo;
        }
        if(v==editInicioClases){
           ed = editInicioClases;
        }
        if(v==editFinClases){
            ed = editFinClases;
        }

        final EditText finalEd = ed;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                finalEd.setText(String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
            }
        },anio,mes,dia);

        datePickerDialog.show();
    }

    // Método para insertar ciclo
    public void agregarCiclo(View v){
        boolean error = false;
        String mensaje = "";

        //Obteniendo valores elementos
        String nombreCiclo = editNombreCiclo.getText().toString();
        String inicioCiclo = editInicioCiclo.getText().toString();
        String finCiclo = editFinCiclo.getText().toString();
        String inicioClases = editInicioClases.getText().toString();
        String finClases = editFinClases.getText().toString();

        // Verificando fechas
        if(!(FechasHelper.fechaEstaEnmedio(inicioCiclo, finCiclo, inicioClases)
                && FechasHelper.fechaEstaEnmedio(inicioCiclo, finCiclo, finClases)
                && FechasHelper.fechaEsPosterior(inicioClases, finClases))){
            error = true;
            mensaje = "El periodo de clases debe estar dentro del tiempo de duración del ciclo." +
                    "\nLa fecha de fin de periodo de clases debe ser posterior a la de inicio de periodo de clases.";
        }
        // Verificando campos vacíos
        if(nombreCiclo.equals("") || inicioCiclo.equals("") || finCiclo.equals("") || inicioClases.equals("")
                || finClases.equals("")){
            error = true;
            mensaje = "Ningun campo debe estar vacío.";
        }

        // Operaciones si no hay errores
        if(error == false){
            //Instanciando ciclo para guardar
            Ciclo ciclo = new Ciclo();
            ciclo.setNombreCiclo(nombreCiclo);
            ciclo.setInicio(inicioCiclo);
            ciclo.setFin(finCiclo);
            ciclo.setEstadoCiclo(false); // Se almacena como inactivo por defecto
            ciclo.setInicioPeriodoClase(inicioClases);
            ciclo.setFinPeriodoClase(finClases);
            mensaje = ciclo.guardar(this);
        }

        // Mensaje de salida
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    //Limpiar campos
    public void limpiarTexto(View v) {
        editNombreCiclo.setText("");
        editInicioCiclo.setText("");
        editFinCiclo.setText("");
        editInicioClases.setText("");
        editFinClases.setText("");
    }
}

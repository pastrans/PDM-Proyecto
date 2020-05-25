package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;


import java.util.Calendar;

public class NuevoCiclo extends Activity implements View.OnClickListener{
    //Declarando
    ControlBD helper;
    EditText editNombreCiclo, editInicioCiclo, editFinCiclo, editInicioClases, editFinClases;
    RadioButton estadoRadioButton1, estadoRadioButton2;
    Button btnInicioCiclo, btnFinCiclo, btnInicioClases, btnFinClases;
    private int diaic, mesic, anoic, diafc, mesfc, anofc, diaicl, mesicl, anoicl, diafcl, mesfcl, anofcl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ciclo);
        helper = new ControlBD(this);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        editInicioCiclo = (EditText) findViewById(R.id.editInicioCiclo);
        editFinCiclo = (EditText) findViewById(R.id.editFinCiclo);
        editInicioClases = (EditText) findViewById(R.id.editInicioClases);
        editFinClases = (EditText) findViewById(R.id.editFinClases);
        btnInicioCiclo = (Button) findViewById(R.id.btnInicioCiclo);
        btnFinCiclo = (Button) findViewById(R.id.btnFinCiclo);
        btnInicioClases = (Button) findViewById(R.id.btnInicioClases);
        btnFinClases = (Button) findViewById(R.id.btnFinClases);
        estadoRadioButton1 = (RadioButton) findViewById(R.id.estadoRadioButton1);
        estadoRadioButton2 = (RadioButton) findViewById(R.id.estadoRadioButton2);

        btnInicioCiclo.setOnClickListener(this);
        btnFinCiclo.setOnClickListener(this);
        btnInicioClases.setOnClickListener(this);
        btnFinClases.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v==btnInicioCiclo){
            final Calendar c = Calendar.getInstance();
            diaic = c.get(Calendar.DAY_OF_MONTH);
            mesic = c.get(Calendar.MONTH);
            anoic = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    editInicioCiclo.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            },anoic,mesic,diaic);
            datePickerDialog.show();
        }
        if(v==btnFinCiclo){
            final Calendar c = Calendar.getInstance();
            diafc = c.get(Calendar.DAY_OF_MONTH);
            mesfc = c.get(Calendar.MONTH);
            anofc = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth2) {
                    editFinCiclo.setText(dayOfMonth2+"/"+(monthOfYear+1)+"/"+year);
                }
            },anofc,mesfc,diafc);
            datePickerDialog.show();
        }
        if(v==btnInicioClases){
            final Calendar c = Calendar.getInstance();
            diaicl = c.get(Calendar.DAY_OF_MONTH);
            mesicl = c.get(Calendar.MONTH);
            anoicl = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth3) {
                    editInicioClases.setText(dayOfMonth3+"/"+(monthOfYear+1)+"/"+year);
                }
            },anoicl,mesicl,diaicl);
            datePickerDialog.show();
        }
        if(v==btnFinClases){
            final Calendar c = Calendar.getInstance();
            diafcl = c.get(Calendar.DAY_OF_MONTH);
            mesfcl = c.get(Calendar.MONTH);
            anofcl = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth4) {
                    editFinClases.setText(dayOfMonth4+"/"+(monthOfYear+1)+"/"+year);
                }
            },anofcl,mesfcl,diafcl);
            datePickerDialog.show();
        }
    }

    //Metodo para insertar ciclo
    public void btnAgregarNCiclo(View v){
        //Verificando Radio Button
        if (estadoRadioButton1.isChecked()){
            final String text = "Activo";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();

        }else if(estadoRadioButton2.isChecked()){
            final String text = "Inactivo";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        //Obteniendo valores elementos
        String nombreCiclo = editNombreCiclo.getText().toString();
        String inicioCiclo = editInicioCiclo.getText().toString();
        String finCiclo = editFinCiclo.getText().toString();
        Boolean estado = Boolean.parseBoolean(estadoRadioButton1.getText().toString());
        String inicioClases = editInicioClases.getText().toString();
        String finClases = editFinClases.getText().toString();
        //Instanciando ciclo para guardar
        Ciclo ciclo = new Ciclo();
        ciclo.setNombreCiclo(nombreCiclo);
        ciclo.setInicio(inicioCiclo);
        ciclo.setFin(finCiclo);
        ciclo.setEstadoCiclo(estado);
        ciclo.setInicioPeriodoClase(inicioClases);
        ciclo.setFinPeriodoClase(finClases);
        String regInsertados = ciclo.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    // Método para regresar al activity anterior
    public void btnRegresarNCiclo(View v){
        super.onBackPressed();
    }

    //Limpiar campos
    public void btnLimpiarTextoNCiclo(View v) {
        editNombreCiclo.setText("");
        editInicioCiclo.setText("");
        editFinCiclo.setText("");
        editInicioClases.setText("");
        editFinClases.setText("");
    }
}

package com.example.grupo9pdm115.Activities.Feriado;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.R;

//import java.text.ParseException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NuevoFeriado extends Activity implements View.OnClickListener {
    //Declarando
    EditText editNombreFeriado, editDescripcionFeriado, editInicioFeriado, editFinFeriado;
    Button btnInicioFeriado, btnFinFeriado;
    Spinner cicloFeriado;
    ControlBD helper;
    private int diaif, mesif, anoif, diaff, mesff, anoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_feriado);
        editNombreFeriado = (EditText) findViewById(R.id.editNombreFeriado);
        editDescripcionFeriado = (EditText) findViewById(R.id.editDescripcionFeriado);
        editInicioFeriado = (EditText) findViewById(R.id.editInicioFeriado);
        editFinFeriado = (EditText) findViewById(R.id.editFinFeriado);
        btnInicioFeriado = (Button) findViewById(R.id.btnInicioFeriado);
        btnFinFeriado = (Button) findViewById(R.id.btnFinFeriado);
        cicloFeriado = (Spinner) findViewById(R.id.spinnerCicloFeriadoNuevo);

        helper = new ControlBD(this);
        llenarCicloSpinner(this);

        btnInicioFeriado.setOnClickListener(this);
        btnFinFeriado.setOnClickListener(this);
    }

    //Metodo para fechas
    @Override
    public void onClick(View v) {
        if (v == btnInicioFeriado) {
            final Calendar c = Calendar.getInstance();
            diaif = c.get(Calendar.DAY_OF_MONTH);
            mesif = c.get(Calendar.MONTH);
            anoif = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    editInicioFeriado.setText( year+ "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                }
            }, anoif, mesif, diaif);
            datePickerDialog.show();
        }
        if (v == btnFinFeriado) {
            final Calendar c = Calendar.getInstance();
            diaff = c.get(Calendar.DAY_OF_MONTH);
            mesff = c.get(Calendar.MONTH);
            anoff = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth2) {
                    editFinFeriado.setText(year+ "/" + (monthOfYear + 1) + "/" + dayOfMonth2 );
                }
            }, anoff, mesff, diaff);
            datePickerDialog.show();
        }
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
    //Metodo para insertar feriado
    public void btnAgregarNFeriado(View v) throws ParseException {
        //Obteniendo Valores
        String regInsertados;
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
        if(feriado.getNombreFeriado().isEmpty())
        {
            regInsertados = "Nombre está vacio";
        }
        else{
            if(feriado.getDescripcionFeriado().isEmpty()){
                regInsertados = "Descripción está vacio";
            }
            else{
                if(feriado.getFechaInicioFeriado().isEmpty()){
                    regInsertados = "La fecha inicial está vacia";
                }
                else {
                    if (feriado.getFechaFinFeriado().isEmpty()) {
                        regInsertados = "La fecha final está vacia";
                    } else{
                        regInsertados = feriado.guardar(this);
                    }
                }
            }
        }
        //Ano-Mes-Dia
        /*SimpleDateFormat sdformat = new SimpleDateFormat();
        Date d1 = sdformat.parse(feriado.getFechaInicioFeriado());
        Date d2 = sdformat.parse(feriado.getFechaFinFeriado());
        if (d1.compareTo(d2) == 0){
            regInsertados = "las fechas son iguales";
        }else {regInsertados = feriado.guardar(this);}*/
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

    }
    /* public void btnAgregarNFeriado(View v){
         //Instanciando feriado para guardar
         Feriado feriado = new Feriado();
         Ciclo ciclo = (Ciclo) cicloFeriado.getSelectedItem();
         String regInsertados;
         feriado.setNombreFeriado(nombrFeriado.getText().toString());
         feriado.setDescripcionFeriado(descripcionFeriado.getText().toString());
         feriado.setFechaInicioFeriado(editInicioFeriado.getText().toString());
         feriado.setFechaFinFeriado(editFinFeriado.getText().toString());
         feriado.setIdCiclo(ciclo.getIdCiclo());
         feriado.setBloquearReservas(true);
         helper.abrir();
         regInsertados = helper.insertar(feriado.getNombreTabla(), feriado.getValores());
         helper.cerrar();
         Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
         //Toast.makeText(this, "El ciclo es: " + ciclo.getIdCiclo(), Toast.LENGTH_SHORT).show();
     }*/
    // Método para regresar al activity anterior

    //Limpiar campos
    public void btnLimpiarTextoNFeriado(View v) {
        editNombreFeriado.setText("");
        editDescripcionFeriado.setText("");
        editInicioFeriado.setText("");
        editFinFeriado.setText("");
    }

}

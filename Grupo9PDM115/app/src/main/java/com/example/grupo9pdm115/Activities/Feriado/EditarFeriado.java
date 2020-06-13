package com.example.grupo9pdm115.Activities.Feriado;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditarFeriado extends AppCompatActivity implements View.OnClickListener {
    //Declarando
    EditText editNombreFeriado, editDescripcionFeriado, editInicioFeriado, editFinFeriado;
    Button btnInicioFeriado, btnFinFeriado;

    Feriado feriado;
    private int diaif, mesif, anoif, diaff, mesff, anoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_feriado);

        feriado = new Feriado();
        editNombreFeriado = (EditText) findViewById(R.id.editNombre);
        editDescripcionFeriado = (EditText) findViewById(R.id.editDescripcion);
        editInicioFeriado = (EditText) findViewById(R.id.editInicioFeriado);
        editFinFeriado = (EditText) findViewById(R.id.editFinFeriado);
        btnInicioFeriado = (Button) findViewById(R.id.btnInicioFeriado);
        btnFinFeriado = (Button) findViewById(R.id.btnFinFeriado);

        btnInicioFeriado.setOnClickListener(this);
        btnFinFeriado.setOnClickListener(this);

        //Verificando paso de datos por intent
        if(getIntent().getExtras() !=null){
            feriado.setIdFeriado(getIntent().getIntExtra("idferiado", 0));
            feriado.setIdCiclo(getIntent().getIntExtra("idciclo", 0));
            editInicioFeriado.setText(getIntent().getStringExtra("inicioferiado"));
            editFinFeriado.setText(getIntent().getStringExtra("finferiado"));
            editNombreFeriado.setText(getIntent().getStringExtra("nombreferiado"));
            editDescripcionFeriado.setText(getIntent().getStringExtra("descripcionferiado"));
            feriado.setBloquearReservas(getIntent().getBooleanExtra("bloquearreservas",false));
        }
    }
    //Metodo para actualizar feriado
    public void btnEditarEFeriado(View v) throws ParseException {
        String regInsertados;
        String nombreFeriado = editNombreFeriado.getText().toString();
        feriado.setNombreFeriado(nombreFeriado);

        String descripcionFeriado = editDescripcionFeriado.getText().toString();
        feriado.setDescripcionFeriado(descripcionFeriado);

        String inicioFeriado = editInicioFeriado.getText().toString();
        feriado.setFechaInicioFeriado(inicioFeriado);

        String finFeriado = editFinFeriado.getText().toString();
        feriado.setFechaFinFeriado(finFeriado);
        validarfecha(feriado.getFechaInicioFeriado(), feriado.getFechaFinFeriado(),feriado);

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
                        regInsertados = feriado.actualizar(this);
                    }
                }
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
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
                    editInicioFeriado.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
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
                    editFinFeriado.setText( year+ "/" + (monthOfYear + 1) + "/" + dayOfMonth2);
                }
            }, anoff, mesff, diaff);
            datePickerDialog.show();
        }
    }

    //Limpiar campos
    public void btnLimpiarTextoEFeriado(View v) {
        editNombreFeriado.setText("");
        editDescripcionFeriado.setText("");
        editInicioFeriado.setText("");
        editFinFeriado.setText("");
    }

}

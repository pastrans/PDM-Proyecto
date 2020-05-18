package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;


import java.util.Calendar;

public class NuevoCiclo extends Activity {
    //Declarando
    ControlBD helper;
    EditText editNombreCiclo, t1;
    DatePicker inicioCicloDatePicker, finCicloDatePicker, inicioPeriodoClaseDatePicker,finPeriodoClaseDatePicker ;
    RadioButton estadoRadioButton1;
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID= 0;
    Calendar C = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ciclo);
        helper = new ControlBD(this);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        t1 = (EditText) findViewById(R.id.editFechaInicio);
        finCicloDatePicker = (DatePicker) findViewById(R.id.finCicloDatePicker);
        inicioPeriodoClaseDatePicker = (DatePicker) findViewById(R.id.inicioPeriodoClaseDatePicker);
        finPeriodoClaseDatePicker = (DatePicker) findViewById(R.id.finPeriodoClaseDatePicker);
        estadoRadioButton1 = (RadioButton) findViewById(R.id.estadoRadioButton1);

        //********  DESDE AQUI
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });
    }

    private void colocar_fecha(){
        t1.setText((mMonthIni +1)+ "-" + mDayIni + "-" + mYearIni+ "");
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni= year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this,mDateSetListener, sYearIni, sMonthIni, sDayIni);

        }
        return null;
    }

    //****** HASTA AQUI

    //Metodo para insertar ciclo
    public void insertarCiclo(View v){
        //Obteniendo valores elementos
        String nombreCliclo = editNombreCiclo.getText().toString();


        //Instanciando ciclo para guardar
        Ciclo ciclo = new Ciclo();
        ciclo.setNombreCiclo(nombreCliclo);
        String regInsertados = ciclo.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }
    // Método para regresar al activity anterior
    public void btnRegresarNCiclo(View v){
        super.onBackPressed();
    }
}

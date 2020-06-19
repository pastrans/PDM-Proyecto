package com.example.grupo9pdm115.Activities.Feriado;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.CicloSpinnerHelper;

import java.text.ParseException;
import java.util.Calendar;

public class EditarFeriado extends AppCompatActivity implements View.OnClickListener {
    //Declarando
    EditText editNombreFeriado, editDescripcionFeriado, editInicioFeriado, editFinFeriado;
    Spinner spnCicloFeriado;
    RadioButton rbReservasBloqueadas, rbReservasPermitidas;
    TextView txtFechaInicioFeriado;
    LinearLayout layoutFinFeriado;
    CicloSpinnerHelper cicloSpinnerHelper;

    Feriado feriado;
    int control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "EFE"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_feriado);

        // Inicializando views
        editNombreFeriado = (EditText) findViewById(R.id.editNombreFeriado);
        editDescripcionFeriado = (EditText) findViewById(R.id.editDescripcionFeriado);
        editInicioFeriado = (EditText) findViewById(R.id.editInicioFeriado);
        editFinFeriado = (EditText) findViewById(R.id.editFinFeriado);
        txtFechaInicioFeriado = (TextView) findViewById(R.id.txtFechaInicioFeriado);
        layoutFinFeriado = (LinearLayout) findViewById(R.id.layoutFinFeriado);
        spnCicloFeriado = (Spinner) findViewById(R.id.spinnerCicloFeriadoNuevo);
        rbReservasBloqueadas = (RadioButton) findViewById(R.id.rbReservasBloqueadas);
        rbReservasPermitidas = (RadioButton) findViewById(R.id.rbReservasPermitidas);

        feriado = new Feriado();
        control = 0;

        // Llenar spinner ciclo feriado
        cicloSpinnerHelper = new CicloSpinnerHelper(this);
        spnCicloFeriado.setAdapter(cicloSpinnerHelper.getAdapterCiclo(this));

        // Definiendo click listener
        editInicioFeriado.setOnClickListener(this);
        editFinFeriado.setOnClickListener(this);

        //Verificando paso de datos por intent
        if(getIntent().getExtras() !=null){
            feriado.setIdFeriado(getIntent().getIntExtra("idferiado", 0));
            // Modificar selección del spinner según id del ciclo
            spnCicloFeriado.setSelection(cicloSpinnerHelper.getActualPositionInSpinner(this,
                    getIntent().getIntExtra("idciclo", 0)));
            editInicioFeriado.setText(getIntent().getStringExtra("inicioferiado"));
            // Si fin feriado está vacío es fecha única
            editFinFeriado.setText(getIntent().getStringExtra("finferiado"));
            if(editFinFeriado.getText().toString().equals("")){
                layoutFinFeriado.setVisibility(View.GONE);
                txtFechaInicioFeriado.setText("Fecha:");
                control = 1;
            }
            editNombreFeriado.setText(getIntent().getStringExtra("nombreferiado"));
            editDescripcionFeriado.setText(getIntent().getStringExtra("descripcionferiado"));
            // Modificar radio buttons según bloquearreservas
            feriado.setBloquearReservas(getIntent().getBooleanExtra("bloquearreservas",false));
            if(!feriado.isBloquearReservas()){
                rbReservasPermitidas.setChecked(true);
            }
        }
    }

    //Metodo para actualizar feriado
    public void actualizarFeriado(View v) throws ParseException {
        // Instanciando feriado para actualizar
        feriado.setNombreFeriado(editNombreFeriado.getText().toString());
        feriado.setDescripcionFeriado(editDescripcionFeriado.getText().toString());
        feriado.setFechaInicioFeriadoFromLocal(editInicioFeriado.getText().toString());
        feriado.setFechaFinFeriadoFromLocal(editFinFeriado.getText().toString());
        feriado.setIdCiclo(cicloSpinnerHelper.getIdCiclo(spnCicloFeriado.getSelectedItemPosition()));
        feriado.setBloquearReservas(rbReservasBloqueadas.isChecked());

        // Validaciones lógicas
        String mensaje = "";
        switch (feriado.verificarCampos(this, control)){
            case 0:
                mensaje = feriado.actualizar(this);
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                finish();
                break;
            case 1:
                mensaje = "Todos los campos deben estar llenos."; break;
            case 2:
                mensaje = "Las fechas deben estar dentro del ciclo."; break;
            case 3:
                mensaje = "La fecha de fin debe ser posterior a la de inicio."; break;
        }

        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    //Limpiar campos
    public void limpiarTexto(View v) {
        editNombreFeriado.setText("");
        editDescripcionFeriado.setText("");
        editInicioFeriado.setText("");
        editFinFeriado.setText("");
        spnCicloFeriado.setSelection(0);
    }

    // Método para fechas
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

}

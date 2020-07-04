package com.example.grupo9pdm115.Activities.Feriado;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.work.Data;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.CicloSpinnerHelper;

import java.util.Calendar;
import java.util.UUID;


public class NuevoFeriado extends CyaneaAppCompatActivity implements View.OnClickListener {
    //Declarando
    EditText editNombreFeriado, editDescripcionFeriado, editInicioFeriado, editFinFeriado, editHInicio;
    Spinner spnCicloFeriado;
    CheckBox cbFechaUnica;
    RadioButton rbReservasBloqueadas, rbReservasPermitidas;
    TextView txtFechaInicioFeriado;
    LinearLayout layoutFinFeriado;
    CicloSpinnerHelper cicloSpinnerHelper;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if ((Sesion.getLoggedIn( getApplicationContext() ) && !Sesion.getAccesoUsuario( getApplicationContext(), "IFE" ))
                || !Sesion.getLoggedIn( getApplicationContext() )) {
            Intent intent = new Intent( this, ErrorDeUsuario.class );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity( intent );
            finish();
        }

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nuevo_feriado );

        // Inicializando views
        editNombreFeriado = (EditText) findViewById( R.id.editNombreFeriado );
        editDescripcionFeriado = (EditText) findViewById( R.id.editDescripcionFeriado );
        editInicioFeriado = (EditText) findViewById( R.id.editInicioFeriado );
        editFinFeriado = (EditText) findViewById( R.id.editFinFeriado );
        cbFechaUnica = (CheckBox) findViewById( R.id.cbFechaUnica );
        txtFechaInicioFeriado = (TextView) findViewById( R.id.txtFechaInicioFeriado );
        layoutFinFeriado = (LinearLayout) findViewById( R.id.layoutFinFeriado );
        spnCicloFeriado = (Spinner) findViewById( R.id.spinnerCicloFeriadoNuevo );
        rbReservasBloqueadas = (RadioButton) findViewById( R.id.rbReservasBloqueadas );
        rbReservasPermitidas = (RadioButton) findViewById( R.id.rbReservasPermitidas );

        editHInicio = (EditText) findViewById( R.id.editHoraInicio );

        // Llenar spinner ciclo feriado
        cicloSpinnerHelper = new CicloSpinnerHelper( this );
        spnCicloFeriado.setAdapter( cicloSpinnerHelper.getAdapterCiclo( this ) );

        // Definiendo click listener
        editInicioFeriado.setOnClickListener( this );
        editFinFeriado.setOnClickListener( this );

        editHInicio.setOnClickListener( this );
    }

    //Metodo para insertar feriado
    public void agregarFeriado(View v) {
        // Instanciando feriado para guardar
        Feriado feriado = new Feriado();
        feriado.setNombreFeriado( editNombreFeriado.getText().toString() );
        feriado.setDescripcionFeriado( editDescripcionFeriado.getText().toString() );
        feriado.setFechaInicioFeriadoFromLocal( editInicioFeriado.getText().toString() );
        feriado.setFechaFinFeriadoFromLocal( editFinFeriado.getText().toString() );
        feriado.setIdCiclo( cicloSpinnerHelper.getIdCiclo( spnCicloFeriado.getSelectedItemPosition() ) );
        feriado.setBloquearReservas( rbReservasBloqueadas.isChecked() );

        String tag = generateKey();
        Long Alertime = calendar.getTimeInMillis() - System.currentTimeMillis();
        int random = (int) (Math.random() * 50 + 1);

        Data data = GuardarData( editNombreFeriado.getText().toString(), editDescripcionFeriado.getText().toString(), random );
        Workmanagernoti.GuardarNoti( Alertime, data, tag );

        // Validaciones lógicas
        int caso = (cbFechaUnica.isChecked()) ? 1 : 0; // 1 verificación con fecha única, 0 con inicio y fin
        String mensaje = "";
        switch (feriado.verificarCampos( this, caso )) {
            case 0:
                mensaje = feriado.guardar( this );
                limpiar();
                break;
            case 1:
                mensaje = "Todos los campos deben estar llenos.";
                break;
            case 2:
                mensaje = "Las fechas deben estar dentro del ciclo.";
                break;
            case 3:
                mensaje = "La fecha de fin debe ser posterior a la de inicio.";
                break;
        }

        Toast.makeText( this, mensaje, Toast.LENGTH_SHORT ).show();
    }

    private void limpiar() {
        editNombreFeriado.setText( "" );
        editDescripcionFeriado.setText( "" );
        editInicioFeriado.setText( "" );
        editFinFeriado.setText( "" );
        editHInicio.setText("");
        spnCicloFeriado.setSelection( 0 );

    }

    //Limpiar campos
    public void limpiarTexto(View v) {
        limpiar();
    }

    // Método para ocultar o mostrar el view de fecha fin según el checkbox
    public void onCheckboxClicked(View v) {
        // Verificar si el checkbox fue seleccionado o no
        if (((CheckBox) v).isChecked()) {
            layoutFinFeriado.setVisibility( View.GONE );
            editFinFeriado.setText( "" );
            txtFechaInicioFeriado.setText( "Fecha:" );
        } else {
            layoutFinFeriado.setVisibility( View.VISIBLE );
            txtFechaInicioFeriado.setText( "Fecha de inicio:" );
        }
    }


    // Método para fechas
    @Override
    public void onClick(View v) {
        if (v == editInicioFeriado || v == editFinFeriado) {
            final EditText ed = (EditText) v;
            final Calendar c = Calendar.getInstance();
            int dia = c.get( Calendar.DAY_OF_MONTH );
            int mes = c.get( Calendar.MONTH );
            int anio = c.get( Calendar.YEAR );

            DatePickerDialog datePickerDialog = new DatePickerDialog( this, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH,monthOfYear);
                    calendar.set(Calendar.DATE,dayOfMonth);
                    ed.setText( String.format( "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year ) );
                }
            }, anio, mes, dia );

            datePickerDialog.show();
        }
        if (v == editHInicio) {
            final EditText editHora = (EditText) v;
            String horaEnEditTextUnida = ((EditText) v).getText().toString();
            String[] horaEnEditTextSeparada = horaEnEditTextUnida.split( ":" );
            final Calendar c = Calendar.getInstance();

            // Si el EditText tiene una hora colocar esa hora, de lo contrario, colocar la hora actual
            int hora = horaEnEditTextUnida.equals( "" ) ? c.get( Calendar.HOUR_OF_DAY ) : Integer.parseInt( horaEnEditTextSeparada[0] );
            int minuto = horaEnEditTextUnida.equals( "" ) ? c.get( Calendar.MINUTE ) : Integer.parseInt( horaEnEditTextSeparada[1] );

            TimePickerDialog timePickerDialog = new TimePickerDialog( this, new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calendar.set( Calendar.HOUR_OF_DAY, hourOfDay );
                    calendar.set( Calendar.MINUTE, minute );
                    editHora.setText( String.format( "%02d:%02d", hourOfDay, minute ) );
                }
            }, hora, minuto, true );
            timePickerDialog.show();
        }
    }
    private String generateKey(){
        return UUID.randomUUID().toString();
    }

    private Data GuardarData(String titulo, String detalle, int id_noti){
        return new Data.Builder()
                .putString( "titulo",titulo )
                .putString( "detalle",detalle )
                .putInt( "id_noti", id_noti).build();

    }
}

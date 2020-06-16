package com.example.grupo9pdm115.Activities.EventoEspecial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.DetalleReserva.NuevoDetalleReservaEspecial;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.EventoEspecial;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.CicloMateriaSpinner;
import com.example.grupo9pdm115.Utilidades.FechasHelper;

import java.util.Calendar;

public class NuevoEventoEspecial extends AppCompatActivity implements View.OnClickListener {

    EditText edtNombreEvento, edtFechaEvento, edtDescripcionEvento;
    Spinner spinnerCicloMateria;
    CicloMateriaSpinner cicloMateriaSpinnerAdapter;
    ControlBD helper;
    int dia, mes, anio, idSolicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento_especial);

        if(getIntent().getExtras() != null){
            idSolicitud = getIntent().getIntExtra("idSolicitud", 0);
        }

        helper = new ControlBD(this);

        edtNombreEvento = (EditText) findViewById(R.id.editNombreEventoEspecial);
        edtDescripcionEvento = (EditText) findViewById(R.id.editDescripcionEventoEspecial);
        edtFechaEvento = (EditText) findViewById(R.id.editFechaEventoEspecial);
        spinnerCicloMateria = (Spinner) findViewById(R.id.spinnerCicloMateriaEventoEspecialNuevo);
        helper.abrir();
        cicloMateriaSpinnerAdapter = new CicloMateriaSpinner(helper, FechasHelper.cambiarFormatoLocalAIso(getAhora()));
        helper.cerrar();
        spinnerCicloMateria.setAdapter(cicloMateriaSpinnerAdapter.getAdapterMateria(getApplication()));
        edtFechaEvento.setOnClickListener(this);
    }

    public void btnNuevoNEventoEspecial(View v){
        EventoEspecial eventoEspecial = new EventoEspecial();
        String nombreEvento = edtNombreEvento.getText().toString().trim();
        String descripcionEvento = edtDescripcionEvento.getText().toString().trim();
        String fechaEvento = edtFechaEvento.getText().toString().trim();
        int posMateria = spinnerCicloMateria.getSelectedItemPosition();
        if(nombreEvento.equals("")){
            Toast.makeText(this, "Ingrese un nombre el evento", Toast.LENGTH_SHORT).show();
            return;
        }
        if(fechaEvento.equals("")){
            Toast.makeText(this, "Ingrese una fecha para el evento", Toast.LENGTH_SHORT).show();
            return;
        }
        eventoEspecial.setNombreEvento(nombreEvento);
        eventoEspecial.setFechaEvento(FechasHelper.cambiarFormatoLocalAIso(fechaEvento));
        eventoEspecial.setDescripcionEvento(descripcionEvento);
        if(posMateria != 0)
            eventoEspecial.setIdCicloMateria(cicloMateriaSpinnerAdapter.getIdCicloMateria(posMateria));
        else
            eventoEspecial.setIdCicloMateria(0);
        Toast.makeText(this, String.valueOf(eventoEspecial.getIdCicloMateria()), Toast.LENGTH_SHORT).show();
        String res = eventoEspecial.guardar(this);
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NuevoDetalleReservaEspecial.class);
        intent.putExtra("idEventoEspecial", eventoEspecial.getLast(this));
        intent.putExtra("idSolicitud", idSolicitud);
        intent.putExtra("fechaReserva", eventoEspecial.getFechaEvento());
        startActivity(intent);
    }


    public void ahora(){
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);
    }

    public String getAhora(){
        final Calendar c = Calendar.getInstance();
        return String.format("%02d/%02d/%d", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1,  c.get(Calendar.YEAR));
    }

    @Override
    public void onClick(View v){
        EditText ed = (EditText) v;
        ahora();
        final EditText finalEd = ed;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                finalEd.setText(String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1,  year));
            }
        },anio,mes,dia);

        datePickerDialog.show();
    }
}

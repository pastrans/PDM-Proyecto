package com.example.grupo9pdm115.Activities.Local;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.TipoLocalSpinner;

public class NuevoLocal extends CyaneaAppCompatActivity {

    EditText nombreLocal;
    EditText capcidad;
    Spinner tipoLocalSpinner;
    TipoLocalSpinner tipoLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ILO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_local);
        nombreLocal = (EditText) findViewById(R.id.editNombreLocal);
        capcidad = (EditText) findViewById(R.id.editCapacidad);

        // Spinner
        tipoLocalSpinner = (Spinner) findViewById(R.id.tipoLocalSpinner);
        tipoLocalAdapter = new TipoLocalSpinner(this);
        tipoLocalSpinner.setAdapter(tipoLocalAdapter.getAdapterTipoLocal(this));
    }

    public void btnAgregarNLocal(View v){
        String regInsertados;
        Local local = new Local();
        int posTipoLocal = tipoLocalSpinner.getSelectedItemPosition();
        local.setNombreLocal(nombreLocal.getText().toString());
        local.setCapacidad(Integer.parseInt(capcidad.getText().toString()));
        local.setIdtipolocal(tipoLocalAdapter.getIdTipoLocal(posTipoLocal));
        if(local.getNombreLocal().isEmpty()){
            regInsertados="EL nombre está vacio";
        }
        else{
            if(local.getCapacidad() <= 0){
                regInsertados = "La capacidad debe ser mayor a cero";
            }else {
                if (local.getIdtipolocal() == 0) {
                    regInsertados = "Escoga un tipo de local";
                } else {
                    regInsertados = local.guardar(this);
                    limpiar();
                }
            }
        }
        //regInsertados = local.guardar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {
        nombreLocal.setText("");
        capcidad.setText("");
        tipoLocalSpinner.setSelection(0);
    }

    public void btnLimpiarNLocal(View v){
    limpiar();
    }

}

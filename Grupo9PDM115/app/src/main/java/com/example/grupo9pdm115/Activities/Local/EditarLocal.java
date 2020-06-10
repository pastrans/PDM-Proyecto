package com.example.grupo9pdm115.Activities.Local;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.TipoLocalSpinner;

public class EditarLocal extends AppCompatActivity {

    EditText nombreLocal;
    EditText capcidad;
    Spinner tipoLocalSpinner;
    ControlBD helper;
    TipoLocal tipoLocalClass;
    TipoLocalSpinner tipoLocalAdapter;
    Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_local);
        helper = new ControlBD(this);
        tipoLocalClass = new TipoLocal();
        local = new Local();
        tipoLocalSpinner = (Spinner) findViewById(R.id.spinnerTipoLocal);
        nombreLocal = (EditText) findViewById(R.id.editNombreLocal);
        capcidad = (EditText) findViewById(R.id.editCapacidad);
        helper.abrir();
        tipoLocalAdapter = new TipoLocalSpinner(helper);
        helper.cerrar();
        tipoLocalSpinner.setAdapter(tipoLocalAdapter.getAdapterTipoLocal(getApplicationContext()));
        int idTipoLocal = 0;
        if (getIntent().getExtras() != null){
            int capacidad = 0;
            capacidad = getIntent().getIntExtra("capacidad", 0);
            idTipoLocal = getIntent().getIntExtra("idTipoLocal", 0);
            local.setIdlocal(getIntent().getIntExtra("idLocal", 0));
            nombreLocal.setText(getIntent().getStringExtra("nombreLocal"));
            capcidad.setText(String.valueOf(capacidad));
            for (int i = 1; i < tipoLocalSpinner.getAdapter().getCount(); i++){
                int idTipoLocalItem = tipoLocalAdapter.getIdTipoLocal(i);
                if (idTipoLocalItem == idTipoLocal)
                    tipoLocalSpinner.setSelection(i);
            }
        }
    }

    public void btnEditarELocal(View v){
        int posTipoLocal = 0;
        String res = "";
        posTipoLocal = tipoLocalSpinner.getSelectedItemPosition();
        local.setNombreLocal(nombreLocal.getText().toString());
        local.setCapacidad(Integer.valueOf(capcidad.getText().toString()));
        local.setIdtipolocal(tipoLocalAdapter.getIdTipoLocal(posTipoLocal));
        res = local.actualizar(this);
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public void btnLimpiarELocal(View v){
        nombreLocal.setText("");
        capcidad.setText("0");
        tipoLocalSpinner.setSelection(0);
    }

    public void btnRegresarELocal(View v){
        finish();
    }

}

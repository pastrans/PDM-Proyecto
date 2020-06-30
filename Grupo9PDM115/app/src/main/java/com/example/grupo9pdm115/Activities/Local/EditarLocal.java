package com.example.grupo9pdm115.Activities.Local;

import androidx.appcompat.app.AppCompatActivity;

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
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class EditarLocal extends AppCompatActivity {

    EditText nombreLocal;
    EditText capcidad;
    Spinner tipoLocalSpinner;
    TipoLocalSpinner tipoLocalAdapter;
    Local local;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ELO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_local);
        local = new Local();
        nombreLocal = (EditText) findViewById(R.id.editNombreLocal);
        capcidad = (EditText) findViewById(R.id.editCapacidad);

        // Spinner
        tipoLocalSpinner = (Spinner) findViewById(R.id.spinnerTipoLocal);
        tipoLocalAdapter = new TipoLocalSpinner(this);
        tipoLocalSpinner.setAdapter(tipoLocalAdapter.getAdapterTipoLocal(this));

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
        mSimpleDialog = new MaterialDialog.Builder(this)
            .setTitle("Editar")
            .setMessage("¿Está seguro de editar los datos?")
            .setCancelable(false)
            .setPositiveButton("Editar", R.drawable.ic_edit, new MaterialDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                int posTipoLocal = 0;
                String res = "";
                posTipoLocal = tipoLocalSpinner.getSelectedItemPosition();
                local.setNombreLocal(nombreLocal.getText().toString());
                local.setCapacidad(Integer.parseInt(capcidad.getText().toString()));
                local.setIdtipolocal(tipoLocalAdapter.getIdTipoLocal(posTipoLocal));
                if(local.getNombreLocal().isEmpty()){
                    res="EL nombre está vacio";
                }
                else{
                    if(local.getCapacidad() <= 0){
                        res = "La capacidad debe ser mayor a cero";
                    }else {
                        if (local.getIdtipolocal() == 0) {
                            res = "Escoga un tipo de local";
                        } else {
                            res = local.actualizar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                //res = local.actualizar(this);
                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                }
            })
            .setNegativeButton("Cancelar", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    dialogInterface.cancel();
                }
            })
            .setAnimation("edit_anim.json")
            .build();
        mSimpleDialog.show();
    }

    public void btnLimpiarELocal(View v){
        nombreLocal.setText("");
        capcidad.setText("");
        tipoLocalSpinner.setSelection(0);
    }

    public void btnRegresarELocal(View v){
        finish();
    }

}

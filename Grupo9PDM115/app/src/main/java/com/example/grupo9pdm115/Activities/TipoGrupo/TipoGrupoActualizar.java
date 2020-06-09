package com.example.grupo9pdm115.Activities.TipoGrupo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

public class TipoGrupoActualizar extends Activity {
    // Declarando
    EditText editNombreTG;
    TipoGrupo tipoGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_grupo_actualizar);

        tipoGrupo = new TipoGrupo();
        editNombreTG = (EditText) findViewById(R.id.editNombreTG);

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            editNombreTG.setText(getIntent().getStringExtra("nombretipogrupo"));
            tipoGrupo.setIdTipoGrupo(getIntent().getIntExtra("idtipogrupo", 0));
        }
    }

    // Método para actualizar día
    public void btnactualizar(View v) {
        String nombreTG = editNombreTG.getText().toString();
        tipoGrupo.setNombreTipoGrupo(nombreTG);
        String estado = tipoGrupo.actualizar(this);
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    // Método para regresar al activity anterior
    public void btnregresar(View v) {
        super.onBackPressed();
    }
}


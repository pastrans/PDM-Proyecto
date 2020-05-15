package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.TipoGrupo;

public class NuevoTipoGrupo extends AppCompatActivity {
    ControlBD helper;
    EditText nombreTipoGrupo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tipo_grupo);
        helper = new ControlBD(this);
        nombreTipoGrupo = (EditText) findViewById(R.id.editNombreTipoGrupo);

    }

    public void agregarTipoGrupo(View v){
        String reginsertados;
        TipoGrupo tipoGrupo = new TipoGrupo();
        tipoGrupo.setNombreTipoGrupo(nombreTipoGrupo.getText().toString());
        helper.abrir();
        reginsertados = helper.insertar(tipoGrupo.getNombreTabla(), tipoGrupo.getValores());
        helper.cerrar();
        Toast.makeText(this, reginsertados, Toast.LENGTH_SHORT).show();
    }

}

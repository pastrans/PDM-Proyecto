package com.example.grupo9pdm115.Activities.Grupo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.NuevoGrupoSpinners;

public class EditarGrupo extends AppCompatActivity {

    TextView editNumero;
    Grupo grupo;
    Spinner spinnerTipoGrupo;
    Spinner spinnerCicloMateria;
    NuevoGrupoSpinners control;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_grupo);
        helper = new ControlBD(this);
        spinnerTipoGrupo = (Spinner)  findViewById(R.id.spinTipoGrupoEdit);
        spinnerCicloMateria = (Spinner) findViewById(R.id.spinMateriaEdit);
        helper.abrir();
        control= new NuevoGrupoSpinners(helper);
        helper.cerrar();
        spinnerTipoGrupo.setAdapter(control.getAdapterTipoGrupo(this));
        spinnerCicloMateria.setAdapter(control.getAdapterMateria(this));
        editNumero = (TextView) findViewById(R.id.editNumero);
        int numeroGrupo = 0, idTipoGrupo = 0, idCicloMateria = 0;
        grupo = new Grupo();
        if (getIntent().getExtras() != null){
            numeroGrupo = getIntent().getIntExtra("numeroGrupo", 0);
            idCicloMateria = getIntent().getIntExtra("idCicloMateria", 0);
            idTipoGrupo = getIntent().getIntExtra("idTipoGrupo", 0);
            editNumero.setText(String.valueOf(numeroGrupo));
            grupo.setIdGrupo(getIntent().getIntExtra("idGrupo", 0));
            grupo.setIdTipoGrupo(numeroGrupo);
        }
        for (int i = 1; i < spinnerCicloMateria.getAdapter().getCount(); i++){
            int idCicloMateriaItem = control.getIdCicloMateria(i);
            if (idCicloMateriaItem == idCicloMateria)
                spinnerCicloMateria.setSelection(i);
        }
        for (int i = 1; i < spinnerTipoGrupo.getAdapter().getCount(); i++){
            int idTipoGrupoItem = control.getIdTipoGrupo(i);
            if (idTipoGrupoItem == idTipoGrupo)
                spinnerTipoGrupo.setSelection(i);
        }
    }

    public void btnEditarEGrupo(View v){
        String resultado = "";
        grupo.setNumero(Integer.valueOf(editNumero.getText().toString()));
        int posTipoGrupo = spinnerTipoGrupo.getSelectedItemPosition();
        int posCicloMateria = spinnerCicloMateria.getSelectedItemPosition();
        grupo.setIdTipoGrupo(control.getIdTipoGrupo(posTipoGrupo));
        grupo.setIdCicloMateria(control.getIdCicloMateria(posCicloMateria));
        String verificar = verificarDatos(grupo);
        if(!verificar.equals("")){
            Toast.makeText(this, verificar, Toast.LENGTH_SHORT).show();
            return;
        }
        resultado = grupo.actualizar(this);
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();
    }

    public void btnLimpiarEGrupo(View v){
        editNumero.setText("");
        spinnerTipoGrupo.setSelection(0);
        spinnerCicloMateria.setSelection(0);
    }

    public String verificarDatos(Grupo g){
        if(g.getNumero() < 1)
            return "Ingrese un número de grupo válido";
        if (g.getNumero() != getIntent().getIntExtra("numeroGrupo", 0)
                || g.getIdCicloMateria() != getIntent().getIntExtra("idCicloMateria", 0)
                || g.getIdTipoGrupo() != getIntent().getIntExtra("idTipoGrupo", 0)){
            if(g.verificar(1, getApplicationContext()))
                return "Ya existe un grupo";
        }
        return "";
    }

}

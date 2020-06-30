package com.example.grupo9pdm115.Activities.Grupo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.NuevoGrupoSpinners;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class EditarGrupo extends AppCompatActivity {

    TextView editNumero;
    Grupo grupo;
    Spinner spinnerTipoGrupo;
    Spinner spinnerCicloMateria;
    NuevoGrupoSpinners control;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "EGR"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_grupo);
        editNumero = (TextView) findViewById(R.id.editNumero);

        // Spinners
        spinnerTipoGrupo = (Spinner)  findViewById(R.id.spinTipoGrupoEdit);
        spinnerCicloMateria = (Spinner) findViewById(R.id.spinMateriaEdit);
        control= new NuevoGrupoSpinners(this);
        spinnerTipoGrupo.setAdapter(control.getAdapterTipoGrupo(this));
        spinnerCicloMateria.setAdapter(control.getAdapterMateria(this));

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
        mSimpleDialog = new MaterialDialog.Builder(this)
            .setTitle("Editar")
            .setMessage("¿Está seguro de editar los datos?")
            .setCancelable(false)
            .setPositiveButton("Editar", R.drawable.ic_edit, new MaterialDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String resultado = "";

                    int posTipoGrupo = spinnerTipoGrupo.getSelectedItemPosition();
                    int posCicloMateria = spinnerCicloMateria.getSelectedItemPosition();
                    if (posCicloMateria!= 0 ) {
                        if (posTipoGrupo!= 0) {
                            int a;
                            try {
                                a = Integer.parseInt(editNumero.getText().toString());
                            }catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Número inválido", Toast.LENGTH_SHORT).show();
                                return;

                            }
                            grupo.setNumero(Integer.valueOf(editNumero.getText().toString()));
                            grupo.setIdTipoGrupo(control.getIdTipoGrupo(posTipoGrupo));
                            grupo.setIdCicloMateria(control.getIdCicloMateria(posCicloMateria));
                            String verificar = verificarDatos(grupo);
                            if(!verificar.equals("")){
                                Toast.makeText(getApplicationContext(), verificar, Toast.LENGTH_SHORT).show();
                                return;
                            }
                            resultado = grupo.actualizar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Seleccione Tipo de grupo ", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Seleccione una materia", Toast.LENGTH_SHORT).show();
                    }
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

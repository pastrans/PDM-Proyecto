package com.example.grupo9pdm115.Activities.TipoGrupo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class EditarTipoGrupo extends CyaneaAppCompatActivity {
    // Declarando
    EditText editNombreTG;
    TipoGrupo tipoGrupo;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ETG"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tipo_grupo);

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
        mSimpleDialog = new MaterialDialog.Builder(this)
                .setTitle("Editar")
                .setMessage("¿Está seguro de editar los datos?")
                .setCancelable(false)
                .setPositiveButton("Editar", R.drawable.ic_edit, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String estado;
                        String nombreTG = editNombreTG.getText().toString();
                        tipoGrupo.setNombreTipoGrupo(nombreTG);

                        String verificar = verificarDatos(tipoGrupo);
                        if(!verificar.equals("")){
                            Toast.makeText(getApplicationContext(), verificar, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(tipoGrupo.getNombreTipoGrupo().isEmpty()){
                            estado="Nombre está vacío";
                        }
                        else{
                            estado = tipoGrupo.actualizar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), estado, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Toast.makeText(getApplicationContext(), estado, Toast.LENGTH_SHORT).show();
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

    //Limpiar campos
    public void btnLimpiarETipoGrupo(View v) {
        editNombreTG.setText("");
    }

    public String verificarDatos(TipoGrupo tg){
        if(tg.getNombreTipoGrupo().equals(""))
            return "Ingrese un nombre al tipo de grupo";
        if(tg.verificar(1, getApplicationContext()) && !tg.getNombreTipoGrupo().equals(getIntent().getStringExtra("nombretipogrupo")))
            return "Ya existe el tipo de grupo";
        return "";
    }

}


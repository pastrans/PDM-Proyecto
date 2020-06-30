package com.example.grupo9pdm115.Activities.TipoGrupo;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

public class NuevoTipoGrupo extends CyaneaAppCompatActivity {
    ControlBD helper;
    EditText nombreTipoGrupo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ITG"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_tipo_grupo);
        helper = new ControlBD(this);
        nombreTipoGrupo = (EditText) findViewById(R.id.editNombreTipoGrupo);

    }

    //Metodo para insertar tipo de grupo
    public void btnAgregarNTipoGrupo(View v){
        // Obteniendo valores elementos
        String regInsertados;
        String nombreTG = nombreTipoGrupo.getText().toString();

        // Instanciando dia para guardar
        TipoGrupo tipoGrupo = new TipoGrupo();
        tipoGrupo.setNombreTipoGrupo(nombreTG);

        String verificar = verificarDatos(tipoGrupo);
        if(!verificar.equals("")){
            Toast.makeText(this, verificar, Toast.LENGTH_SHORT).show();
            return;
        }

        //Validando campos vacíos
        if(tipoGrupo.getNombreTipoGrupo().isEmpty()){
            regInsertados ="Nombre está vacío";
        }
        else{
            regInsertados = tipoGrupo.guardar(this);
            limpiar();
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {
        nombreTipoGrupo.setText("");
    }

    //Limpiar campos
    public void btnLimpiarNTipoGrupo(View v) {
        limpiar();
    }

    public String verificarDatos(TipoGrupo tg){
        if(tg.getNombreTipoGrupo().equals(""))
            return "Ingrese un nombre al tipo de grupo";
        if(tg.verificar(1, getApplicationContext()))
            return "Ya existe el tipo de grupo";
        return "";
    }

}

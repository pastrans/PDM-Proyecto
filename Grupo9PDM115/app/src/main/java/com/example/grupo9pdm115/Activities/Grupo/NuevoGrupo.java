package com.example.grupo9pdm115.Activities.Grupo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.NuevoGrupoSpinners;


public class NuevoGrupo extends AppCompatActivity {

    EditText numero;
    Spinner idTipoGrupo;
    Spinner idCicloMateria;
    NuevoGrupoSpinners control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "IGR"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_grupo);
        numero = (EditText) findViewById(R.id.editNumero);

        // Spinners
        idTipoGrupo = (Spinner)  findViewById(R.id.spinTipoGrupo);
        idCicloMateria = (Spinner) findViewById(R.id.spinMateria);
        control= new NuevoGrupoSpinners(this);
        idTipoGrupo.setAdapter(control.getAdapterTipoGrupo(this));
        idCicloMateria.setAdapter(control.getAdapterMateria(this));
    }

    public void btnNuevoNGrupo(View v){
        String reginsertados;
        Grupo grupo = new Grupo();
        int posicionTipoGrupo = 0, idTP = 0,posicionMateria= 0,idCM =0;
        posicionTipoGrupo = idTipoGrupo.getSelectedItemPosition();
        posicionMateria= idCicloMateria.getSelectedItemPosition();
        if (posicionMateria!= 0 ) {
            if (posicionTipoGrupo!= 0) {
                int a;
                try {
                    a = Integer.parseInt(numero.getText().toString());
                }catch (NumberFormatException e){
                    Toast.makeText(this, "Número inválido", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Log.i("posicion: ", String.valueOf(posicion)  );
                grupo.setNumero(Integer.parseInt(numero.getText().toString()));
                idTP= control.getIdTipoGrupo(posicionTipoGrupo);
                grupo.setIdTipoGrupo(idTP);
                idCM= control.getIdCicloMateria(posicionMateria);//
                grupo.setIdCicloMateria(idCM);
                String verificar = verificarDatos(grupo);
                if(!verificar.equals("")){
                    Toast.makeText(this, verificar, Toast.LENGTH_SHORT).show();
                    return;
                }
                reginsertados = grupo.guardar(this);
                Toast.makeText(this, reginsertados, Toast.LENGTH_SHORT).show();
                limpiar();
            }
            else {
                Toast.makeText(this, "Seleccione Tipo de grupo ", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Seleccione una materia", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar() {
        numero.setText("");
        idTipoGrupo.setSelection(0);
        idCicloMateria.setSelection(0);
    }

    public void btnLimpiarNGrupo(View v){
        limpiar();
    }


    public String verificarDatos(Grupo g){
        if(g.getNumero() < 1)
            return "Ingrese un número de grupo válido";
        if(g.verificar(1, getApplicationContext()))
            return "Ya existe un grupo";
        return "";
    }

}

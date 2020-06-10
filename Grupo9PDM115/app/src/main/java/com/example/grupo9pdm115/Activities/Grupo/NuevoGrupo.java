package com.example.grupo9pdm115.Activities.Grupo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.NuevoGrupoSpinners;


public class NuevoGrupo extends AppCompatActivity {
    ControlBD helper;
    EditText numero;
    Spinner idTipoGrupo;
    Spinner idCicloMateria;
    NuevoGrupoSpinners control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_grupo);
        helper = new ControlBD(this);
        numero = (EditText) findViewById(R.id.editNumero);
        idTipoGrupo = (Spinner)  findViewById(R.id.spinTipoGrupo);
        idCicloMateria = (Spinner) findViewById(R.id.spinMateria);
        helper.abrir();
        control= new NuevoGrupoSpinners(helper);
        helper.cerrar();
        idTipoGrupo.setAdapter(control.getAdapterTipoGrupo(getApplicationContext()));
        idCicloMateria.setAdapter(control.getAdapterMateria(getApplicationContext()));

    }
    public void btnNuevoNGrupo(View v){
        String reginsertados;
        Grupo grupo = new Grupo();
        int posicionTipoGrupo = 0, idTP = 0,posicionMateria= 0,idCM =0;
        posicionTipoGrupo = idTipoGrupo.getSelectedItemPosition();
        posicionMateria= idCicloMateria.getSelectedItemPosition();
        if (posicionMateria!= 0 ) {
            if (posicionTipoGrupo!= 0) {
                //Log.i("posicion: ", String.valueOf(posicion)  );
                grupo.setNumero(Integer.parseInt(numero.getText().toString()));
                idTP= control.getIdTipoGrupo(posicionTipoGrupo);
                grupo.setIdTipoGrupo(idTP);
                idCM= control.getIdCicloMateria(posicionMateria);//
                grupo.setIdCicloMateria(idCM);
                helper.abrir();
                reginsertados = grupo.guardar(this);
                helper.cerrar();
                Toast.makeText(this, reginsertados, Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(this, "Seleccione Tipo de grupo ", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Seleccione una materia", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnLimpiarNGrupo(View v){
        numero.setText("");
        idTipoGrupo.setSelection(0);
        idCicloMateria.setSelection(0);
    }

    public void btnRegresarNGrupo(View v){
        finish();
    }

}

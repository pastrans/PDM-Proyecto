package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Spinners.NuevoGrupoSpinners;


public class NuevoGrupo extends AppCompatActivity {
    ControlBD helper;
    EditText numero;
    Spinner idTipoGrupo;
    EditText idCicloMateria;
    NuevoGrupoSpinners control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_grupo);
        helper = new ControlBD(this);
        numero = (EditText) findViewById(R.id.editNumero);
        idTipoGrupo = (Spinner)  findViewById(R.id.spinTipoGrupo);
        helper.abrir();
        control= new NuevoGrupoSpinners(helper);
        helper.cerrar();
        idTipoGrupo.setAdapter(control.getAdapterTipoGrupo(getApplicationContext()));
        idCicloMateria = (EditText) findViewById(R.id.editMateria);
    }
    public void btnNuevoNGrupo(View v){
        String reginsertados;
        Grupo grupo = new Grupo();
        int posicion = 0, id = 0;
        posicion = idTipoGrupo.getSelectedItemPosition();

        if (posicion!= 0) {
            //Log.i("posicion: ", String.valueOf(posicion)  );
            grupo.setNumero(Integer.parseInt(numero.getText().toString()));
            id= control.getIdTipoGrupo(posicion);
            grupo.setIdTipoGrupo(id);
            grupo.setIdCicloMateria(Integer.parseInt(idCicloMateria.getText().toString()));
            helper.abrir();
            reginsertados = helper.insertar(grupo.getNombreTabla(), grupo.getValores());
            helper.cerrar();
            Toast.makeText(this, reginsertados, Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(this, "Seleccione Tipo de grupo ", Toast.LENGTH_SHORT).show();
        }







    }




}

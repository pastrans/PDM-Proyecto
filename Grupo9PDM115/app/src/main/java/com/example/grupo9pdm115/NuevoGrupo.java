package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Grupo;

import java.util.ArrayList;


public class NuevoGrupo extends AppCompatActivity {
    ControlBD helper;
    EditText numero;
    Spinner idTipoGrupo;
    EditText idCicloMateria;
   // String[] lenguajes = {"Seleccione","Ruby","Java",".NET","Python","PHP","JavaScript","GO"};
    ArrayList<String> contenido;  // para llenar el contendio del spiner
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_grupo);
        helper = new ControlBD(this);
        numero = (EditText) findViewById(R.id.editNumero);
        idTipoGrupo = (Spinner)  findViewById(R.id.spinTipoGrupo);
        helper.abrir();
        helper.consultarListaTipoGrupo();
        helper.cerrar();
        contenido = new ArrayList<String>();
        contenido.add("Seleccione");
        for (int i=0; i<helper.getLisTipoGrupo().size(); i++){
            contenido.add(helper.getLisTipoGrupo().get(i).getNombreTipoGrupo());

        }
        idTipoGrupo.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,contenido));
        //ArrayAdapter<CharSequence> adapter =
        //       ArrayAdapter.createFromResource(this, R.array.contenidoTipoGrupo, android.R.layout.simple_spinner_item);
        //idTipoGrupo.setAdapter(adapter);
        idCicloMateria = (EditText) findViewById(R.id.editMateria);
    }
    public void btnNuevoNGrupo(View v){
        String reginsertados;
        Grupo grupo = new Grupo();
        int posicion = 0, id = 0;
        grupo.setNumero(Integer.parseInt(numero.getText().toString()));
        posicion = idTipoGrupo.getSelectedItemPosition();
        posicion --; // le resto uno porque el primer elemento es seleccione
        if (posicion!= -1) {
            //Log.i("posicion: ", String.valueOf(posicion)  );
            id = helper.getLisTipoGrupo().get(posicion).getIdTipoGrupo();



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

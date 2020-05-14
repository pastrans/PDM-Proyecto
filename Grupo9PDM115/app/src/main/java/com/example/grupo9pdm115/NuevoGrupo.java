package com.example.grupo9pdm115;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Grupo;


public class NuevoGrupo extends AppCompatActivity {
    ControlBD helper;
    EditText numero;
    Spinner idTipoGrupo;
    EditText idCicloMateria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_grupo);
        helper = new ControlBD(this);
        numero = (EditText) findViewById(R.id.editNumero);
        idTipoGrupo = (Spinner)  findViewById(R.id.spinTipoGrupo);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.contenidoTipoGrupo, android.R.layout.simple_spinner_item);
        idTipoGrupo.setAdapter(adapter);
        idCicloMateria = (EditText) findViewById(R.id.editMateria);
    }
    public void nuevoGrupo(View v){
        String reginsertados;
        Grupo grupo = new Grupo();

        grupo.setNumero(Integer.parseInt(numero.getText().toString()));
        grupo.setIdTipoGrupo(idTipoGrupo.getSelectedItemPosition());
        grupo.setIdCicloMateria(Integer.parseInt(idCicloMateria.getText().toString()));
        helper.abrir();
        reginsertados = helper.insertar(grupo.getNombreTabla(), grupo.getValores());
        helper.cerrar();

        Toast.makeText(this, reginsertados, Toast.LENGTH_SHORT).show();
    }




}

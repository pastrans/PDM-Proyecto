package com.example.grupo9pdm115.Activities.CicloMateria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.CicloMateriaAdapter;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarCicloMateria  extends AppCompatActivity {
    //Declarando atributos para manejo del ListView
    ListView listaCicloMaterias;
    EditText editCodigoMateria;
    CicloMateriaAdapter listaMateriaAdapter;
    CicloMateria cicloMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if ((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CCM"))
                || !Sesion.getLoggedIn(getApplicationContext())) {
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ciclo_materia);
        editCodigoMateria = (EditText) findViewById(R.id.editcodmateria);

        //Inicializar elementos para llenar lista
        listaCicloMaterias = (ListView) findViewById(R.id.listaCicloMaterias);

        //Llamar método para llenar lista
        llenarListaCicloMateria(null);


        //Asociamos el menú contextual al listview
        registerForContextMenu(listaCicloMaterias);
    }

    //Metodo parra llenar lista de ciclos
    public void llenarListaCicloMateria(String filtro) {
        cicloMateria = new CicloMateria();
        List objetcts;

        if (filtro == null) {
            objetcts = cicloMateria.getAll(this);
        } else {
            objetcts = cicloMateria.getAllFiltered(this, "codmateria", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaMateriaAdapter = new CicloMateriaAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaCicloMaterias.setAdapter(listaMateriaAdapter);
    }

    //Metodo para agregar ciclo
    public void agregarCicloMateria(View v) {
        Intent intent = new Intent(this, NuevoCicloMateria.class);
        startActivity(intent);
    }

    public void buscarMateria(View v) {
        llenarListaCicloMateria(editCodigoMateria.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaCicloMateria(null);
    }

    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // Modificable
        menu.setHeaderTitle(listaMateriaAdapter.getItem(info.position).getCodMateria());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctc_lista_ciclo_materia, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        CicloMateria cmActual = (listaMateriaAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarCicloMateria:
                if (cmActual != null) {
                    Intent intent = new Intent(getApplicationContext(), EditarCicloMateria.class);
                    intent.putExtra("idciclomateria", cmActual.getIdCicloMateria());
                    intent.putExtra("codmateria", cmActual.getCodMateria());
                    intent.putExtra("idciclo", cmActual.getIdCiclo());
                    startActivity(intent);
                }
                return true;

            case R.id.ctxEliminarCicloMateria:
                if (cmActual != null) {
                    String regEliminadas;
                    regEliminadas = cmActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaCicloMateria(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

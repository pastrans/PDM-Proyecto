package com.example.grupo9pdm115.Activities.Coordinacion;

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

import com.example.grupo9pdm115.Activities.Materia.EditarMateria;
import com.example.grupo9pdm115.Activities.Materia.NuevoMateria;
import com.example.grupo9pdm115.Adapters.CoordinacionAdapter;
import com.example.grupo9pdm115.Adapters.MateriaAdapter;
import com.example.grupo9pdm115.Modelos.Coordinacion;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarCoordinacion extends AppCompatActivity {

    //Declarando atributos para manejo del ListView
    ListView listaCoordinacion;
    CoordinacionAdapter listaCoordinacionAdapter;
    EditText editNombreUsuario;
    Coordinacion coordinacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_coordinacion);

        //Inicializar elementos para llenar lista
        listaCoordinacion = (ListView) findViewById(R.id.listacoordinacion);
        editNombreUsuario = (EditText) findViewById(R.id.editNombreCoordinador);

        //Llamar método para llenar lista
        llenarListaCoordinacion("");

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaCoordinacion);
    }

    //Metodo parra llenar lista de materias
    public void llenarListaCoordinacion(String filtro) {
        coordinacion = new Coordinacion();
        List objetcts;

        if (filtro.equals(null) ) {
            objetcts = coordinacion.getAll(this);
        } else {
            objetcts = coordinacion.getAllFiltered(this, "idusuario", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaCoordinacionAdapter = new CoordinacionAdapter(this, objetcts);


        // Relacionando la lista con el adaptador y llenándola
        listaCoordinacion.setAdapter(listaCoordinacionAdapter);

    }

    //Metodo para agregar materia
    public void btnNuevoCoodinacion(View v){
        Intent intent = new Intent(this, NuevoCoordinacion.class);
        startActivity(intent);
    }

    public void buscarCoordinador(View v) {
        llenarListaCoordinacion(editNombreUsuario.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaCoordinacion("");
    }

    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaCoordinacionAdapter.getItem(info.position).getIdUsuario());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctc_lista_coordinacion, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Coordinacion coorActual = (listaCoordinacionAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarCoordinacion:
                if(coorActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarMateria.class);
                    intent.putExtra("idcoordinacion", coorActual.getIdCoodinacion());
                    intent.putExtra("idusuario", coorActual.getIdUsuario());
                    intent.putExtra("idciclomateria", coorActual.getIdCicloMateria());
                    intent.putExtra("tipocoordinacion", coorActual.getTipoCoordinacion());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarCoordinacion:
                if(coorActual != null){
                    String regEliminadas;
                    regEliminadas= coorActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaCoordinacion("");
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}

package com.example.grupo9pdm115.Activities.Materia;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Adapters.MateriaAdapter;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarMateria extends AppCompatActivity {

    //Declarando atributos para manejo del ListView
    ListView listaMaterias;
    MateriaAdapter listaMateriasAdapter;
    Materia materia;
    EditText editnombreMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_materia);

        //Inicializar elementos para llenar lista
        listaMaterias = (ListView) findViewById(R.id.listaMaterias);
        editnombreMateria = (EditText) findViewById(R.id.editNombreMateria);
        //Llamar método para llenar lista
        llenarListaMateria(null);

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaMaterias);
    }

    //Metodo parra llenar lista de materias
    public void llenarListaMateria(String filtro){
        materia = new Materia();
        List objetcts;

        if (filtro == null) {
            objetcts = materia.getAll(this);
        } else {
            objetcts = materia.getAllFiltered(this, "codmateria", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaMateriasAdapter = new MateriaAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaMaterias.setAdapter(listaMateriasAdapter);
    }

    //Metodo para agregar materia
    public void btnNuevoMateria(View v){
        Intent intent = new Intent(this, NuevoMateria.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaMateria(null);
    }

    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaMateriasAdapter.getItem(info.position).getNombreMateria());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_materia, menu);
    }

    public void buscarMateria(View v) {
        llenarListaCicloMateria(editnombreMateria.getText().toString());
    }
    public void llenarListaCicloMateria(String filtro) {
        materia = new Materia();
        List objetcts;

        if (filtro == null) {
            objetcts = materia.getAll(this);
        } else {
            objetcts = materia.getAllFiltered(this, "nombremat", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaMateriasAdapter = new MateriaAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaMaterias.setAdapter(listaMateriasAdapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Materia materiaActual = (listaMateriasAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarMateria:
                if(materiaActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarMateria.class);
                    intent.putExtra("codMateria", materiaActual.getCodMateria());
                    intent.putExtra("idUnidad", materiaActual.getIdUnidad());
                    intent.putExtra("nombreMat", materiaActual.getNombreMateria());
                    intent.putExtra("masiva", materiaActual.isMasiva());

                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarMateria:
                if(materiaActual != null){
                    String regEliminadas;
                    regEliminadas= materiaActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaMateria(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}

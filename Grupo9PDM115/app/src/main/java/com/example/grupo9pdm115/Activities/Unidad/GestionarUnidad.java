package com.example.grupo9pdm115.Activities.Unidad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.UnidadAdapter;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarUnidad extends Activity {
    ListView listUnidad;
    UnidadAdapter listaUnidadAdapter;
    Unidad unidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_unidad);

        // Inicializar elementos a manejar
        listUnidad = (ListView) findViewById(R.id.listUnidad);

        // Llamar método para llenar lista
        llenarListaUnidad();

        // Asociamos el menú contextual al listview
        registerForContextMenu(listUnidad);
    }
    // Método para llenar lista de unidad
    public void llenarListaUnidad(){
        unidad = new Unidad();
        List objects = unidad.getAll(this);

        // Inicializar el adaptador con la información a mostrar
        listaUnidadAdapter = new UnidadAdapter(this, objects);

        // Relacionando la lista con el adaptador y llenándola
        listUnidad.setAdapter(listaUnidadAdapter);
    }
    // Método para agregar una unidad
    public void agregarUnidad (View v){
        Intent intent = new Intent(this, NuevoUnidad.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaUnidad();
    }
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaUnidadAdapter.getItem(info.position).getNombreent());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_dias, menu);//NO ES NECESARIO CREAR UNO NUEVO, POR QUE SE REPITE PARA TODOS (EL DISEÑO)
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Unidad unidadActual = (listaUnidadAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(unidadActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarUnidad.class);
                    intent.putExtra("idunidad", unidadActual.getIdUnidad());
                    intent.putExtra("nombreent", unidadActual.getNombreent());
                    intent.putExtra("descripcionent", unidadActual.getDescripcionent());
                    intent.putExtra("prioridad", unidadActual.getPrioridad());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(unidadActual != null){
                    String regEliminadas;
                    regEliminadas= unidadActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaUnidad();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}

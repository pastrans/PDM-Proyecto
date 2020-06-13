package com.example.grupo9pdm115.Activities.TipoGrupo;



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

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Adapters.TipoGrupoAdapter;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarTipoGrupo extends AppCompatActivity {
    // Declarando atributos para manejo del ListView
    ListView listaTipoGrupo;
    TipoGrupoAdapter listaTipoGrupoAdapter;
    TipoGrupo tipoGrupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tipo_grupo);
        // Inicializar elementos a manejar
        listaTipoGrupo = (ListView) findViewById(R.id.listTG);

        // Llamar método para llenar lista
        llenarListaTipoGrupo();

        // Asociamos el menú contextual al listview
        registerForContextMenu(listaTipoGrupo);
    }

    // Método para llenar lista de días
    public void llenarListaTipoGrupo(){
        tipoGrupo = new TipoGrupo();
        List objects = tipoGrupo.getAll(this);

        // Inicializar el adaptador con la información a mostrar
        listaTipoGrupoAdapter = new TipoGrupoAdapter(this, objects);

        // Relacionando la lista con el adaptador y llenándola
        listaTipoGrupo.setAdapter(listaTipoGrupoAdapter);
    }
    // Método para agregar un día
    public void agregarTipoGrupo(View v){
        Intent intent = new Intent(this, NuevoTipoGrupo.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaTipoGrupo();
    }

    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaTipoGrupoAdapter.getItem(info.position).getNombreTipoGrupo());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_tipo_grupo, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TipoGrupo TGActual = (listaTipoGrupoAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(TGActual != null){
                    Intent intent = new Intent(getApplicationContext(), TipoGrupoActualizar.class);
                    intent.putExtra("idtipogrupo", TGActual.getIdTipoGrupo());
                    intent.putExtra("nombretipogrupo", TGActual.getNombreTipoGrupo());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(TGActual != null){
                    if (TGActual.verificar(2, getApplicationContext())){
                        Toast.makeText(this, "No se puede eliminar debido a dependecias del registro", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    String regEliminadas;
                    regEliminadas= TGActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaTipoGrupo();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

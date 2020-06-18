package com.example.grupo9pdm115.Activities.TipoLocal;

import androidx.annotation.NonNull;
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

import com.example.grupo9pdm115.Adapters.TipoLocalAdapter;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarTipoLocal extends AppCompatActivity {

    TipoLocal tipoLocal;
    EditText editNombreCiclo;
    ListView listaTipos;
    TipoLocalAdapter listaTipoLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tipo_local);
        listaTipos = (ListView) findViewById(R.id.idListadoTiposLocales);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreTipoLocal);
        llenarListaTipoLocal(null);
    }

    public void llenarListaTipoLocal(String filtro){
        tipoLocal = new TipoLocal();
        List objetcts;

        if(filtro == null){
            objetcts = tipoLocal.getAll(this);
        }
        else{
            objetcts = tipoLocal.getAllFiltered(this,"nombretipo", filtro);
        }

        listaTipoLocalAdapter = new TipoLocalAdapter(this, objetcts);
        listaTipos.setAdapter(listaTipoLocalAdapter);
        registerForContextMenu(listaTipos);
    }
    public void buscarTipoLocal(View v){
        llenarListaTipoLocal(editNombreCiclo.getText().toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaTipoLocal(null);
    }

    public void agregarTipoLocal (View v){
        Intent inte = new Intent(this, NuevoTipoLocal.class);
        startActivity(inte);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreTipo = listaTipoLocalAdapter.getItem(info.position).getNombreTipo();
        menu.setHeaderTitle("Acciones para "+ nombreTipo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_tipo_local, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TipoLocal tipoLocalSeleccionado = (listaTipoLocalAdapter.getItem(info.position));
        switch (item.getItemId()){
            case R.id.ctxActualizar:
                Intent inte = new Intent(this, EditarTipoLocal.class);
                inte.putExtra("nombreTipo", tipoLocalSeleccionado.getNombreTipo());
                inte.putExtra("idEncargado", tipoLocalSeleccionado.getIdEncargado());
                inte.putExtra("idTipoLocal", tipoLocalSeleccionado.getIdTipoLocal());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if (tipoLocalSeleccionado != null){
                    String resEliminados = "";
                    resEliminados = tipoLocalSeleccionado.eliminar(this);
                    Toast.makeText(this, resEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaTipoLocal(null);
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

}

package com.example.grupo9pdm115.Activities.Local;

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

import com.example.grupo9pdm115.Adapters.LocalAdapter;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarLocal extends AppCompatActivity {

    Local local;
    EditText editNombrelocal;
    ListView listaLocal;
    LocalAdapter listaLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_local);
        listaLocal = (ListView) findViewById(R.id.idListadoLocales);
        editNombrelocal = (EditText) findViewById(R.id.editNombreLocal);
        llenarListaLocales(null);
    }
    public void buscarLocal(View v){
        llenarListaLocales(editNombrelocal.getText().toString());
    }
    public void llenarListaLocales(String filtro){
        local = new Local();
        List objetcts;

        if(filtro == null){
            objetcts = local.getAll(this);
        }
        else{
            objetcts = local.getAllFiltered(this,"nombrelocal", filtro);
        }
        listaLocalAdapter = new LocalAdapter(this, objetcts);
        listaLocal.setAdapter(listaLocalAdapter);
        registerForContextMenu(listaLocal);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaLocales(null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreLocal = listaLocalAdapter.getItem(info.position).getNombreLocal();
        menu.setHeaderTitle("Acciones para "+ nombreLocal);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_tipo_local, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Local localSeleccionado = (listaLocalAdapter.getItem(info.position));
        switch (item.getItemId()){
            case R.id.ctxActualizar:
                Intent inte = new Intent(this, EditarLocal.class);
                inte.putExtra("nombreLocal", localSeleccionado.getNombreLocal());
                inte.putExtra("capacidad", localSeleccionado.getCapacidad());
                inte.putExtra("idTipoLocal", localSeleccionado.getIdtipolocal());
                inte.putExtra("idLocal", localSeleccionado.getIdlocal());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if (localSeleccionado != null){
                    String resEliminados = "";
                    resEliminados = localSeleccionado.eliminar(this);
                    Toast.makeText(this, resEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaLocales(null);
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void btnNuevoGLocal(View v){
        Intent inte = new Intent(this, NuevoLocal.class);
        startActivity(inte);
    }

}

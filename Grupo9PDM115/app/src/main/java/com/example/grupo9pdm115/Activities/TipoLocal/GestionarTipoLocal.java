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

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.TipoLocalAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarTipoLocal extends AppCompatActivity {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    TipoLocal tipoLocal;
    EditText editNombreCiclo;
    ListView listaTipos;
    TipoLocalAdapter listaTipoLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "ITL");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DTL");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ETL");
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CTL"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }
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
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
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
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent inte = new Intent(this, EditarTipoLocal.class);
                inte.putExtra("nombreTipo", tipoLocalSeleccionado.getNombreTipo());
                inte.putExtra("idEncargado", tipoLocalSeleccionado.getIdEncargado());
                inte.putExtra("idTipoLocal", tipoLocalSeleccionado.getIdTipoLocal());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
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

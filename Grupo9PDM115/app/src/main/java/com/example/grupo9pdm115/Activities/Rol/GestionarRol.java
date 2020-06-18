package com.example.grupo9pdm115.Activities.Rol;

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

import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarRol extends AppCompatActivity {


    Rol rol;
    EditText editNombreCiclo;
    ListView listaRoles;
    RolAdapter listaRolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_rol);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreRol);
        listaRoles = (ListView) findViewById(R.id.listViewRoles);
        llenarListaRoles(null);
    }

    public void nuevoRol(View v){
        Intent inte = new Intent(this, NuevoRol.class);
        startActivity(inte);
    }

    public void buscarRol(View v){
        llenarListaRoles(editNombreCiclo.getText().toString());
    }

    public void llenarListaRoles(String filtro){
        rol = new Rol();
        List objetcts;

        if(filtro == null){
            objetcts = rol.getAll(this);
        }
        else{
            objetcts = rol.getAllFiltered(this,"nombrerol", filtro);
        }

        listaRolAdapter = new RolAdapter(this, objetcts);
        listaRoles.setAdapter(listaRolAdapter);
        registerForContextMenu(listaRoles);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaRoles(null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreRol = listaRolAdapter.getItem(info.position).getNombreRol();
        menu.setHeaderTitle("Acciones para "+ nombreRol);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_rol, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Rol rolSeleccionado = (listaRolAdapter.getItem(info.position));
        switch (item.getItemId()){
            case R.id.ctxActualizarRol:
                Intent inte = new Intent(this, EditarRol.class);
                inte.putExtra("nombreRol", rolSeleccionado.getNombreRol());
                inte.putExtra("idRol", rolSeleccionado.getIdRol());
                startActivity(inte);
                return true;
            case R.id.ctxEliminarRol:
                if (rolSeleccionado != null){
                    String resEliminados = "";
                    resEliminados = rolSeleccionado.eliminar(this);
                    Toast.makeText(this, resEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaRoles(null);
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
}

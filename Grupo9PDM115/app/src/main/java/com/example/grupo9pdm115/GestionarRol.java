package com.example.grupo9pdm115;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Adapters.UsuarioAdapter;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Usuario;

import java.util.List;

public class GestionarRol extends AppCompatActivity {


    Rol rol;
    ListView listaRoles;
    RolAdapter listaRolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_rol);
        listaRoles = (ListView) findViewById(R.id.listViewRoles);
        llenarListaRoles();
    }

    public void nuevoRol(View v){
        Intent inte = new Intent(this, NuevoRol.class);
        startActivity(inte);
    }

    public void llenarListaRoles(){
        rol = new Rol();
        List objects = rol.getAll(this);
        listaRolAdapter = new RolAdapter(this, objects);
        listaRoles.setAdapter(listaRolAdapter);
        registerForContextMenu(listaRoles);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaRoles();
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

                return true;
            case R.id.ctxEliminarRol:
                if (rolSeleccionado != null){
                    String resEliminados = "";
                    resEliminados = rolSeleccionado.eliminar(this);
                    Toast.makeText(this, resEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaRoles();
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
}

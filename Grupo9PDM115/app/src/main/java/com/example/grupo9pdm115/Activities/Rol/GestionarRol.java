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

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarRol extends AppCompatActivity {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Rol rol;
    EditText editNombreCiclo;
    ListView listaRoles;
    RolAdapter listaRolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IRO");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DRO");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ERO");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CRO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_rol);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreRol);
        listaRoles = (ListView) findViewById(R.id.listViewRoles);
        llenarListaRoles(null);
    }

    public void nuevoRol(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
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
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent inte = new Intent(this, EditarRol.class);
                inte.putExtra("nombreRol", rolSeleccionado.getNombreRol());
                inte.putExtra("idRol", rolSeleccionado.getIdRol());
                startActivity(inte);
                return true;
            case R.id.ctxEliminarRol:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
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

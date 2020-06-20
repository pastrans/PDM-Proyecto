package com.example.grupo9pdm115.Activities.Usuario;

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
import com.example.grupo9pdm115.Adapters.UsuarioAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarUsuario extends AppCompatActivity {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    ListView listaUsuarios;
    EditText editNombreUsuario;
    UsuarioAdapter listaUsuariosAdapter;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IUS");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DUS");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EUS");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CUS"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuario);
        listaUsuarios = (ListView) findViewById(R.id.idListadoUsuarios);
        llenarListaUsuarios(null);
        editNombreUsuario = (EditText) findViewById(R.id.editNombreUsuario);
        registerForContextMenu(listaUsuarios);
    }

    public void llenarListaUsuarios(String filtro){
        usuario = new Usuario();
        List objetcts;

        if(filtro == null){
            objetcts = usuario.getAll(this);
        }
        else{

            objetcts = usuario.getAllFiltered1(this, filtro);
        }

        listaUsuariosAdapter = new UsuarioAdapter(this, objetcts);
        listaUsuarios.setAdapter(listaUsuariosAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaUsuarios(null);
    }
    // Método para buscar usuario filtrado
    public void buscarUsuario(View v){
        llenarListaUsuarios(editNombreUsuario.getText().toString());
    }

    public void btnNuevoGUsuario(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent inte = new Intent(this, NuevoUsuario.class);
        startActivity(inte);
    }

    /*public void prueba(View v){
        if(Sesion.getLoggedIn(getApplicationContext())){
            Set<String> accesoUsuario = Sesion.getAllAccesoUsuario(getApplicationContext());
            String accesos = "Los accesos son: \n";
            //Toast.makeText(this, "HOLAAA", Toast.LENGTH_SHORT).show();
            for (String acceso : accesoUsuario){
                accesos += "Acceso a : " + acceso +"\n";
            }
            Log.i("ACCESOS", accesos);
            Toast.makeText(this, accesos, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No se ha iniciado sesión", Toast.LENGTH_SHORT).show();
        }
    }*/


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreCompleto = listaUsuariosAdapter.getItem(info.position).getNombrePersonal() + " " + listaUsuariosAdapter.getItem(info.position).getApellidoPersonal();
        menu.setHeaderTitle(nombreCompleto);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_usuario, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Usuario usuarioSeleccionado = (listaUsuariosAdapter.getItem(info.position));
        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent inte = new Intent(this, EditarUsuario.class);
                inte.putExtra("idUsuario", usuarioSeleccionado.getIdUsuario());
                inte.putExtra("nombreUsuario", usuarioSeleccionado.getNombreUsuario());
                inte.putExtra("nombrePersona", usuarioSeleccionado.getNombrePersonal());
                inte.putExtra("apellidoPersona", usuarioSeleccionado.getApellidoPersonal());
                inte.putExtra("claveUsuario", usuarioSeleccionado.getClaveUsuario());
                inte.putExtra("correoPersona", usuarioSeleccionado.getCorreoPersonal());
                inte.putExtra("unidad", usuarioSeleccionado.getIdUnidad());
                inte.putExtra("rol", usuarioSeleccionado.getIdRol());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (usuarioSeleccionado != null){
                   String regEliminados;
                   regEliminados = usuarioSeleccionado.eliminar(this);
                   Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                   llenarListaUsuarios(null);
                }
                //Toast.makeText(this, "Eliminar User", Toast.LENGTH_LONG).show();
                return true;
            /*case R.id.ctxAsignarAcceso:
                if(usuarioSeleccionado != null){
                    Intent in = new Intent(this, NuevoAccesoUsuario.class);
                    in.putExtra("idUsuario", usuarioSeleccionado.getIdUsuario());
                    in.putExtra("nombrePersonal", usuarioSeleccionado.getNombrePersonal() + " " + usuarioSeleccionado.getApellidoPersonal());
                    startActivity(in);
                }*/
            default:
                return super.onContextItemSelected(item);
        }
    }

}

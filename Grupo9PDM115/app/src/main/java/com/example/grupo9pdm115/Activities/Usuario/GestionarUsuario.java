package com.example.grupo9pdm115.Activities.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.UsuarioAdapter;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarUsuario extends AppCompatActivity {

    ListView listaUsuarios;
    EditText editNombreUsuario;
    UsuarioAdapter listaUsuariosAdapter;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

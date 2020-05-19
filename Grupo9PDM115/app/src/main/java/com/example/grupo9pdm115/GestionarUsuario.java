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

import com.example.grupo9pdm115.Adapters.UsuarioAdapter;
import com.example.grupo9pdm115.Modelos.AccesoUsuario;
import com.example.grupo9pdm115.Modelos.Usuario;

import java.util.List;

public class GestionarUsuario extends AppCompatActivity {

    ListView listaUsuarios;
    UsuarioAdapter listaUsuariosAdapter;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuario);
        listaUsuarios = (ListView) findViewById(R.id.idListadoUsuarios);
        llenarListaUsuarios();
        registerForContextMenu(listaUsuarios);
    }

    public void llenarListaUsuarios(){
        usuario = new Usuario();
        //LLENAR DATOS DE EJEMPLO
        //usuario.insertUnidad(this);
        List objects = usuario.getAll(this);
        listaUsuariosAdapter = new UsuarioAdapter(this, objects);
        listaUsuarios.setAdapter(listaUsuariosAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaUsuarios();
    }

    public void btnNuevoGUsuario(View v){
        Intent inte = new Intent(this, NuevoUsuario.class);
        startActivity(inte);
    }

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
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if (usuarioSeleccionado != null){
                   String regEliminados;
                   regEliminados = usuarioSeleccionado.eliminar(this);
                   Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                   llenarListaUsuarios();
                }
                //Toast.makeText(this, "Eliminar User", Toast.LENGTH_LONG).show();
                return true;
            case R.id.ctxAsignarAcceso:
                if(usuarioSeleccionado != null){
                    Intent in = new Intent(this, AccesoUsuarioNuevo.class);
                    in.putExtra("idUsuario", usuarioSeleccionado.getIdUsuario());
                    in.putExtra("nombrePersonal", usuarioSeleccionado.getNombrePersonal() + " " + usuarioSeleccionado.getApellidoPersonal());
                    startActivity(in);
                }
            default:
                return super.onContextItemSelected(item);
        }
    }
}

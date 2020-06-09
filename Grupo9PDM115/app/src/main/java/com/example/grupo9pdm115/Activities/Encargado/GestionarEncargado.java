package com.example.grupo9pdm115.Activities.Encargado;

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

import com.example.grupo9pdm115.Adapters.EncargadoAdapter;
import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarEncargado extends AppCompatActivity {
    ListView listaEncargados;
    EncargadoAdapter listaEncargadosAdapter;
    Encargado encargado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_encargado);
        listaEncargados = (ListView) findViewById(R.id.listEncargado);
        llenarListaEncargados();
    }
    public void llenarListaEncargados(){
        encargado = new Encargado();
        //LLENAR DATOS DE EJEMPLO
        //usuario.insertUnidad(this);
        List objects = encargado.getAll(this);
        listaEncargadosAdapter = new EncargadoAdapter(this, objects);
        listaEncargados.setAdapter(listaEncargadosAdapter);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaEncargados();
    }
    public void btnNuevoGEncargado(View v){
        Intent inte = new Intent(this, NuevoEncargado.class);
        startActivity(inte);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreCompleto = listaEncargadosAdapter.getItem(info.position).getIdUsuario();
        menu.setHeaderTitle(nombreCompleto);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_dias, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Encargado encargadoSeleccionado = (listaEncargadosAdapter.getItem(info.position));
        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                Intent inte = new Intent(this, EditarEncargado.class);
                inte.putExtra("idEncargado", encargadoSeleccionado.getIdEncargado());
                inte.putExtra("idUsuario", encargadoSeleccionado.getIdUsuario());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if (encargadoSeleccionado != null){
                    String regEliminados;
                    regEliminados = encargadoSeleccionado.eliminar(this);
                    Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaEncargados();
                }
                //Toast.makeText(this, "Eliminar User", Toast.LENGTH_LONG).show();
                return true;
            /*case R.id.ctxAsignarAcceso:
                if(encargadoSeleccionado  != null){
                    Intent in = new Intent(this, AccesoUsuarioNuevo.class);
                    in.putExtra("idUsuario", usuarioSeleccionado.getIdUsuario());
                    in.putExtra("nombrePersonal", usuarioSeleccionado.getNombrePersonal() + " " + usuarioSeleccionado.getApellidoPersonal());
                    startActivity(in);
                }*/
            default:
                return super.onContextItemSelected(item);
        }
    }
}

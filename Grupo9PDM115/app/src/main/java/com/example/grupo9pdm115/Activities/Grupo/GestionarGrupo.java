package com.example.grupo9pdm115.Activities.Grupo;

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

import com.example.grupo9pdm115.Activities.Rol.EditarRol;
import com.example.grupo9pdm115.Adapters.GrupoAdapter;
import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarGrupo extends AppCompatActivity {

    ListView listViewGrupos;
    GrupoAdapter grupoAdapter;
    Grupo grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_grupo);
        listViewGrupos = (ListView) findViewById(R.id.listViewGrupos);
        llenarListaGrupos();
    }

    public void btnNuevoGrupo(View v){
        Intent inte = new Intent(this, NuevoGrupo.class);
        startActivity(inte);
    }

    public void llenarListaGrupos(){
        grupo = new Grupo();
        List objects = grupo.getAll(this);
        grupoAdapter = new GrupoAdapter(this, objects);
        listViewGrupos.setAdapter(grupoAdapter);
        registerForContextMenu(listViewGrupos);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaGrupos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String numeroGrupo = String.valueOf(grupoAdapter.getItem(info.position).getNumero());
        menu.setHeaderTitle("Acciones para el grupo"+ numeroGrupo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_grupo, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Grupo grupoSeleccionado = (grupoAdapter.getItem(info.position));
        CicloMateria cicloMateria = new CicloMateria();
        cicloMateria.consultar(this, String.valueOf(grupoSeleccionado.getIdCicloMateria()));
        switch (item.getItemId()){
            case R.id.ctxActualizarGrupo:
                Intent inte = new Intent(this, EditarGrupo.class);
                inte.putExtra("idGrupo", grupoSeleccionado.getIdGrupo());
                inte.putExtra("numeroGrupo", grupoSeleccionado.getNumero());
                inte.putExtra("idTipoGrupo", grupoSeleccionado.getIdTipoGrupo());
                inte.putExtra("materia", cicloMateria.getCodMateria());
                inte.putExtra("idCicloMateria", grupoSeleccionado.getIdCicloMateria());
                startActivity(inte);
                return true;
            case R.id.ctxEliminarGrupo:
                if (grupoSeleccionado != null){
                    String resEliminados = "";
                    resEliminados = grupoSeleccionado.eliminar(this);
                    Toast.makeText(this, resEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaGrupos();
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
}

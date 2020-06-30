package com.example.grupo9pdm115.Activities.Grupo;

import androidx.annotation.NonNull;
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.grupo9pdm115.Activities.Ciclo.GestionarCiclo;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Activities.Rol.EditarRol;
import com.example.grupo9pdm115.Adapters.GrupoAdapter;
import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Grupo;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class GestionarGrupo extends CyaneaAppCompatActivity implements View.OnClickListener{

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listViewGrupos;
    EditText editNombreCiclo;
    //ListView listViewGrupos;
    GrupoAdapter grupoAdapter;
    Grupo grupo;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IGR");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DGR");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EGR");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CGR"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_grupo);
        //listViewGrupos = (ListView) findViewById(R.id.listViewGrupos);
        listViewGrupos = (SwipeMenuListView) findViewById(R.id.listViewGrupos);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        llenarListaGrupos(null);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //editItem.setBackground(new ColorDrawable(Color.rgb(0xF5, 0xF5,0xF5)));
                // set item width
                editItem.setWidth(170);
                // set a icon
                editItem.setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listViewGrupos.setMenuCreator(creator);
        listViewGrupos.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                final Grupo grupoSeleccionado = (grupoAdapter.getItem(position));
                CicloMateria cicloMateria = new CicloMateria();
                cicloMateria.consultar(getApplicationContext(), String.valueOf(grupoSeleccionado.getIdCicloMateria()));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        Intent inte = new Intent(getApplicationContext(), EditarGrupo.class);
                        inte.putExtra("idGrupo", grupoSeleccionado.getIdGrupo());
                        inte.putExtra("numeroGrupo", grupoSeleccionado.getNumero());
                        inte.putExtra("idTipoGrupo", grupoSeleccionado.getIdTipoGrupo());
                        inte.putExtra("materia", cicloMateria.getCodMateria());
                        inte.putExtra("idCicloMateria", grupoSeleccionado.getIdCicloMateria());
                        startActivity(inte);
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (grupoSeleccionado != null){
                            mSimpleDialog = new MaterialDialog.Builder( GestionarGrupo.this)
                                .setTitle("Eliminar")
                                .setMessage("¿Está seguro de eliminar?")
                                .setCancelable(false)
                                .setPositiveButton("Eliminar", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                    String resEliminados = "";
                                    resEliminados = grupoSeleccionado.eliminar(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), resEliminados, Toast.LENGTH_SHORT).show();
                                    llenarListaGrupos(null);
                                    dialogInterface.dismiss();
                                            }
                                })
                                .setNegativeButton("Cancelar", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                    Toast.makeText(getApplicationContext(), "Registro no eliminado!", Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                    }
                                })
                                .setAnimation("delete_anim.json")
                                .build();
                            mSimpleDialog.show();
                        }
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public void btnNuevoGrupo(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent inte = new Intent(this, NuevoGrupo.class);
        startActivity(inte);
    }

    public void llenarListaGrupos(String filtro){
        grupo = new Grupo();
        List objects;
        if (filtro == null)
        {
            objects = grupo.getAll(this);
        }else{
            objects = grupo.getAllFiltered1(this,filtro);
        }
        grupoAdapter = new GrupoAdapter(this, objects);
        listViewGrupos.setAdapter(grupoAdapter);
        //registerForContextMenu(listViewGrupos);
    }
    public void buscarGrupo(View v){
        buscarGrupo();
    }
    public void buscarGrupo(){
        llenarListaGrupos(editNombreCiclo.getText().toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaGrupos(null);
    }
/*
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
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent inte = new Intent(this, EditarGrupo.class);
                inte.putExtra("idGrupo", grupoSeleccionado.getIdGrupo());
                inte.putExtra("numeroGrupo", grupoSeleccionado.getNumero());
                inte.putExtra("idTipoGrupo", grupoSeleccionado.getIdTipoGrupo());
                inte.putExtra("materia", cicloMateria.getCodMateria());
                inte.putExtra("idCicloMateria", grupoSeleccionado.getIdCicloMateria());
                startActivity(inte);
                return true;
            case R.id.ctxEliminarGrupo:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
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

 */


    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.bvoice) {
            // Si entramos a dar clic en el boton
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora ");
            startActivityForResult(i, check); } }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode==check && resultCode==RESULT_OK){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            editNombreCiclo.setText(results.get(0));
            buscarGrupo();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
}

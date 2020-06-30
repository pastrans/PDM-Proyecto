package com.example.grupo9pdm115.Activities.Rol;

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
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class GestionarRol extends CyaneaAppCompatActivity implements View.OnClickListener{

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaRoles;
    Rol rol;
    EditText editNombreCiclo;
    //ListView listaRoles;
    RolAdapter listaRolAdapter;
    private MaterialDialog mSimpleDialog;

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
        listaRoles = (SwipeMenuListView) findViewById(R.id.listViewRoles);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //listaRoles = (ListView) findViewById(R.id.listViewRoles);
        llenarListaRoles(null);
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
        listaRoles.setMenuCreator(creator);
        listaRoles.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final Rol rolSeleccionado = (listaRolAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        Intent inte = new Intent(getApplicationContext(), EditarRol.class);
                        inte.putExtra("nombreRol", rolSeleccionado.getNombreRol());
                        inte.putExtra("idRol", rolSeleccionado.getIdRol());
                        startActivity(inte);
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (rolSeleccionado != null){
                            mSimpleDialog = new MaterialDialog.Builder(GestionarRol.this)
                                .setTitle("Eliminar")
                                .setMessage("¿Está seguro de eliminar?")
                                .setCancelable(false)
                                .setPositiveButton("Eliminar", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String resEliminados = "";
                                        resEliminados = rolSeleccionado.eliminar(getApplicationContext());
                                        Toast.makeText(getApplicationContext(), resEliminados, Toast.LENGTH_SHORT).show();
                                        llenarListaRoles(null);
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

    public void nuevoRol(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent inte = new Intent(this, NuevoRol.class);
        startActivity(inte);
    }

    public void buscarRol(View v){
        //llenarListaRoles(editNombreCiclo.getText().toString());
        buscarRol();
    }
    public void buscarRol(){

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
/*
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
    }*/

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
            buscarRol();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editNombreCiclo.setText("");
        super.onPause();
    }


}

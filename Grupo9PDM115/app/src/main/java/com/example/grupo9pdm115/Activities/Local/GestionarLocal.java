package com.example.grupo9pdm115.Activities.Local;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.grupo9pdm115.Adapters.LocalAdapter;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class GestionarLocal extends AppCompatActivity implements View.OnClickListener{

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaLocal;
    Local local;
    EditText editNombrelocal;
    //ListView listaLocal;
    LocalAdapter listaLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "ILO");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DLO");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ELO");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CLO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_local);
        listaLocal = (SwipeMenuListView) findViewById(R.id.idListadoLocales);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //listaLocal = (ListView) findViewById(R.id.idListadoLocales);
        editNombrelocal = (EditText) findViewById(R.id.editNombreLocal);
        llenarListaLocales(null);
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
        listaLocal.setMenuCreator(creator);
        listaLocal.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuItem(index);
                Local localSeleccionado = (listaLocalAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        Intent inte = new Intent(getApplicationContext(), EditarLocal.class);
                        inte.putExtra("nombreLocal", localSeleccionado.getNombreLocal());
                        inte.putExtra("capacidad", localSeleccionado.getCapacidad());
                        inte.putExtra("idTipoLocal", localSeleccionado.getIdtipolocal());
                        inte.putExtra("idLocal", localSeleccionado.getIdlocal());
                        startActivity(inte);
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (localSeleccionado != null){
                            String resEliminados = "";
                            resEliminados = localSeleccionado.eliminar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), resEliminados, Toast.LENGTH_SHORT).show();
                            llenarListaLocales(null);
                        }
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
    public void buscarLocal(View v){
        //llenarListaLocales(editNombrelocal.getText().toString());
        buscarLocal();
    }
    public void buscarLocal(){
        llenarListaLocales(editNombrelocal.getText().toString());
    }
    public void llenarListaLocales(String filtro){
        local = new Local();
        List objetcts;

        if(filtro == null){
            objetcts = local.getAll(this);
        }
        else{
            objetcts = local.getAllFiltered(this,"nombrelocal", filtro);
        }
        listaLocalAdapter = new LocalAdapter(this, objetcts);
        listaLocal.setAdapter(listaLocalAdapter);
        registerForContextMenu(listaLocal);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaLocales(null);
    }
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreLocal = listaLocalAdapter.getItem(info.position).getNombreLocal();
        menu.setHeaderTitle("Acciones para "+ nombreLocal);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_tipo_local, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Local localSeleccionado = (listaLocalAdapter.getItem(info.position));
        switch (item.getItemId()){
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent inte = new Intent(this, EditarLocal.class);
                inte.putExtra("nombreLocal", localSeleccionado.getNombreLocal());
                inte.putExtra("capacidad", localSeleccionado.getCapacidad());
                inte.putExtra("idTipoLocal", localSeleccionado.getIdtipolocal());
                inte.putExtra("idLocal", localSeleccionado.getIdlocal());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (localSeleccionado != null){
                    String resEliminados = "";
                    resEliminados = localSeleccionado.eliminar(this);
                    Toast.makeText(this, resEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaLocales(null);
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }
    */

    public void btnNuevoGLocal(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent inte = new Intent(this, NuevoLocal.class);
        startActivity(inte);
    }
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
            editNombrelocal.setText(results.get(0));
            buscarLocal();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause (){
        editNombrelocal.setText("");
        super.onPause();
    }

}

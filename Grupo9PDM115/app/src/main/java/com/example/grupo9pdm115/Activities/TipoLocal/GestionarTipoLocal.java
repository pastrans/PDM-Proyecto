package com.example.grupo9pdm115.Activities.TipoLocal;

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
import com.example.grupo9pdm115.Adapters.TipoLocalAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class GestionarTipoLocal extends AppCompatActivity implements View.OnClickListener{

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaTipos;
    TipoLocal tipoLocal;
    EditText editNombreCiclo;
    //ListView listaTipos;
    TipoLocalAdapter listaTipoLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "ITL");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DTL");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ETL");
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CTL"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tipo_local);
        listaTipos = (SwipeMenuListView) findViewById(R.id.idListadoTiposLocales);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //listaTipos = (ListView) findViewById(R.id.idListadoTiposLocales);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreTipoLocal);
        llenarListaTipoLocal(null);
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
        listaTipos.setMenuCreator(creator);
        listaTipos.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuItem(index);
                TipoLocal tipoLocalSeleccionado = (listaTipoLocalAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        Intent inte = new Intent(getApplicationContext(), EditarTipoLocal.class);
                        inte.putExtra("nombreTipo", tipoLocalSeleccionado.getNombreTipo());
                        inte.putExtra("idEncargado", tipoLocalSeleccionado.getIdEncargado());
                        inte.putExtra("idTipoLocal", tipoLocalSeleccionado.getIdTipoLocal());
                        startActivity(inte);
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (tipoLocalSeleccionado != null){
                            String resEliminados = "";
                            resEliminados = tipoLocalSeleccionado.eliminar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), resEliminados, Toast.LENGTH_SHORT).show();
                            llenarListaTipoLocal(null);
                        }
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    public void llenarListaTipoLocal(String filtro){
        tipoLocal = new TipoLocal();
        List objetcts;

        if(filtro == null){
            objetcts = tipoLocal.getAll(this);
        }
        else{
            objetcts = tipoLocal.getAllFiltered(this,"nombretipo", filtro);
        }

        listaTipoLocalAdapter = new TipoLocalAdapter(this, objetcts);
        listaTipos.setAdapter(listaTipoLocalAdapter);
        registerForContextMenu(listaTipos);
    }
    public void buscarTipoLocal(View v){
        //llenarListaTipoLocal(editNombreCiclo.getText().toString());
        buscarTipoLocal();
    }
    public void buscarTipoLocal(){
        llenarListaTipoLocal(editNombreCiclo.getText().toString());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaTipoLocal(null);
    }

    public void agregarTipoLocal (View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent inte = new Intent(this, NuevoTipoLocal.class);
        startActivity(inte);
    }
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreTipo = listaTipoLocalAdapter.getItem(info.position).getNombreTipo();
        menu.setHeaderTitle("Acciones para "+ nombreTipo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_tipo_local, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TipoLocal tipoLocalSeleccionado = (listaTipoLocalAdapter.getItem(info.position));
        switch (item.getItemId()){
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent inte = new Intent(this, EditarTipoLocal.class);
                inte.putExtra("nombreTipo", tipoLocalSeleccionado.getNombreTipo());
                inte.putExtra("idEncargado", tipoLocalSeleccionado.getIdEncargado());
                inte.putExtra("idTipoLocal", tipoLocalSeleccionado.getIdTipoLocal());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (tipoLocalSeleccionado != null){
                    String resEliminados = "";
                    resEliminados = tipoLocalSeleccionado.eliminar(this);
                    Toast.makeText(this, resEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaTipoLocal(null);
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
            buscarTipoLocal();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editNombreCiclo.setText("");
        super.onPause();
    }

}

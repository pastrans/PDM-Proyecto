package com.example.grupo9pdm115.Activities.TipoGrupo;



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

import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.TipoGrupoAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.TipoGrupo;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class GestionarTipoGrupo extends AppCompatActivity implements View.OnClickListener{
    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;
    // Declarando atributos para manejo del ListView
    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaTipoGrupo;
    //ListView listaTipoGrupo;
    EditText editNombreTP;
    TipoGrupoAdapter listaTipoGrupoAdapter;
    TipoGrupo tipoGrupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "ITG");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DTG");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ETG");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CTG"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_tipo_grupo);
        // Inicializar elementos a manejar
        listaTipoGrupo = (SwipeMenuListView) findViewById(R.id.listTG);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);

        //listaTipoGrupo = (ListView) findViewById(R.id.listTG);
        editNombreTP = (EditText) findViewById(R.id.editNombreTipoGrupo);

        // Llamar método para llenar lista
        llenarListaTipoGrupo(null);

        // Asociamos el menú contextual al listview
        registerForContextMenu(listaTipoGrupo);
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
        listaTipoGrupo .setMenuCreator(creator);
        listaTipoGrupo .setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuItem(index);
                TipoGrupo TGActual = (listaTipoGrupoAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(TGActual != null){
                            Intent intent = new Intent(getApplicationContext(), EditarTipoGrupo.class);
                            intent.putExtra("idtipogrupo", TGActual.getIdTipoGrupo());
                            intent.putExtra("nombretipogrupo", TGActual.getNombreTipoGrupo());
                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(TGActual != null){
                            if (TGActual.verificar(2, getApplicationContext())){
                                Toast.makeText(getApplicationContext(), "No se puede eliminar debido a dependecias del registro", Toast.LENGTH_SHORT).show();
                                return true;
                            }
                            String regEliminadas;
                            regEliminadas= TGActual.eliminar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                            llenarListaTipoGrupo(null);
                        }
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    // Método para llenar lista de días
    public void llenarListaTipoGrupo(String filtro){
        tipoGrupo = new TipoGrupo();
        List objetcts;

        if(filtro == null){
            objetcts = tipoGrupo.getAll(this);
        }
        else{
            objetcts = tipoGrupo.getAllFiltered(this,"nombretipogrupo", filtro);
        }


        // Inicializar el adaptador con la información a mostrar
        listaTipoGrupoAdapter = new TipoGrupoAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaTipoGrupo.setAdapter(listaTipoGrupoAdapter);
    }
    // Método para agregar un día
    public void agregarTipoGrupo(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoTipoGrupo.class);
        startActivity(intent);
    }
    public void buscarTipoGrupo(View v){
        //llenarListaTipoGrupo(editNombreTP.getText().toString());
        buscarTipoGrupo();
    }
    public void buscarTipoGrupo(){
        llenarListaTipoGrupo(editNombreTP.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaTipoGrupo(null);
    }
/*
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaTipoGrupoAdapter.getItem(info.position).getNombreTipoGrupo());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_tipo_grupo, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TipoGrupo TGActual = (listaTipoGrupoAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(TGActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarTipoGrupo.class);
                    intent.putExtra("idtipogrupo", TGActual.getIdTipoGrupo());
                    intent.putExtra("nombretipogrupo", TGActual.getNombreTipoGrupo());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(TGActual != null){
                    if (TGActual.verificar(2, getApplicationContext())){
                        Toast.makeText(this, "No se puede eliminar debido a dependecias del registro", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    String regEliminadas;
                    regEliminadas= TGActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaTipoGrupo(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
            editNombreTP.setText(results.get(0));
            buscarTipoGrupo();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editNombreTP.setText("");
        super.onPause();
    }
}

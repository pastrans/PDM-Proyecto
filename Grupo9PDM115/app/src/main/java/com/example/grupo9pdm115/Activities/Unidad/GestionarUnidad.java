package com.example.grupo9pdm115.Activities.Unidad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.grupo9pdm115.Adapters.UnidadAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.WebService.ControlServicio;

import java.util.ArrayList;
import java.util.List;

public class GestionarUnidad extends AppCompatActivity implements View.OnClickListener{
    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listUnidad;
    //ListView listUnidad;
    UnidadAdapter listaUnidadAdapter;
    Unidad unidad;
    private String urlPublicoUES = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Unidad/ws_unidad_list.php";
    private String urlPublicoUESEliminar = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Unidad/ws_unidad_eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IUN");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DUN");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EUN");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CUN"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_unidad);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // Inicializar elementos a manejar
        listUnidad = (SwipeMenuListView) findViewById(R.id.listUnidad);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //listUnidad = (ListView) findViewById(R.id.listUnidad);

        // Llamar método para llenar lista
        llenarListaUnidad(null);

        // Asociamos el menú contextual al listview
        registerForContextMenu(listUnidad);
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
        listUnidad.setMenuCreator(creator);
        listUnidad.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Unidad unidadActual = (listaUnidadAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(unidadActual != null){
                            Intent intent = new Intent(getApplicationContext(), EditarUnidad.class);
                            intent.putExtra("idunidad", unidadActual.getIdUnidad());
                            intent.putExtra("nombreent", unidadActual.getNombreent());
                            intent.putExtra("descripcionent", unidadActual.getDescripcionent());
                            //intent.putExtra("prioridad", unidadActual.getPrioridad());
                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(unidadActual != null){
                            String regEliminadas;
                            eliminarWeb(unidadActual.getIdUnidad());;
                            regEliminadas= unidadActual.eliminar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                            llenarListaUnidad(null);
                        }
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
    // Método para llenar lista de unidad
    public void llenarListaUnidad(String filtro){
        unidad = new Unidad();
        List objects;
        if(filtro == null){
            String unidadesExternas = ControlServicio.obtenerRespuestaPeticion(urlPublicoUES, this);
            objects = ControlServicio.obtenerUnidades(unidadesExternas, this);
        }else{
            String url = urlPublicoUES + "?unidad=" + filtro;
            String unidadesExternas = ControlServicio.obtenerRespuestaPeticion(url, this);
            objects = ControlServicio.obtenerUnidades(unidadesExternas, this);
        }
        //List objects = unidad.getAll(this);

        for(int i = 0; i < objects.size(); i++){
            Unidad u = (Unidad) objects.get(i);
            if(u.getNombreent().equals("Ninguna"))
                objects.remove(i);
        }

        // Inicializar el adaptador con la información a mostrar
        listaUnidadAdapter = new UnidadAdapter(this, objects);

        // Relacionando la lista con el adaptador y llenándola
        listUnidad.setAdapter(listaUnidadAdapter);
    }
    // Método para agregar una unidad
    public void agregarUnidad (View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoUnidad.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaUnidad(null);
    }
    /*
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaUnidadAdapter.getItem(info.position).getNombreent());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_dias, menu);//NO ES NECESARIO CREAR UNO NUEVO, POR QUE SE REPITE PARA TODOS (EL DISEÑO)
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Unidad unidadActual = (listaUnidadAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(unidadActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarUnidad.class);
                    intent.putExtra("idunidad", unidadActual.getIdUnidad());
                    intent.putExtra("nombreent", unidadActual.getNombreent());
                    intent.putExtra("descripcionent", unidadActual.getDescripcionent());
                    //intent.putExtra("prioridad", unidadActual.getPrioridad());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(unidadActual != null){
                    String regEliminadas;
                    eliminarWeb(unidadActual.getIdUnidad());;
                    regEliminadas= unidadActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaUnidad(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }*/

    public void eliminarWeb(int idUnidad){
        String url = null;
        url = urlPublicoUESEliminar + "?idUnidad=" + idUnidad;
        ControlServicio.sendRequest(url, this);
    }

    public void buscarUnidad(View v){
        //EditText editNombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        //llenarListaUnidad(editNombreUnidad.getText().toString());
        buscarUnidad();
    }
    public void buscarUnidad(){
        EditText editNombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        llenarListaUnidad(editNombreUnidad.getText().toString());
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
            EditText editNombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
            editNombreUnidad.setText(results.get(0));
            buscarUnidad();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        EditText editNombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        editNombreUnidad.setText("");
        super.onPause();
    }
}

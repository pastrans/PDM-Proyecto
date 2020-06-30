package com.example.grupo9pdm115.Activities.Materia;

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

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.MateriaAdapter;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.WebService.ControlServicio;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class GestionarMateria extends CyaneaAppCompatActivity implements View.OnClickListener {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaMaterias;
    //Declarando atributos para manejo del ListView
    //ListView listaMaterias;
    MateriaAdapter listaMateriasAdapter;
    Materia materia;
    EditText editnombreMateria;
    private MaterialDialog mSimpleDialog;

    //private String urlPublicoUES = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Materia/ws_materia_list.php";
    //private String urlPublicoUES = "http://192.168.0.17/LE17004/Proyecto/Materia/ws_materia_list.php";
    private String urlPublicoUESFiltro = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Materia/ws_materia_list_filter.php";
    private String urlPublicoUESEliminar = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Materia/ws_materia_eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IMA");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DMA");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EMA");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CMA"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_materia);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Inicializar elementos para llenar lista
        listaMaterias = (SwipeMenuListView) findViewById(R.id.listaMaterias);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //listaMaterias = (ListView) findViewById(R.id.listaMaterias);
        editnombreMateria = (EditText) findViewById(R.id.editNombreMateria);
        //Llamar método para llenar lista
        llenarListaMateria(null);

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaMaterias);
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
        listaMaterias.setMenuCreator(creator);
        listaMaterias.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final Materia materiaActual = (listaMateriasAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(materiaActual != null){
                            Intent intent = new Intent(getApplicationContext(), EditarMateria.class);
                            intent.putExtra("codMateria", materiaActual.getCodMateria());
                            intent.putExtra("idUnidad", materiaActual.getIdUnidad());
                            intent.putExtra("nombreMat", materiaActual.getNombreMateria());
                            intent.putExtra("masiva", materiaActual.isMasiva());

                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(materiaActual != null){
                            mSimpleDialog = new MaterialDialog.Builder(GestionarMateria.this)
                                    .setTitle("Eliminar")
                                    .setMessage("¿Está seguro de eliminar?")
                                    .setCancelable(false)
                                    .setPositiveButton("Eliminar", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                            String regEliminadas;
                            eliminarWeb(materiaActual.getCodMateria());
                            regEliminadas= materiaActual.eliminar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                            llenarListaMateria(null);
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

    //Metodo parra llenar lista de materias
    public void llenarListaMateria(String filtro){
        materia = new Materia();
        List objetcts = null;

        if (filtro == null) {
            String materiasExternas = ControlServicio.obtenerRespuestaPeticion(urlPublicoUESFiltro, this);
            objetcts = Materia.getAllFromJSON(materiasExternas, this);
            //objetcts = materia.getAll(this);
            //Inicializar el adaptador con la información a mostrar
        } else {
            String nuevo = urlPublicoUESFiltro + "?codmateria=" + filtro.trim();
            String materiasExternas = ControlServicio.obtenerRespuestaPeticion(nuevo, this);
            //Toast.makeText(this, urlPublicoUES, Toast.LENGTH_LONG).show();
            objetcts = Materia.getAllFromJSON(materiasExternas, this);
            //objetcts = materia.getAllFiltered(this, "codmateria", filtro);
        }

        listaMateriasAdapter = new MateriaAdapter(this, objetcts);
        // Relacionando la lista con el adaptador y llenándola
        listaMaterias.setAdapter(listaMateriasAdapter);
    }

    //Metodo para agregar materia
    public void btnNuevoMateria(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoMateria.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaMateria(null);
    }

    /*
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaMateriasAdapter.getItem(info.position).getNombreMateria());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_materia, menu);
    }*/

    public void buscarMateria(View v) {
        //llenarListaMateria(editnombreMateria.getText().toString());
        buscarMateria();
    }
    public void buscarMateria() {
        llenarListaMateria(editnombreMateria.getText().toString());
    }
    public void llenarListaCicloMateria(String filtro) {
        materia = new Materia();
        List objects;

        if (filtro == null) {
            objects = materia.getAll(this);
        } else {
            objects = materia.getAllFiltered(this, "nombremat", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaMateriasAdapter = new MateriaAdapter(this, objects);

        // Relacionando la lista con el adaptador y llenándola
        listaMaterias.setAdapter(listaMateriasAdapter);
    }
/*
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Materia materiaActual = (listaMateriasAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarMateria:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(materiaActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarMateria.class);
                    intent.putExtra("codMateria", materiaActual.getCodMateria());
                    intent.putExtra("idUnidad", materiaActual.getIdUnidad());
                    intent.putExtra("nombreMat", materiaActual.getNombreMateria());
                    intent.putExtra("masiva", materiaActual.isMasiva());

                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarMateria:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(materiaActual != null){
                    String regEliminadas;
                    eliminarWeb(materiaActual.getCodMateria());
                    regEliminadas= materiaActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaMateria(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }*/

    public void eliminarWeb(String codMateria){
        String url = null;
        url = urlPublicoUESEliminar + "?codmateria=" + codMateria;
        ControlServicio.sendRequest(url, this);
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
            editnombreMateria.setText(results.get(0));
            buscarMateria();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editnombreMateria.setText("");
        super.onPause();
    }

}

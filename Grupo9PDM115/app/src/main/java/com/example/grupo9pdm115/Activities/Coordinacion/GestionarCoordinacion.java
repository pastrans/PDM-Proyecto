package com.example.grupo9pdm115.Activities.Coordinacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.example.grupo9pdm115.Activities.Materia.EditarMateria;
import com.example.grupo9pdm115.Activities.Materia.NuevoMateria;
import com.example.grupo9pdm115.Adapters.CoordinacionAdapter;
import com.example.grupo9pdm115.Adapters.MateriaAdapter;
import com.example.grupo9pdm115.Modelos.Coordinacion;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class GestionarCoordinacion extends AppCompatActivity implements View.OnClickListener {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check = 1111;
    SwipeMenuListView listaCoordinacion;
    //Declarando atributos para manejo del ListView
    //ListView listaCoordinacion;
    CoordinacionAdapter listaCoordinacionAdapter;
    EditText editNombreUsuario;
    Coordinacion coordinacion;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "ICO");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DCO");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ECO");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CCO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_coordinacion);

        //Inicializar elementos para llenar lista
        listaCoordinacion = (SwipeMenuListView) findViewById(R.id.listacoordinacion);
        //listaCoordinacion = (ListView) findViewById(R.id.listacoordinacion);
        editNombreUsuario = (EditText) findViewById(R.id.editNombreCoordinador);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);

        //Llamar método para llenar lista
        llenarListaCoordinacion("");

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaCoordinacion);
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
        listaCoordinacion.setMenuCreator(creator);
        listaCoordinacion.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuItem(index);
                final Coordinacion coorActual = (listaCoordinacionAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(coorActual != null){
                            Intent intent = new Intent(getApplicationContext(), EditarCoordinacion.class);
                            intent.putExtra("idcoordinacion", coorActual.getIdCoodinacion());
                            intent.putExtra("idusuario", coorActual.getIdUsuario());
                            intent.putExtra("idciclomateria", coorActual.getIdCicloMateria());
                            intent.putExtra("tipocoordinacion", coorActual.getTipoCoordinacion());
                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(coorActual != null){
                            mSimpleDialog = new MaterialDialog.Builder( GestionarCoordinacion.this)
                                .setTitle("Eliminar")
                                .setMessage("¿Está seguro de eliminar?")
                                .setCancelable(false)
                                .setPositiveButton("Eliminar", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    String regEliminadas;
                                    regEliminadas= coorActual.eliminar(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                                    llenarListaCoordinacion("");
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
    public void llenarListaCoordinacion(String filtro) {
        coordinacion = new Coordinacion();
        List objetcts;

        if (filtro.equals(null) ) {
            objetcts = coordinacion.getAll(this);
        } else {
            objetcts = coordinacion.getAllFiltered1(this, filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaCoordinacionAdapter = new CoordinacionAdapter(this, objetcts);


        // Relacionando la lista con el adaptador y llenándola
        listaCoordinacion.setAdapter(listaCoordinacionAdapter);

    }

    //Metodo para agregar materia
    public void btnNuevoCoodinacion(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoCoordinacion.class);
        startActivity(intent);
    }

    public void buscarCoordinador(View v) {
        //llenarListaCoordinacion(editNombreUsuario.getText().toString());
        buscarCoordinador();
    }
    public void buscarCoordinador(){
        llenarListaCoordinacion(editNombreUsuario.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaCoordinacion("");
    }

    // Para menú contextual
    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        Usuario usu = new Usuario();
        usu.consultar(this, listaCoordinacionAdapter.getItem(info.position).getIdUsuario());
        menu.setHeaderTitle(usu.getNombrePersonal());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctc_lista_coordinacion, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Coordinacion coorActual = (listaCoordinacionAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarCoordinacion:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(coorActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarCoordinacion.class);
                    intent.putExtra("idcoordinacion", coorActual.getIdCoodinacion());
                    intent.putExtra("idusuario", coorActual.getIdUsuario());
                    intent.putExtra("idciclomateria", coorActual.getIdCicloMateria());
                    intent.putExtra("tipocoordinacion", coorActual.getTipoCoordinacion());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarCoordinacion:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(coorActual != null){
                    String regEliminadas;
                    regEliminadas= coorActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaCoordinacion("");
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
            editNombreUsuario.setText(results.get(0));
            buscarCoordinador();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editNombreUsuario.setText("");
        super.onPause();
    }

}

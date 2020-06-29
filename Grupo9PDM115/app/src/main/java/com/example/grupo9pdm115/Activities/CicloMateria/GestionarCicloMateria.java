package com.example.grupo9pdm115.Activities.CicloMateria;

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
import com.example.grupo9pdm115.Adapters.CicloMateriaAdapter;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class GestionarCicloMateria  extends AppCompatActivity implements View.OnClickListener {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaCicloMaterias;
    //Declarando atributos para manejo del ListView
    //ListView listaCicloMaterias;
    EditText editCodigoMateria;
    CicloMateriaAdapter listaMateriaAdapter;
    CicloMateria cicloMateria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "ICM");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DCM");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ECM");
        // Validando usuario y sesión
        if ((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CCM"))
                || !Sesion.getLoggedIn(getApplicationContext())) {
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ciclo_materia);
        editCodigoMateria = (EditText) findViewById(R.id.editcodmateria);

        //Inicializar elementos para llenar lista
        listaCicloMaterias = (SwipeMenuListView) findViewById(R.id.listaCicloMaterias);
        //listaCicloMaterias = (ListView) findViewById(R.id.listaCicloMaterias);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);

        //Llamar método para llenar lista
        llenarListaCicloMateria(null);


        //Asociamos el menú contextual al listview
        registerForContextMenu(listaCicloMaterias);
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
        listaCicloMaterias.setMenuCreator(creator);
        listaCicloMaterias.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                CicloMateria cmActual = (listaMateriaAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (cmActual != null) {
                            Intent intent = new Intent(getApplicationContext(), EditarCicloMateria.class);
                            intent.putExtra("idciclomateria", cmActual.getIdCicloMateria());
                            intent.putExtra("codmateria", cmActual.getCodMateria());
                            intent.putExtra("idciclo", cmActual.getIdCiclo());
                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (cmActual != null) {
                            String regEliminadas;
                            regEliminadas = cmActual.eliminar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                            llenarListaCicloMateria(null);
                        }
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    //Metodo parra llenar lista de ciclos
    public void llenarListaCicloMateria(String filtro) {
        cicloMateria = new CicloMateria();
        List objetcts;

        if (filtro == null) {
            objetcts = cicloMateria.getAll(this);
        } else {
            objetcts = cicloMateria.getAllFiltered(this, "codmateria", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaMateriaAdapter = new CicloMateriaAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaCicloMaterias.setAdapter(listaMateriaAdapter);
    }

    //Metodo para agregar ciclo
    public void agregarCicloMateria(View v) {
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoCicloMateria.class);
        startActivity(intent);
    }

    public void buscarMateria(View v) {
        //llenarListaCicloMateria(editCodigoMateria.getText().toString());
        buscarMateria();
    }
    public void buscarMateria(){
        llenarListaCicloMateria(editCodigoMateria.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaCicloMateria(null);
    }
/*
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // Modificable
        menu.setHeaderTitle(listaMateriaAdapter.getItem(info.position).getCodMateria());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctc_lista_ciclo_materia, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        CicloMateria cmActual = (listaMateriaAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarCicloMateria:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (cmActual != null) {
                    Intent intent = new Intent(getApplicationContext(), EditarCicloMateria.class);
                    intent.putExtra("idciclomateria", cmActual.getIdCicloMateria());
                    intent.putExtra("codmateria", cmActual.getCodMateria());
                    intent.putExtra("idciclo", cmActual.getIdCiclo());
                    startActivity(intent);
                }
                return true;

            case R.id.ctxEliminarCicloMateria:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (cmActual != null) {
                    String regEliminadas;
                    regEliminadas = cmActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaCicloMateria(null);
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
            editCodigoMateria.setText(results.get(0));
            buscarMateria();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
    editCodigoMateria.setText("");
    super.onPause();
    }

}

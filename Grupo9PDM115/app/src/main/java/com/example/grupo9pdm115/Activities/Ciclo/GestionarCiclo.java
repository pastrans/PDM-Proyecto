package com.example.grupo9pdm115.Activities.Ciclo;

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
import com.example.grupo9pdm115.Adapters.CicloAdapter;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GestionarCiclo extends CyaneaAppCompatActivity implements View.OnClickListener {
    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    //Librerias
    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaCiclos;
    //Declarando atributos para manejo del ListView
    //ListView listaCiclos;
    EditText editNombreCiclo;
    CicloAdapter listaCiclosAdapter;
    Ciclo ciclo;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "ICL");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DCL");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "ECL");

        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CCL"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ciclo);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);

        //Inicializar elementos para llenar lista
        //listaCiclos = (ListView) findViewById(R.id.listaCiclos);
        listaCiclos = (SwipeMenuListView) findViewById(R.id.listaCiclos);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //Llamar método para llenar lista
        llenarListaCiclo(null);

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaCiclos);
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

                // create "activo" item
                SwipeMenuItem activoItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                activoItem.setWidth(170);
                // set a icon
                activoItem.setIcon(R.drawable.ic_activate);
                // add to menu
                menu.addMenuItem(activoItem);

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
        listaCiclos.setMenuCreator(creator);
        listaCiclos.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                final Ciclo cicloActual = (listaCiclosAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(cicloActual != null){
                            Intent intent = new Intent(getApplicationContext(), EditarCiclo.class);
                            intent.putExtra("idciclo", cicloActual.getIdCiclo());
                            intent.putExtra("nombreciclo", cicloActual.getNombreCiclo());
                            intent.putExtra("iniciociclo", cicloActual.getInicioToLocal());
                            intent.putExtra("finciclo", cicloActual.getFinToLocal());
                            intent.putExtra("estadociclo", Boolean.toString(cicloActual.isEstadoCiclo()));
                            intent.putExtra("inicioclases", cicloActual.getInicioPeriodoClaseToLocal());
                            intent.putExtra("finclases", cicloActual.getFinPeriodoClaseToLocal());
                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(cicloActual != null){
                            String mensaje = "";
                            int resultado = cicloActual.activarCiclo(getApplicationContext());
                            if(resultado == -1)
                                mensaje =  getApplicationContext().getString(R.string.mnjCicloYaActivo); // "El ciclo ya se encuentra activo.";
                            else if(resultado == 0)
                                mensaje = getApplicationContext().getString(R.string.mnjCicloNoExiste); // "El ciclo no existe.";
                            else
                                mensaje = getApplicationContext().getString(R.string.mnjNuevoCicloActivo) + cicloActual.getNombreCiclo();; // "Nuevo ciclo activo: " + cicloActual.getNombreCiclo();

                            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                            llenarListaCiclo(null);
                        }
                        return true;
                    case 2:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(cicloActual != null){
                            mSimpleDialog = new MaterialDialog.Builder(GestionarCiclo.this)
                                .setTitle("Eliminar")
                                .setMessage("¿Está seguro de eliminar?")
                                .setCancelable(false)
                                .setPositiveButton("Eliminar", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String regEliminadas;
                                        // Si es ciclo activo no permitir eliminar
                                        if(cicloActual.isEstadoCiclo()){
                                            regEliminadas = getApplicationContext().getString(R.string.mnjNoElimCicloActivo); // "No es posible eliminar un ciclo activo.";
                                        }
                                        else{
                                            regEliminadas= cicloActual.eliminar(getApplicationContext());
                                        }
                                        Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                                        llenarListaCiclo(null);
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

    //Metodo parra llenar lista de ciclos
    public void llenarListaCiclo(String filtro){
        ciclo = new Ciclo();
        List objetcts;

        if(filtro == null){
            objetcts = ciclo.getAll(this);
        }
        else{
            objetcts = ciclo.getAllFiltered(this,"nombreciclo", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaCiclosAdapter = new CicloAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaCiclos.setAdapter(listaCiclosAdapter);
    }

    // Método para agregar ciclo
    public void agregarCiclo(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoCiclo.class);
        startActivity(intent);
    }

    // Método para buscar ciclo filtrado
    public void buscarCiclo(View v){
        //llenarListaCiclo(editNombreCiclo.getText().toString());
        buscarCiclo();
    }
    public void buscarCiclo(){
        llenarListaCiclo(editNombreCiclo.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaCiclo(null);
    }
/*
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaCiclosAdapter.getItem(info.position).getNombreCiclo());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_ciclo, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Ciclo cicloActual = (listaCiclosAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarCiclo:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(cicloActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarCiclo.class);
                    intent.putExtra("idciclo", cicloActual.getIdCiclo());
                    intent.putExtra("nombreciclo", cicloActual.getNombreCiclo());
                    intent.putExtra("iniciociclo", cicloActual.getInicioToLocal());
                    intent.putExtra("finciclo", cicloActual.getFinToLocal());
                    intent.putExtra("estadociclo", Boolean.toString(cicloActual.isEstadoCiclo()));
                    intent.putExtra("inicioclases", cicloActual.getInicioPeriodoClaseToLocal());
                    intent.putExtra("finclases", cicloActual.getFinPeriodoClaseToLocal());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxActivarCiclo:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(cicloActual != null){
                    String mensaje = "";
                    int resultado = cicloActual.activarCiclo(getApplicationContext());
                    if(resultado == -1)
                        mensaje =  this.getString(R.string.mnjCicloYaActivo); // "El ciclo ya se encuentra activo.";
                    else if(resultado == 0)
                        mensaje = this.getString(R.string.mnjCicloNoExiste); // "El ciclo no existe.";
                    else
                        mensaje = this.getString(R.string.mnjNuevoCicloActivo) + cicloActual.getNombreCiclo();; // "Nuevo ciclo activo: " + cicloActual.getNombreCiclo();

                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    llenarListaCiclo(null);
                }
                return true;
            case R.id.ctxEliminarCiclo:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(cicloActual != null){
                    String regEliminadas;
                    // Si es ciclo activo no permitir eliminar
                    if(cicloActual.isEstadoCiclo()){
                        regEliminadas = this.getString(R.string.mnjNoElimCicloActivo); // "No es posible eliminar un ciclo activo.";
                    }
                    else{
                        regEliminadas= cicloActual.eliminar(getApplicationContext());
                    }
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaCiclo(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
            buscarCiclo();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editNombreCiclo.setText("");
        super.onPause();
    }

}

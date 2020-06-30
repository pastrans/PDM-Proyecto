package com.example.grupo9pdm115.Activities.Feriado;

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
import com.example.grupo9pdm115.Activities.Ciclo.GestionarCiclo;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.FeriadoAdapter;
import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class GestionarFeriado extends AppCompatActivity implements View.OnClickListener{
    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaFeriados;
    //Declarando atributos para el manejo del listview
    //ListView listaFeriados;
    EditText editNombreFeriado;
    FeriadoAdapter listaFeriadosAdapter;
    Feriado feriado;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IFE");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DFE");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EFE");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CFE"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_feriado);
        editNombreFeriado = (EditText) findViewById(R.id.editNombreFeriado);

        //Iniciar elementos para llenar lista
        //listaFeriados = (ListView) findViewById(R.id.listaFeriados);
        listaFeriados = (SwipeMenuListView) findViewById(R.id.listaFeriados);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);

        //Llamar método para llenar lista
        llenarListaFeriado(null);

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaFeriados);
    }

    //Metodo parra llenar lista de feriado
    public void llenarListaFeriado(String filtro){
        feriado = new Feriado();
        List objetcts;

        if(filtro == null){
            objetcts = feriado.getAll(this);
        }
        else{
            objetcts = feriado.getAllFiltered(this,"nombreferiado", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaFeriadosAdapter = new FeriadoAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaFeriados.setAdapter(listaFeriadosAdapter);
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
        listaFeriados.setMenuCreator(creator);
        listaFeriados.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final Feriado feriadoActual = (listaFeriadosAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(feriadoActual != null){
                            Intent intent = new Intent(getApplicationContext(), EditarFeriado.class);
                            intent.putExtra("idferiado", feriadoActual.getIdFeriado());
                            intent.putExtra("idciclo", feriadoActual.getIdCiclo());
                            intent.putExtra("inicioferiado", feriadoActual.getFechaInicioFeriadoToLocal());
                            intent.putExtra("finferiado", feriadoActual.getFechaFinFeriadoToLocal());
                            intent.putExtra("nombreferiado", feriadoActual.getNombreFeriado());
                            intent.putExtra("descripcionferiado", feriadoActual.getDescripcionFeriado());
                            intent.putExtra("bloquearreservas", feriadoActual.isBloquearReservas());
                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(feriadoActual != null){
                            mSimpleDialog = new MaterialDialog.Builder( GestionarFeriado.this)
                                .setTitle("Eliminar")
                                .setMessage("¿Está seguro de eliminar?")
                                .setCancelable(false)
                                .setPositiveButton("Eliminar", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String regEliminadas;
                                        regEliminadas= feriadoActual.eliminar(getApplicationContext());
                                        Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                                        llenarListaFeriado(null);
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

    //Metodo para agregar Feriado
    public void agregarFeriado(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent inte = new Intent(this, NuevoFeriado.class);
        startActivity(inte);
    }

    // Método para buscar feriado filtrado
    public void buscarFeriado(View v){
        //llenarListaFeriado(editNombreFeriado.getText().toString());
        buscarFeriado();
    }
    public void buscarFeriado(){
        llenarListaFeriado(editNombreFeriado.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaFeriado(null);
    }
/*
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaFeriadosAdapter.getItem(info.position).getNombreFeriado());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_feriado, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Feriado feriadoActual = (listaFeriadosAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarFeriado:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(feriadoActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarFeriado.class);
                    intent.putExtra("idferiado", feriadoActual.getIdFeriado());
                    intent.putExtra("idciclo", feriadoActual.getIdCiclo());
                    intent.putExtra("inicioferiado", feriadoActual.getFechaInicioFeriadoToLocal());
                    intent.putExtra("finferiado", feriadoActual.getFechaFinFeriadoToLocal());
                    intent.putExtra("nombreferiado", feriadoActual.getNombreFeriado());
                    intent.putExtra("descripcionferiado", feriadoActual.getDescripcionFeriado());
                    intent.putExtra("bloquearreservas", feriadoActual.isBloquearReservas());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarFeriado:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(feriadoActual != null){
                    String regEliminadas;
                    regEliminadas= feriadoActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaFeriado(null);
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
            editNombreFeriado.setText(results.get(0));
            buscarFeriado();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onPause(){
        editNombreFeriado.setText("");
        super.onPause();
    }
    public void onDestroy(){ super.onDestroy(); }
}

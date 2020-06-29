package com.example.grupo9pdm115.Activities.Horario;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.CicloAdapter;
import com.example.grupo9pdm115.Adapters.HorarioAdapter;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GestionarHorario extends AppCompatActivity implements View.OnClickListener {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listHorario;
    //ListView listHorario;
    EditText editNombreCiclo;
    Horario horario;
    HorarioAdapter listaHorarioAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IHO");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DHO");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EHO");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CHO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_horario);
        //Toast.makeText(getApplicationContext(), "mostrar", Toast.LENGTH_SHORT).show();
        // Inicializar elementos a manejar
        listHorario = (SwipeMenuListView) findViewById(R.id.listHorario);
        //listHorario = (ListView) findViewById(R.id.listHorario);
        Voice=(Button) findViewById(R.id.bvoice);

        //Voice.setOnClickListener(this);
        editNombreCiclo = (EditText) findViewById(R.id.editNombreCiclo);
        Voice.setOnClickListener(this);

        // Llamar método para llenar lista
        llenarListaHorario(null);

        // Asociamos el menú contextual al listview
        registerForContextMenu(listHorario);

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
        listHorario.setMenuCreator(creator);
        listHorario.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuItem(index);
                Horario horario = (listaHorarioAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(horario != null){

                            Intent intent = new Intent(getApplicationContext(), EditarHorario.class);
                            intent.putExtra("idhorario", horario.getIdHora());
                            intent.putExtra("horainicio", horario.getHoraInicio());
                            intent.putExtra("horafinal", horario.getHoraFinal());
                            startActivity(intent);
                        }
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if(horario != null){
                            String regEliminadas;
                            regEliminadas= horario.eliminar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                            llenarListaHorario(null);
                        }
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    // Método para llenar lista de unidad
    public void llenarListaHorario(String filtro){
        horario = new Horario();
        List objetcts;

        if(filtro == null){
            objetcts = horario.getAll(this);
        }
        else{
            objetcts = horario.getAllFiltered1(this,filtro);
        }

        //Inicializar el adaptador con la información a mostrar

        listaHorarioAdapter = new HorarioAdapter(this, objetcts);
        listHorario.setAdapter(listaHorarioAdapter);
    }
    public void buscarhorario(View v){
        //llenarListaHorario(editNombreCiclo.getText().toString());
        buscarhorario();
    }
    public void buscarhorario(){
        llenarListaHorario(editNombreCiclo.getText().toString());
    }
    // Método para agregar una unidad
    public void nuevoHorario (View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoHorario.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaHorario(null);
    }
    /*
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaHorarioAdapter.getItem(info.position).getHoraInicio());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_dias, menu);//NO ES NECESARIO CREAR UNO NUEVO, POR QUE SE REPITE PARA TODOS (EL DISEÑO)
    }
   // @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Horario horario = (listaHorarioAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(horario != null){
                    Intent intent = new Intent(getApplicationContext(), EditarHorario.class);
                    intent.putExtra("idhorario", horario.getIdHora());
                    intent.putExtra("horainicio", horario.getHoraInicio());
                    intent.putExtra("horafinal", horario.getHoraFinal());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(horario != null){
                    String regEliminadas;
                    regEliminadas= horario.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaHorario(null);
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
            buscarhorario();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editNombreCiclo.setText("");
        super.onPause();
    }

}

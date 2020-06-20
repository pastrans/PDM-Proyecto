package com.example.grupo9pdm115.Activities.Dia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.DiaAdapter;
import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarDia extends AppCompatActivity {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    // Declarando atributos para manejo del ListView
    ListView listaDias;
    DiaAdapter listaDiasAdapter;
    Dia dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IDI");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DDI");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EDI");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CDI"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_dia);

        // Inicializar elementos a manejar
        listaDias = (ListView) findViewById(R.id.listDias);

        // Llamar método para llenar lista
        llenarListaDia();

        // Asociamos el menú contextual al listview
        registerForContextMenu(listaDias);
    }

    // Método para llenar lista de días
    public void llenarListaDia(){
        dia = new Dia();
        List objects = dia.getAll(this);
        // Inicializar el adaptador con la información a mostrar
        listaDiasAdapter = new DiaAdapter(this, objects);

        // Relacionando la lista con el adaptador y llenándola
        listaDias.setAdapter(listaDiasAdapter);
    }

    // Método para agregar un día
    public void agregarDia(View v){
        Intent intent = new Intent(this, NuevoDia.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaDia();
    }

    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaDiasAdapter.getItem(info.position).getNombreDia());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_dias, menu);
    }
    // Operaciones a realizar al seleccionar del menú contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Dia diaActual = (listaDiasAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(diaActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarDia.class);
                    intent.putExtra("iddia", diaActual.getIdDia());
                    intent.putExtra("nombredia", diaActual.getNombreDia());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(diaActual != null){
                    String regEliminadas;
                    regEliminadas= diaActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaDia();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

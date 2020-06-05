package com.example.grupo9pdm115;

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

import com.example.grupo9pdm115.Adapters.DiaAdapter;
import com.example.grupo9pdm115.Modelos.Dia;

import java.util.List;

public class DiaGestionar extends Activity {
    // public static final int ACTIVITY_GESTIONAR_DIA = 1;

    // Declarando atributos para manejo del ListView
    ListView listaDias;
    DiaAdapter listaDiasAdapter;
    Dia dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario
/*
        if(true){ // !usuario.tieneAcceso(ACTIVITY_GESTIONAR_DIA)
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
            finish();
        }*/

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
        Intent intent = new Intent(this, DiaInsertar.class);
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
                    Intent intent = new Intent(getApplicationContext(), DiaActualizar.class);
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

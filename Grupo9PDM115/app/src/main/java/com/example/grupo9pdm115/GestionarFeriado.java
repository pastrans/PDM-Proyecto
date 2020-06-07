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

import com.example.grupo9pdm115.Adapters.FeriadoAdapter;
import com.example.grupo9pdm115.Modelos.Feriado;

import java.util.List;

public class GestionarFeriado extends Activity {
    //Declarando atributos para el manejo del listview
    ListView listaFeriados;
    FeriadoAdapter listaFeriadosAdapter;
    Feriado feriado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_feriado);

        //Iniciar elementos para llenar lista
        listaFeriados = (ListView) findViewById(R.id.listaFeriados);

        //Llamar método para llenar lista
        llenarListaFeriado();

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaFeriados);
    }
    //Metodo parra llenar lista de ciclos
    public void llenarListaFeriado(){
        feriado = new Feriado();
        List objetcts = feriado.getAll(this);

        //Inicializar el adaptador con la información a mostrar
        listaFeriadosAdapter = new FeriadoAdapter(this, objetcts);

        // Relacionando la lista con el adaptador y llenándola
        listaFeriados.setAdapter(listaFeriadosAdapter);
    }
    //Metodo para agregar Feriado
    public void btnNuevoFeriado(View v){
        Intent inte = new Intent(this, NuevoFeriado.class);
        startActivity(inte);
    }
    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaFeriado();
    }
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
                if(feriadoActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarFeriado.class);
                    intent.putExtra("idferiado", feriadoActual.getIdFeriado());
                    intent.putExtra("idciclo", feriadoActual.getIdCiclo());
                    intent.putExtra("inicioferiado", feriadoActual.getFechaInicioFeriado());
                    intent.putExtra("finferiado", feriadoActual.getFechaFinFeriado());
                    intent.putExtra("nombreferiado", feriadoActual.getNombreFeriado());
                    intent.putExtra("descripcionferiado", feriadoActual.getDescripcionFeriado());
                    intent.putExtra("bloquearreservas", feriadoActual.isBloquearReservas());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarFeriado:
                if(feriadoActual != null){
                    String regEliminadas;
                    regEliminadas= feriadoActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaFeriado();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

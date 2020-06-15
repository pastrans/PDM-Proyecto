package com.example.grupo9pdm115.Activities.Feriado;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.FeriadoAdapter;
import com.example.grupo9pdm115.Modelos.Feriado;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarFeriado extends AppCompatActivity {
    //Declarando atributos para el manejo del listview
    ListView listaFeriados;
    EditText editNombreFeriado;
    FeriadoAdapter listaFeriadosAdapter;
    Feriado feriado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        listaFeriados = (ListView) findViewById(R.id.listaFeriados);

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
    }

    //Metodo para agregar Feriado
    public void agregarFeriado(View v){
        Intent inte = new Intent(this, NuevoFeriado.class);
        startActivity(inte);
    }

    // Método para buscar feriado filtrado
    public void buscarFeriado(View v){
        llenarListaFeriado(editNombreFeriado.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaFeriado(null);
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
                    intent.putExtra("inicioferiado", feriadoActual.getFechaInicioFeriadoToLocal());
                    intent.putExtra("finferiado", feriadoActual.getFechaFinFeriadoToLocal());
                    intent.putExtra("nombreferiado", feriadoActual.getNombreFeriado());
                    intent.putExtra("descripcionferiado", feriadoActual.getDescripcionFeriado());
                    intent.putExtra("bloquearreservas", Boolean.toString(feriadoActual.isBloquearReservas()));
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarFeriado:
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
    }
}

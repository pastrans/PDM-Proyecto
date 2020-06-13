package com.example.grupo9pdm115.Activities.Ciclo;

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
import com.example.grupo9pdm115.Adapters.CicloAdapter;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

import java.util.List;


public class GestionarCiclo extends AppCompatActivity {
    //Declarando atributos para manejo del ListView
    ListView listaCiclos;
    EditText editNombreCiclo;
    CicloAdapter listaCiclosAdapter;
    Ciclo ciclo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        listaCiclos = (ListView) findViewById(R.id.listaCiclos);

        //Llamar método para llenar lista
        llenarListaCiclo(null);


        //Asociamos el menú contextual al listview
        registerForContextMenu(listaCiclos);
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

    //Metodo para agregar ciclo
    public void agregarCiclo(View v){
        Intent intent = new Intent(this, NuevoCiclo.class);
        startActivity(intent);
    }

    public void buscarCiclo(View v){
        llenarListaCiclo(editNombreCiclo.getText().toString());
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaCiclo(null);
    }

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
                if(cicloActual != null){
                    String mensaje = "";
                    int resultado = cicloActual.activarCiclo(getApplicationContext());
                    if(resultado == -1)
                        mensaje = "El ciclo ya se encuentra activo.";
                    else if(resultado == 0)
                        mensaje = "El ciclo no existe.";
                    else
                        mensaje = "Nuevo ciclo activo: " + cicloActual.getNombreCiclo();

                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    llenarListaCiclo(null);
                }
                return true;
            case R.id.ctxEliminarCiclo:
                if(cicloActual != null){
                    String regEliminadas;
                    regEliminadas= cicloActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaCiclo(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

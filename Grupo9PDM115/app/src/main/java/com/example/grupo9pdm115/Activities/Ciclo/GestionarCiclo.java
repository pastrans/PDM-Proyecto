package com.example.grupo9pdm115.Activities.Ciclo;

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

import com.example.grupo9pdm115.Adapters.CicloAdapter;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.R;

import java.util.List;


public class GestionarCiclo extends Activity {
    //Declarando atributos para manejo del ListView
    ListView listaCiclos;
    CicloAdapter listaCiclosAdapter;
    Ciclo ciclo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ciclo);

        //Inicializar elementos para llenar lista
        listaCiclos = (ListView) findViewById(R.id.listaCiclos);

        //Llamar método para llenar lista
        llenarListaCiclo();

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaCiclos);
    }

    //Metodo parra llenar lista de ciclos
    public void llenarListaCiclo(){
        ciclo = new Ciclo();
        List objetcts = ciclo.getAll(this);

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

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaCiclo();
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
                    intent.putExtra("iniciociclo", cicloActual.getInicio());
                    intent.putExtra("finciclo", cicloActual.getFin());
                    intent.putExtra("estadociclo", Boolean.toString(cicloActual.isEstadoCiclo()));
                    intent.putExtra("inicioclases", cicloActual.getInicioPeriodoClase());
                    intent.putExtra("finclases", cicloActual.getFinPeriodoClase());
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
                    llenarListaCiclo();
                }
                return true;
            case R.id.ctxEliminarCiclo:
                if(cicloActual != null){
                    String regEliminadas;
                    regEliminadas= cicloActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaCiclo();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
//Falta agregar la lista para mostrar los datos
// Link para tablas dinámicas
//https://programaressencillo.wordpress.com/2014/11/22/android-tablas-dinamicas-en-android/
// Link para ListView
//http://www.hermosaprogramacion.com/2014/10/android-listas-adaptadores/

package com.example.grupo9pdm115.Activities.Unidad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.grupo9pdm115.Adapters.UnidadAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.WebService.ControlServicio;

import java.util.List;

public class GestionarUnidad extends AppCompatActivity {
    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;
    ListView listUnidad;
    UnidadAdapter listaUnidadAdapter;
    Unidad unidad;
    private String urlPublicoUES = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Unidad/ws_unidad_list.php";
    private String urlPublicoUESEliminar = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Unidad/ws_unidad_eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IUN");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DUN");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EUN");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CUN"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_unidad);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // Inicializar elementos a manejar
        listUnidad = (ListView) findViewById(R.id.listUnidad);

        // Llamar método para llenar lista
        llenarListaUnidad(null);

        // Asociamos el menú contextual al listview
        registerForContextMenu(listUnidad);
    }
    // Método para llenar lista de unidad
    public void llenarListaUnidad(String filtro){
        unidad = new Unidad();
        List objects;
        if(filtro == null){
            String unidadesExternas = ControlServicio.obtenerRespuestaPeticion(urlPublicoUES, this);
            objects = ControlServicio.obtenerUnidades(unidadesExternas, this);
        }else{
            String url = urlPublicoUES + "?unidad=" + filtro;
            String unidadesExternas = ControlServicio.obtenerRespuestaPeticion(url, this);
            objects = ControlServicio.obtenerUnidades(unidadesExternas, this);
        }
        //List objects = unidad.getAll(this);

        for(int i = 0; i < objects.size(); i++){
            Unidad u = (Unidad) objects.get(i);
            if(u.getNombreent().equals("Ninguna"))
                objects.remove(i);
        }

        // Inicializar el adaptador con la información a mostrar
        listaUnidadAdapter = new UnidadAdapter(this, objects);

        // Relacionando la lista con el adaptador y llenándola
        listUnidad.setAdapter(listaUnidadAdapter);
    }
    // Método para agregar una unidad
    public void agregarUnidad (View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoUnidad.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaUnidad(null);
    }
    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaUnidadAdapter.getItem(info.position).getNombreent());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_dias, menu);//NO ES NECESARIO CREAR UNO NUEVO, POR QUE SE REPITE PARA TODOS (EL DISEÑO)
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Unidad unidadActual = (listaUnidadAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(unidadActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarUnidad.class);
                    intent.putExtra("idunidad", unidadActual.getIdUnidad());
                    intent.putExtra("nombreent", unidadActual.getNombreent());
                    intent.putExtra("descripcionent", unidadActual.getDescripcionent());
                    //intent.putExtra("prioridad", unidadActual.getPrioridad());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(unidadActual != null){
                    String regEliminadas;
                    eliminarWeb(unidadActual.getIdUnidad());;
                    regEliminadas= unidadActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaUnidad(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void eliminarWeb(int idUnidad){
        String url = null;
        url = urlPublicoUESEliminar + "?idUnidad=" + idUnidad;
        ControlServicio.sendRequest(url, this);
    }

    public void buscarUnidad(View v){
        EditText editNombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        llenarListaUnidad(editNombreUnidad.getText().toString());
    }

}

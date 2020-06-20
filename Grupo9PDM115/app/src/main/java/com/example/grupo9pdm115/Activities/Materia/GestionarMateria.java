package com.example.grupo9pdm115.Activities.Materia;

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
import com.example.grupo9pdm115.Adapters.MateriaAdapter;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.WebService.ControlServicio;

import java.util.ArrayList;
import java.util.List;

public class GestionarMateria extends AppCompatActivity {

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;

    //Declarando atributos para manejo del ListView
    ListView listaMaterias;
    MateriaAdapter listaMateriasAdapter;
    Materia materia;
    EditText editnombreMateria;

    //private String urlPublicoUES = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Materia/ws_materia_list.php";
    //private String urlPublicoUES = "http://192.168.0.17/LE17004/Proyecto/Materia/ws_materia_list.php";
    private String urlPublicoUESFiltro = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Materia/ws_materia_list_filter.php";
    private String urlPublicoUESEliminar = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Materia/ws_materia_eliminar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IMA");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DMA");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EMA");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CMA"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_materia);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //Inicializar elementos para llenar lista
        listaMaterias = (ListView) findViewById(R.id.listaMaterias);
        editnombreMateria = (EditText) findViewById(R.id.editNombreMateria);
        //Llamar método para llenar lista
        llenarListaMateria(null);

        //Asociamos el menú contextual al listview
        registerForContextMenu(listaMaterias);
    }

    //Metodo parra llenar lista de materias
    public void llenarListaMateria(String filtro){
        materia = new Materia();
        List objetcts = null;

        if (filtro == null) {
            String materiasExternas = ControlServicio.obtenerRespuestaPeticion(urlPublicoUESFiltro, this);
            objetcts = Materia.getAllFromJSON(materiasExternas, this);
            //objetcts = materia.getAll(this);
            //Inicializar el adaptador con la información a mostrar
        } else {
            String nuevo = urlPublicoUESFiltro + "?codmateria=" + filtro.trim();
            String materiasExternas = ControlServicio.obtenerRespuestaPeticion(nuevo, this);
            //Toast.makeText(this, urlPublicoUES, Toast.LENGTH_LONG).show();
            objetcts = Materia.getAllFromJSON(materiasExternas, this);
            //objetcts = materia.getAllFiltered(this, "codmateria", filtro);
        }

        listaMateriasAdapter = new MateriaAdapter(this, objetcts);
        // Relacionando la lista con el adaptador y llenándola
        listaMaterias.setAdapter(listaMateriasAdapter);
    }

    //Metodo para agregar materia
    public void btnNuevoMateria(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, NuevoMateria.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaMateria(null);
    }

    // Para menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        // Modificable
        menu.setHeaderTitle(listaMateriasAdapter.getItem(info.position).getNombreMateria());

        MenuInflater inflater = getMenuInflater();
        // Modificable
        inflater.inflate(R.menu.menu_ctx_lista_materia, menu);
    }

    public void buscarMateria(View v) {
        llenarListaMateria(editnombreMateria.getText().toString());
    }
    public void llenarListaCicloMateria(String filtro) {
        materia = new Materia();
        List objects;

        if (filtro == null) {
            objects = materia.getAll(this);
        } else {
            objects = materia.getAllFiltered(this, "nombremat", filtro);
        }

        //Inicializar el adaptador con la información a mostrar
        listaMateriasAdapter = new MateriaAdapter(this, objects);

        // Relacionando la lista con el adaptador y llenándola
        listaMaterias.setAdapter(listaMateriasAdapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Materia materiaActual = (listaMateriasAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizarMateria:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(materiaActual != null){
                    Intent intent = new Intent(getApplicationContext(), EditarMateria.class);
                    intent.putExtra("codMateria", materiaActual.getCodMateria());
                    intent.putExtra("idUnidad", materiaActual.getIdUnidad());
                    intent.putExtra("nombreMat", materiaActual.getNombreMateria());
                    intent.putExtra("masiva", materiaActual.isMasiva());

                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminarMateria:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if(materiaActual != null){
                    String regEliminadas;
                    eliminarWeb(materiaActual.getCodMateria());
                    regEliminadas= materiaActual.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaMateria(null);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void eliminarWeb(String codMateria){
        String url = null;
        url = urlPublicoUESEliminar + "?codmateria=" + codMateria;
        ControlServicio.sendRequest(url, this);
    }

}

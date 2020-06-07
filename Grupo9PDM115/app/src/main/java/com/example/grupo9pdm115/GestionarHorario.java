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

import com.example.grupo9pdm115.Adapters.HorarioAdapter;
import com.example.grupo9pdm115.Modelos.Horario;

import java.util.List;

public class GestionarHorario extends Activity {
    ListView listHorario;
    Horario horario;
    HorarioAdapter listaHorarioAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_horario);
        //Toast.makeText(getApplicationContext(), "mostrar", Toast.LENGTH_SHORT).show();
        // Inicializar elementos a manejar
        listHorario = (ListView) findViewById(R.id.listHorario);

        // Llamar método para llenar lista
        llenarListaHorario();

        // Asociamos el menú contextual al listview
        registerForContextMenu(listHorario);

    }
    // Método para llenar lista de unidad
    public void llenarListaHorario(){
        horario = new Horario();
        List objects = horario.getAll(this);
        listaHorarioAdapter = new HorarioAdapter(this, objects);
        listHorario.setAdapter(listaHorarioAdapter);
    }
    // Método para agregar una unidad
    public void nuevoHorario (View v){
        Intent intent = new Intent(this, NuevoHorario.class);
        startActivity(intent);
    }

    // Para que actualice la lista cuando se regrese a la ventana
    @Override
    public void onRestart() {
        super.onRestart();
        llenarListaHorario();
    }
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
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener la posición del elemento
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Horario horario = (listaHorarioAdapter.getItem(info.position));

        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(horario != null){
                    Intent intent = new Intent(getApplicationContext(), EditarHorario.class);
                    intent.putExtra("idhorario", horario.getIdHora());
                    intent.putExtra("horainicio", horario.getHoraInicio());
                    intent.putExtra("horafinal", horario.getHoraFinal());
                    startActivity(intent);
                }
                return true;
            case R.id.ctxEliminar:
                if(horario != null){
                    String regEliminadas;
                    regEliminadas= horario.eliminar(getApplicationContext());
                    Toast.makeText(getApplicationContext(), regEliminadas, Toast.LENGTH_SHORT).show();
                    llenarListaHorario();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}

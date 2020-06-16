package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.DetalleReserva.GestionarDetalleReserva;
import com.example.grupo9pdm115.Adapters.SolicitudAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarSolicitudesEncargado extends AppCompatActivity {

    ListView listaSolicitud;
    SolicitudAdapter solicitudAdapter;
    Solicitud solicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_solicitudes_encargado);
        listaSolicitud = (ListView) findViewById(R.id.listaSolicitud);
        llenarListaSolicitudes();
        registerForContextMenu(listaSolicitud);
    }

    public void llenarListaSolicitudes(){
        solicitud = new Solicitud();
        //LLENAR DATOS DE EJEMPLO
        //usuario.insertUnidad(this);
        List objects = solicitud.getAllFiltered(this, "idEncargado", Sesion.getIdusuario(this));
        solicitudAdapter = new SolicitudAdapter(this, objects);
        listaSolicitud.setAdapter(solicitudAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreCompleto = solicitudAdapter.getItem(info.position).getAsuntoSolicitud() + " " + solicitudAdapter.getItem(info.position).getIdUsuario();
        menu.setHeaderTitle(nombreCompleto);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_solicitud, menu);
        TipoLocal tl = new TipoLocal();
        Solicitud solicitud = (solicitudAdapter.getItem(info.position));
        List tipoLocal = tl.getAllFiltered(this, "idEncargado", solicitud.getIdEncargado());
        if(tipoLocal.size() > 0){
            MenuItem item = menu.findItem(R.id.ctxEnviarEncargado);
            item.setVisible(false);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Solicitud solicitudSeleccionada = (solicitudAdapter.getItem(info.position));
        switch (item.getItemId()) {
            case R.id.ctxEliminar:
                if (solicitudSeleccionada != null){
                    String regEliminados;
                    regEliminados = solicitudSeleccionada.eliminar(this);
                    Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaSolicitudes();
                }
                return true;
            case R.id.ctxConsultar:
                Toast.makeText(this, "Consultar", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ctxRevisar:
                Intent revisarInte = new Intent(this, GestionarDetalleReserva.class);
                revisarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                revisarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                revisarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                startActivity(revisarInte);
                return true;
            case R.id.ctxEnviarEncargado:
                //solicitudSeleccionada.setIdEncargado();
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarListaSolicitudes();
    }
}

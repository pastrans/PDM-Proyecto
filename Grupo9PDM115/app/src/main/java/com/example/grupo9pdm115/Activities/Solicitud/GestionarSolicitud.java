package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.example.grupo9pdm115.Activities.DetalleReserva.GestionarDetalleReserva;
import com.example.grupo9pdm115.Adapters.SolicitudAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.R;

import java.util.List;

public class GestionarSolicitud extends AppCompatActivity {

    ListView listaSolicitud;
    EditText editAsunto;
    SolicitudAdapter solicitudAdapter;
    Solicitud solicitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_solicitud);
        listaSolicitud = (ListView) findViewById(R.id.listaSolicitud);
        editAsunto = (EditText) findViewById(R.id.editAsunto);
        llenarListaSolicitudes(null);
        registerForContextMenu(listaSolicitud);
    }

    public void llenarListaSolicitudes(String filtro){
        solicitud = new Solicitud();
        //LLENAR DATOS DE EJEMPLO
        //usuario.insertUnidad(this);
        List objetcts;

        if(filtro == null){
            objetcts = solicitud.getAll(this);
        }
        else{
            objetcts = solicitud.getAllFiltered(this,"nombreciclo", filtro);
        }
        //List objects = solicitud.getAllFiltered(this, "idUsuario", Sesion.getIdusuario(this));
        solicitudAdapter = new SolicitudAdapter(this, objetcts);
        listaSolicitud.setAdapter(solicitudAdapter);
    }
    public void buscarSolicitud(View v){
        llenarListaSolicitudes(editAsunto.getText().toString());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreCompleto = solicitudAdapter.getItem(info.position).getAsuntoSolicitud() + " " + solicitudAdapter.getItem(info.position).getIdUsuario();
        menu.setHeaderTitle(nombreCompleto);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_solicitud, menu);
        Solicitud solicitud = (solicitudAdapter.getItem(info.position));
        if(solicitud.getIdUsuario().equals(Sesion.getIdusuario(getApplicationContext()))){
            MenuItem item = menu.findItem(R.id.ctxEnviarEncargado);
            item.setVisible(false);
        }
        if(!solicitud.getIdEncargado().equals(Sesion.getIdusuario(this))){
            MenuItem item = menu.findItem(R.id.ctxRevisar);
            item.setVisible(false);
        }
        if(!solicitud.getIdUsuario().equals(Sesion.getIdusuario(this))){
            MenuItem item = menu.findItem(R.id.ctxEliminar);
            item.setVisible(false);
        }
        MenuItem item3 = menu.findItem(R.id.ctxNuevoFinReserva);
        item3.setVisible(false);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Solicitud solicitudSeleccionada = (solicitudAdapter.getItem(info.position));
        switch (item.getItemId()) {
            case R.id.ctxConsultar:
                Intent consultarInte = new Intent(this, GestionarDetalleReserva.class);
                consultarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                consultarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                consultarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                consultarInte.putExtra("accion", 1);
                startActivity(consultarInte);
                return true;
            case R.id.ctxEliminar:
                if (solicitudSeleccionada != null){
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    String regEliminados;
                                    regEliminados = solicitudSeleccionada.eliminar(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), regEliminados, Toast.LENGTH_SHORT).show();
                                    llenarListaSolicitudes(null);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(getApplicationContext(), "NEGATIVO", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("¿Seguro de eliminar la solicitud?").setPositiveButton("Sí", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                return true;
            case R.id.ctxRevisar:
                Intent revisarInte = new Intent(this, GestionarDetalleReserva.class);
                revisarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                revisarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                revisarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                revisarInte.putExtra("accion", 2);
                startActivity(revisarInte);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public void btnNuevoGSolicitud(View v){
        Intent inte = new Intent(this, NuevoSolicitud.class);
        startActivity(inte);
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarListaSolicitudes(null);
    }
}

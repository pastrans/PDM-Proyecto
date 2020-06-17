package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Reserva;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Utilidades.FechasHelper;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class GestionarSolicitudesEncargado extends AppCompatActivity {

    ListView listaSolicitud;
    SolicitudAdapter solicitudAdapter;
    Solicitud solicitud, solicitudActualizarDialog;
    private String nuevaFecha = "";

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
            MenuItem item2 = menu.findItem(R.id.ctxEliminar);
            item2.setVisible(false);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Solicitud solicitudSeleccionada = (solicitudAdapter.getItem(info.position));
        solicitudActualizarDialog = (solicitudAdapter.getItem(info.position));
        switch (item.getItemId()) {
            case R.id.ctxEliminar:
                /*if (solicitudSeleccionada != null){
                    String regEliminados;
                    regEliminados = solicitudSeleccionada.eliminar(this);
                    Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                    llenarListaSolicitudes();
                }*/
                return true;
            case R.id.ctxConsultar:
                Intent consultarInte = new Intent(this, GestionarDetalleReserva.class);
                consultarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                consultarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                consultarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                consultarInte.putExtra("accion", 1);
                startActivity(consultarInte);
                return true;
            case R.id.ctxRevisar:
                Intent revisarInte = new Intent(this, GestionarDetalleReserva.class);
                revisarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                revisarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                revisarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                revisarInte.putExtra("accion", 2);
                startActivity(revisarInte);
                return true;
            case R.id.ctxEnviarEncargado:
                String encargado = solicitudSeleccionada.getEnargadoLocal(this);
                solicitudSeleccionada.setIdEncargado(encargado);
                String res = solicitudSeleccionada.actualizar(this);
                Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                llenarListaSolicitudes();
                //solicitudSeleccionada.setIdEncargado();
                return true;
            case R.id.ctxNuevoFinReserva:
                AlertDialog.Builder msj2 = new AlertDialog.Builder(this);
                msj2.setTitle("Asigne la nueva fecha de finalización: (yyyy-MM-dd)");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                msj2.setView(input);
                msj2.setPositiveButton("Asignar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //try {
                            //Date fecha = FechasHelper.stringToDate(input.getText().toString());
                            //nuevaFecha = FechasHelper.dateToString(fecha);
                            nuevaFecha = input.getText().toString();
                            solicitudActualizarDialog.setNuevoFinPeriodo(nuevaFecha);
                            String res = solicitudActualizarDialog.actualizar(getApplicationContext());
                            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        /*} catch (ParseException e) {
                            Toast.makeText(getApplicationContext(), "Error con la fecha", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }*/
                    }
                });
                msj2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                msj2.show();

                return true;
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

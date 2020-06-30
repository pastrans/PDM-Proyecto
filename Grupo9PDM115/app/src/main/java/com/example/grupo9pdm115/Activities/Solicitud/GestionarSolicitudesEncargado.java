package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.grupo9pdm115.Activities.DetalleReserva.GestionarDetalleReserva;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.SolicitudAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestionarSolicitudesEncargado extends AppCompatActivity {

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaSolicitud;
    //ListView listaSolicitud;
    SolicitudAdapter solicitudAdapter;
    Solicitud solicitud, solicitudActualizarDialog;
    private String nuevaFecha = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "GSO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_solicitudes_encargado);
        listaSolicitud = (SwipeMenuListView) findViewById(R.id.listaSolicitud);
        //listaSolicitud = (ListView) findViewById(R.id.listaSolicitud);
        llenarListaSolicitudes();
        registerForContextMenu(listaSolicitud);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "consultar" item
                SwipeMenuItem queryItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //editItem.setBackground(new ColorDrawable(Color.rgb(0xF5, 0xF5,0xF5)));
                // set item width
                queryItem.setWidth(170);
                // set a icon
                queryItem.setIcon(R.drawable.ic_query);
                // add to menu
                menu.addMenuItem(queryItem);

                // create "revisar" item
                SwipeMenuItem revisarItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                revisarItem.setWidth(170);
                // set a icon
                revisarItem.setIcon(R.drawable.ic_revisar);
                // add to menu
                menu.addMenuItem(revisarItem);

                // create "enviar" item
                SwipeMenuItem enviarItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                enviarItem.setWidth(170);
                // set a icon
                enviarItem.setIcon(R.drawable.ic_send);
                // add to menu
                menu.addMenuItem(enviarItem);

                // create "nuevo" item
                SwipeMenuItem nuevoItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                nuevoItem.setWidth(170);
                // set a icon
                nuevoItem.setIcon(R.drawable.ic_add);
                // add to menu
                menu.addMenuItem(nuevoItem);
            }
        };
        listaSolicitud.setMenuCreator(creator);
        listaSolicitud.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final Solicitud solicitudSeleccionada = (solicitudAdapter.getItem(position));
                solicitudActualizarDialog = (solicitudAdapter.getItem(position));
                switch (index) {
                    case 0:
                        Intent consultarInte = new Intent(getApplicationContext(), GestionarDetalleReserva.class);
                        consultarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                        consultarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                        consultarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                        consultarInte.putExtra("accion", 1);
                        startActivity(consultarInte);
                        return true;
                    case 1:
                        Intent revisarInte = new Intent(getApplicationContext(), GestionarDetalleReserva.class);
                        revisarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                        revisarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                        revisarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                        revisarInte.putExtra("accion", 2);
                        startActivity(revisarInte);
                        return true;
                    case 2:
                        String encargado = solicitudSeleccionada.getEnargadoLocal(getApplicationContext());
                        solicitudSeleccionada.setIdEncargado(encargado);
                        String res = solicitudSeleccionada.actualizar(getApplicationContext());
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        llenarListaSolicitudes();
                    case 3:
                        AlertDialog.Builder msj2 = new AlertDialog.Builder(getApplicationContext());
                        msj2.setTitle("Asigne la nueva fecha de finalización: (yyyy-MM-dd)");
                        final EditText input = new EditText(getApplicationContext());
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

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
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

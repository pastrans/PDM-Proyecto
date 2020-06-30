package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class GestionarSolicitud extends AppCompatActivity implements View.OnClickListener {

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaSolicitud;
    //ListView listaSolicitud;
    EditText editAsunto;
    SolicitudAdapter solicitudAdapter;
    Solicitud solicitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "RSO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_solicitud);
        listaSolicitud = (SwipeMenuListView) findViewById(R.id.listaSolicitud);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //listaSolicitud = (ListView) findViewById(R.id.listaSolicitud);
        editAsunto = (EditText) findViewById(R.id.editAsunto);
        llenarListaSolicitudes(null);
        registerForContextMenu(listaSolicitud);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //editItem.setBackground(new ColorDrawable(Color.rgb(0xF5, 0xF5,0xF5)));
                // set item width
                editItem.setWidth(170);
                // set a icon
                editItem.setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

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
            }
        };
        listaSolicitud.setMenuCreator(creator);
        listaSolicitud.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuItem(index);
                final Solicitud solicitudSeleccionada = (solicitudAdapter.getItem(position));
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

                    case 2:
                        Intent revisarInte = new Intent(getApplicationContext(), GestionarDetalleReserva.class);
                        revisarInte.putExtra("idSolicitud", solicitudSeleccionada.getIdSolicitud());
                        revisarInte.putExtra("tipoSolicitud", solicitudSeleccionada.getTipoSolicitud());
                        revisarInte.putExtra("comentario", solicitudSeleccionada.getComentario());
                        revisarInte.putExtra("accion", 2);
                        startActivity(revisarInte);
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public void llenarListaSolicitudes(String filtro){
        solicitud = new Solicitud();
        //LLENAR DATOS DE EJEMPLO
        //usuario.insertUnidad(this);
        List objetcts;

        if(filtro == null){
            objetcts = solicitud.getAllFiltered(this, "idusuario", Sesion.getIdusuario(this));
        }
        else{
            objetcts = solicitud.getAllFiltered1(this,filtro, Sesion.getIdusuario(this) );
        }
        //List objects = solicitud.getAllFiltered(this, "idUsuario", Sesion.getIdusuario(this));
        solicitudAdapter = new SolicitudAdapter(this, objetcts);
        listaSolicitud.setAdapter(solicitudAdapter);
    }
    public void buscarSolicitud(View v){
        //llenarListaSolicitudes(editAsunto.getText().toString());
        buscarSolicitud();
    }
    public void buscarSolicitud(){
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.bvoice) {
            // Si entramos a dar clic en el boton
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora ");
            startActivityForResult(i, check); } }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode==check && resultCode==RESULT_OK){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            editAsunto.setText(results.get(0));
            buscarSolicitud();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
}

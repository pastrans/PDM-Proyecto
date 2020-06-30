package com.example.grupo9pdm115.Activities.DetalleReserva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.DetalleReservaAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Comun.FechasHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GestionarDetalleReserva extends AppCompatActivity {

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaDetalleReserva;
    //ListView listaDetalleReserva;
    DetalleReservaAdapter detalleReservaAdapter;
    DetalleReserva detalleReserva;
    ControlBD helper;
    Button btnDetalleNormal, btnDetalleEspecial;
    TextView txtTitulo;
    EditText edtComentarioSolicitud;
    LinearLayout layoutComentario, layoutBusquedaDR;
    int idSolicitud = 0, totalDetalles = 0, totalAprobados = 0, accion = 1, totalAprobadosAntes = 0, total = 0;
    List<Integer> idsAgrupados, posRemover;

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
        setContentView(R.layout.activity_gestionar_detalle_reserva);
        txtTitulo = (TextView) findViewById(R.id.txtTituloGestionarDetalle);
        edtComentarioSolicitud = (EditText) findViewById(R.id.edtComentarioSolicitud);
        layoutComentario = (LinearLayout) findViewById(R.id.layoutComentarioSolicitud);
        layoutBusquedaDR = (LinearLayout) findViewById(R.id.layoutBusquedaDR);
        layoutBusquedaDR.setVisibility(View.GONE);
        if(getIntent().getExtras() != null){
            idSolicitud = getIntent().getIntExtra("idSolicitud", 0);
            accion = getIntent().getIntExtra("accion", 1);
            txtTitulo.setVisibility(View.GONE);
            layoutComentario.setVisibility(View.VISIBLE);
            edtComentarioSolicitud.setText(getIntent().getStringExtra("comentario"));
        }
        btnDetalleNormal = (Button) findViewById(R.id.btnNuevoDetalle);
        btnDetalleEspecial = (Button) findViewById(R.id.btnNuevoDetalleEspecial);
        listaDetalleReserva = (SwipeMenuListView) findViewById(R.id.listaDetalleReserva);
        //listaDetalleReserva = (ListView) findViewById(R.id.listaDetalleReserva);
        Voice=(Button) findViewById(R.id.bvoice);
        //Voice.setOnClickListener();
        helper = new ControlBD(this);
        btnDetalleNormal.setVisibility(View.GONE);
        btnDetalleEspecial.setVisibility(View.GONE);
        llenarListaDetalleReserva();
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem localItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //editItem.setBackground(new ColorDrawable(Color.rgb(0xF5, 0xF5,0xF5)));
                // set item width
                localItem.setWidth(170);
                // set a icon
                localItem.setIcon(R.drawable.ic_local);
                // add to menu
                menu.addMenuItem(localItem);

                // create "delete" item
                SwipeMenuItem activateItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                activateItem.setWidth(170);
                // set a icon
                activateItem.setIcon(R.drawable.ic_activate);
                // add to menu
                menu.addMenuItem(activateItem);

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
            }
        };
        listaDetalleReserva.setMenuCreator(creator);
        listaDetalleReserva.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                DetalleReserva detalleReservaSeleccionado = (detalleReservaAdapter.getItem(position));
                switch (index) {
                    case 0:
                        int[] relacionados = new int[idsAgrupados.size()];
                        for(int i = 0; i < idsAgrupados.size(); i++){
                            relacionados[i] = idsAgrupados.get(i);
                        }
                        Horario horarioSeleccionado = new Horario();
                        Dia diaSeleccionado = new Dia();
                        horarioSeleccionado.consultar(getApplicationContext(), String.valueOf(detalleReservaSeleccionado.getIdHora()));
                        diaSeleccionado.consultar(getApplicationContext(), String.valueOf(detalleReservaSeleccionado.getIdDia()));
                        Intent asignarLocalIntent = new Intent(getApplicationContext(), AsignarLocalDetalleReserva.class);
                        asignarLocalIntent.putExtra("id", detalleReservaSeleccionado.getIdDetalleReserva());
                        asignarLocalIntent.putExtra("diaHora", diaSeleccionado.getNombreDia() + " " + horarioSeleccionado.getHoraInicio() + " - " + horarioSeleccionado.getHoraFinal());
                        asignarLocalIntent.putExtra("detallesRelacionados", relacionados);
                        startActivity(asignarLocalIntent);
                        return true;
                    case 1:
                        List<DetalleReserva> detalles = new ArrayList<DetalleReserva>();
                        detalles.add(detalleReservaSeleccionado);
                        if(idsAgrupados.size() > 0){
                            for(int i = 0; i < idsAgrupados.size(); i++){
                                DetalleReserva dr = new DetalleReserva();
                                dr.consultar(getApplication(), String.valueOf(idsAgrupados.get(i)));
                                detalles.add(dr);
                                totalAprobados++;
                            }
                        }
                        DetalleReserva d = new DetalleReserva();
                        String res = d.aprobar(getApplicationContext(), detalles);
                        if(!res.equals("")){
                            Toast.makeText(getApplicationContext(), "Local asignado correctamente", Toast.LENGTH_SHORT).show();
                            totalAprobados++;
                            //total = totalAprobados + totalAprobadosAntes;
                            guardar();
                            //Toast.makeText(this, String.valueOf(totalAprobados) + " - " + String.valueOf(totalDetalles), Toast.LENGTH_SHORT).show();
                            llenarListaDetalleReserva();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Error. Local ya ha sido asignado", Toast.LENGTH_SHORT).show();
                        return true;
                    case 2:
                        res = "";
                        detalleReservaSeleccionado.eliminar(getApplicationContext());
                        if(idsAgrupados.size() > 0){
                            for(int i = 0; i < idsAgrupados.size(); i++){
                                DetalleReserva dr = new DetalleReserva();
                                dr.consultar(getApplication(), String.valueOf(idsAgrupados.get(i)));
                                res = dr.eliminar(getApplication());
                            }
                        }
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        llenarListaDetalleReserva();
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public void llenarListaDetalleReserva(){
        detalleReserva = new DetalleReserva();
        idsAgrupados = new ArrayList<>();
        posRemover = new ArrayList<>();
        String res = "", horaFinal = "";
        int idEventoEspecialAnterior = 0, idLocalAnterior = 0, idDiaAnterior = 0;
        List detalles = detalleReserva.getDetalleReservaSolicitud(getApplication(), idSolicitud);
        totalDetalles = detalles.size();
        for (int i = 0; i < detalles.size(); i++){
            DetalleReserva d = (DetalleReserva) detalles.get(i);
            if(idEventoEspecialAnterior == d.getIdEventoEspecial() && idLocalAnterior == d.getIdLocal() && idDiaAnterior == d.getIdDia() && d.getIdEventoEspecial() != 0){
                idsAgrupados.add(d.getIdDetalleReserva());
                Horario horafin = new Horario();
                horafin.consultar(this, String.valueOf(d.getIdHora()));
                horaFinal = horafin.getHoraFinal();
                posRemover.add(i);
            }
            if(d.isAprobado())
                if(totalAprobados == 0)
                    totalAprobados++;
                /*else
                    totalAprobadosAntes++;*/
            idEventoEspecialAnterior = d.getIdEventoEspecial();
            idLocalAnterior = d.getIdLocal();
            idDiaAnterior = d.getIdDia();
        }
        for (int j = 0; j < posRemover.size(); j++){
            int id = posRemover.get(j) - j;
            detalles.remove(id);
        }
        detalleReservaAdapter = new DetalleReservaAdapter(this, detalles, horaFinal);
        listaDetalleReserva.setAdapter(detalleReservaAdapter);
        registerForContextMenu(listaDetalleReserva);
    }
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Detalle reserva");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_lista_detalle_reserva_solicitud, menu);
        TipoLocal tl = new TipoLocal();
        Solicitud solicitud = new Solicitud();
        solicitud.consultar(this, String.valueOf(idSolicitud));
        List tipoLocal = tl.getAllFiltered(this, "idEncargado", solicitud.getIdEncargado());
        if(tipoLocal.size() == 0 && accion == 2){
            MenuItem item = menu.findItem(R.id.ctxAprobar);
            item.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.cxtAsignarLocal);
            item2.setVisible(false);
        }
        if(accion == 1){
            MenuItem item = menu.findItem(R.id.ctxAprobar);
            item.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.cxtAsignarLocal);
            item2.setVisible(false);
        }
        if(!solicitud.getIdUsuario().equals(Sesion.getIdusuario(this))){
            MenuItem item3 = menu.findItem(R.id.ctxEliminarDetalle);
            item3.setVisible(false);
        }
        if (detalleReservaAdapter.getItem(info.position).isAprobado()){
            MenuItem item = menu.findItem(R.id.cxtAsignarLocal);
            item.setVisible(false);
            MenuItem item2 = menu.findItem(R.id.ctxAprobar);
            item2.setVisible(false);
            Toast.makeText(this, "Sin acciones para el detalle", Toast.LENGTH_SHORT).show();
        }

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        DetalleReserva detalleReservaSeleccionado = (detalleReservaAdapter.getItem(info.position));
        switch (item.getItemId()){
            case R.id.cxtAsignarLocal:
                int[] relacionados = new int[idsAgrupados.size()];
                for(int i = 0; i < idsAgrupados.size(); i++){
                    relacionados[i] = idsAgrupados.get(i);
                }
                Horario horarioSeleccionado = new Horario();
                Dia diaSeleccionado = new Dia();
                horarioSeleccionado.consultar(this, String.valueOf(detalleReservaSeleccionado.getIdHora()));
                diaSeleccionado.consultar(this, String.valueOf(detalleReservaSeleccionado.getIdDia()));
                Intent asignarLocalIntent = new Intent(this, AsignarLocalDetalleReserva.class);
                asignarLocalIntent.putExtra("id", detalleReservaSeleccionado.getIdDetalleReserva());
                asignarLocalIntent.putExtra("diaHora", diaSeleccionado.getNombreDia() + " " + horarioSeleccionado.getHoraInicio() + " - " + horarioSeleccionado.getHoraFinal());
                asignarLocalIntent.putExtra("detallesRelacionados", relacionados);
                startActivity(asignarLocalIntent);
                return true;
            case R.id.ctxAprobar:
                List<DetalleReserva> detalles = new ArrayList<DetalleReserva>();
                detalles.add(detalleReservaSeleccionado);
                if(idsAgrupados.size() > 0){
                    for(int i = 0; i < idsAgrupados.size(); i++){
                        DetalleReserva dr = new DetalleReserva();
                        dr.consultar(getApplication(), String.valueOf(idsAgrupados.get(i)));
                        detalles.add(dr);
                        totalAprobados++;
                    }
                }
                DetalleReserva d = new DetalleReserva();
                String res = d.aprobar(this, detalles);
                if(!res.equals("")){
                    Toast.makeText(this, "Local asignado correctamente", Toast.LENGTH_SHORT).show();
                    totalAprobados++;
                    //total = totalAprobados + totalAprobadosAntes;
                    guardar();
                    //Toast.makeText(this, String.valueOf(totalAprobados) + " - " + String.valueOf(totalDetalles), Toast.LENGTH_SHORT).show();
                    llenarListaDetalleReserva();
                }
                else
                    Toast.makeText(this, "Error. Local ya ha sido asignado", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ctxEliminarDetalle:
                res = "";
                detalleReservaSeleccionado.eliminar(this);
                if(idsAgrupados.size() > 0){
                    for(int i = 0; i < idsAgrupados.size(); i++){
                        DetalleReserva dr = new DetalleReserva();
                        dr.consultar(getApplication(), String.valueOf(idsAgrupados.get(i)));
                        res = dr.eliminar(getApplication());
                    }
                }
                Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
                llenarListaDetalleReserva();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
*/
    public void guardarSolicitudRevisar(View v){
        guardar();
    }

    public void guardar(){
        Solicitud solicitud = new Solicitud();
        solicitud.consultar(this, String.valueOf(idSolicitud));
        solicitud.setComentario(edtComentarioSolicitud.getText().toString());
        if (accion == 2){
            solicitud.setFechaRespuesta(FechasHelper.cambiarFormatoLocalAIso(getAhora()));
            solicitud.setEstadoSolicitud(true);
            if(totalAprobados == totalDetalles && totalAprobados > 0 && totalDetalles > 0){
                solicitud.setAprobadoTotal(true);
            }
        }
        String res = solicitud.actualizar(this);
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }

    public void btnNuevoGDetalleReserva(View v){
        if(idSolicitud != 0){
            Intent inte = new Intent(this, NuevoDetalleReserva.class);
            inte.putExtra("idSolicitud", idSolicitud);
            startActivity(inte);
        }else{
            Toast.makeText(this, "Ingreso no válido a la interfaz", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnNuevoGDetalleReservaE(View v){
        if(idSolicitud != 0){
            Intent inte = new Intent(this, NuevoDetalleReservaEspecial.class);
            inte.putExtra("idSolicitud", idSolicitud);
            startActivity(inte);
        }else{
            Toast.makeText(this, "Ingreso no válido a la interfaz", Toast.LENGTH_SHORT).show();
        }
    }

    public String getAhora(){
        final Calendar c = Calendar.getInstance();
        return String.format("%02d/%02d/%d", c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH) + 1,  c.get(Calendar.YEAR));
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarListaDetalleReserva();
    }
    /*
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
            editNombreCiclo.setText(results.get(0));
            buscarhorario();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }*/
}

package com.example.grupo9pdm115.Activities.DetalleReserva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.DetalleReservaAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.DetalleReserva;
import com.example.grupo9pdm115.Modelos.Dia;
import com.example.grupo9pdm115.Modelos.Horario;
import com.example.grupo9pdm115.Modelos.Reserva;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Utilidades.FechasHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GestionarDetalleReserva extends AppCompatActivity {

    ListView listaDetalleReserva;
    DetalleReservaAdapter detalleReservaAdapter;
    DetalleReserva detalleReserva;
    ControlBD helper;
    Button btnDetalleNormal, btnDetalleEspecial;
    TextView txtTitulo;
    EditText edtComentarioSolicitud;
    LinearLayout layoutComentario;
    int idSolicitud = 0, totalDetalles = 0, totalAprobados = 0;
    List<Integer> idsAgrupados, posRemover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_detalle_reserva);
        txtTitulo = (TextView) findViewById(R.id.txtTituloGestionarDetalle);
        edtComentarioSolicitud = (EditText) findViewById(R.id.edtComentarioSolicitud);
        layoutComentario = (LinearLayout) findViewById(R.id.layoutComentarioSolicitud);
        if(getIntent().getExtras() != null){
            idSolicitud = getIntent().getIntExtra("idSolicitud", 0);
            txtTitulo.setVisibility(View.GONE);
            layoutComentario.setVisibility(View.VISIBLE);
            edtComentarioSolicitud.setText(getIntent().getStringExtra("comentario"));
        }
        btnDetalleNormal = (Button) findViewById(R.id.btnNuevoDetalle);
        btnDetalleEspecial = (Button) findViewById(R.id.btnNuevoDetalleEspecial);
        listaDetalleReserva = (ListView) findViewById(R.id.listaDetalleReserva);
        helper = new ControlBD(this);
        btnDetalleNormal.setVisibility(View.GONE);
        btnDetalleEspecial.setVisibility(View.GONE);
        llenarListaDetalleReserva();
    }

    public void llenarListaDetalleReserva(){
        detalleReserva = new DetalleReserva();
        idsAgrupados = new ArrayList<>();
        posRemover = new ArrayList<>();
        String res = "";
        int idEventoEspecialAnterior = 0, idLocalAnterior = 0, idDiaAnterior = 0;
        List detalles = detalleReserva.getDetalleReservaSolicitud(getApplication(), idSolicitud);
        totalDetalles = detalles.size();
        for (int i = 0; i < detalles.size(); i++){
            DetalleReserva d = (DetalleReserva) detalles.get(i);
            if(idEventoEspecialAnterior == d.getIdEventoEspecial() && idLocalAnterior == d.getIdLocal() && idDiaAnterior == d.getIdDia() && d.getIdEventoEspecial() != 0){
                idsAgrupados.add(d.getIdDetalleReserva());
                posRemover.add(i);
            }
            if(d.isAprobado())
                totalAprobados++;
            idEventoEspecialAnterior = d.getIdEventoEspecial();
            idLocalAnterior = d.getIdLocal();
            idDiaAnterior = d.getIdDia();
        }
        for (int j = 0; j < posRemover.size(); j++){
            int id = posRemover.get(j) - j;
            //res += "IDS = " + String.valueOf(detalles.size());
            detalles.remove(id);
        }
        detalleReservaAdapter = new DetalleReservaAdapter(this, detalles);
        listaDetalleReserva.setAdapter(detalleReservaAdapter);
        registerForContextMenu(listaDetalleReserva);
    }

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
        if(tipoLocal.size() == 0){
            MenuItem item = menu.findItem(R.id.ctxAprobar);
            item.setVisible(false);
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
                    }
                }
                DetalleReserva d = new DetalleReserva();
                String res = d.aprobar(this, detalles);
                if(!res.equals("")){
                    Toast.makeText(this, "Local asignado correctamente", Toast.LENGTH_SHORT).show();
                    totalAprobados++;
                    if(idsAgrupados.size() > 0)
                        totalAprobados += idsAgrupados.size();
                    guardar();
                    llenarListaDetalleReserva();
                }
                else
                    Toast.makeText(this, "Error. Local ya ha sido asignado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void guardarSolicitudRevisar(View v){
        guardar();
    }

    public void guardar(){
        Solicitud solicitud = new Solicitud();
        solicitud.consultar(this, String.valueOf(idSolicitud));
        solicitud.setComentario(edtComentarioSolicitud.getText().toString());
        solicitud.setFechaRespuesta(FechasHelper.cambiarFormatoLocalAIso(getAhora()));
        solicitud.setEstadoSolicitud(true);
        if(totalAprobados == totalDetalles && totalAprobados > 0 && totalDetalles > 0){
            solicitud.setAprobadoTotal(true);
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
}

package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.DetalleReserva.GestionarDetalleReserva;
import com.example.grupo9pdm115.Activities.DetalleReserva.NuevoDetalleReserva;
import com.example.grupo9pdm115.Activities.DetalleReserva.NuevoDetalleReservaEspecial;
import com.example.grupo9pdm115.Activities.EventoEspecial.NuevoEventoEspecial;
import com.example.grupo9pdm115.Adapters.TipoLocalAdapter;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Local;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.TipoLocalSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NuevoSolicitud extends AppCompatActivity {

    EditText edtEncargado, edtAsunto, edtComentario;
    CheckBox chkIntermediario;
    Spinner spinnerLocaSolicitud;
    List<Integer> idsDetalleReserva;
    RadioButton radioNormal, radioEspecial;
    TipoLocalSpinner tipoLocalAdapter;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_solicitud);

        helper = new ControlBD(this);

        edtAsunto = (EditText) findViewById(R.id.editAsuntoSolicitudNuevo);
        edtComentario = (EditText) findViewById(R.id.editComentarioNuevo);
        edtEncargado = (EditText) findViewById(R.id.edtEncargadoSolicitudNuevo);
        edtEncargado.setEnabled(false);
        spinnerLocaSolicitud = (Spinner) findViewById(R.id.spinnerLocaSolicitud);
        chkIntermediario = (CheckBox) findViewById(R.id.chkIntermediarioNuevo);
        radioNormal = (RadioButton) findViewById(R.id.radioNormalNuevo);
        radioEspecial = (RadioButton) findViewById(R.id.radioEspecialNuevo);

        helper.abrir();
        tipoLocalAdapter = new TipoLocalSpinner(this);
        helper.cerrar();

        idsDetalleReserva = new ArrayList<Integer>();
        spinnerLocaSolicitud.setAdapter(tipoLocalAdapter.getAdapterTipoLocal(this));

        spinnerLocaSolicitud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String items = spinnerLocaSolicitud.getSelectedItem().toString();
                if(!items.equals("Seleccione un tipo de local")){
                    TipoLocal tipoLocal = new TipoLocal();
                    List<TipoLocal> tipo = tipoLocal.getAllFiltered(getApplicationContext(), "nombretipo", items);
                    TipoLocal t = (TipoLocal) tipo.get(0);
                    edtEncargado.setText(t.getNombreTipo());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        chkIntermediario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if(chkIntermediario.isChecked())
                        edtEncargado.setEnabled(true);
                   else
                       edtEncargado.setEnabled(false);
               }
           }
        );

    }


    public void btnAgregarDetallerReservaSolicitud(View v){
        Intent intent = new Intent(this, NuevoDetalleReserva.class);
        startActivityForResult(intent, 1);
    }

    public void btnAgregarNSolicitud(View v){
        Solicitud solicitud = new Solicitud();
        String encargado = edtEncargado.getText().toString().trim();
        String comentario = edtComentario.getText().toString().trim();
        String asunto = edtAsunto.getText().toString().trim();
        int posTipoLocal = 0;
        posTipoLocal = spinnerLocaSolicitud.getSelectedItemPosition();
        int tipoSolicitud = -1;
        if(radioNormal.isChecked())
            tipoSolicitud = 1;
        if(radioEspecial.isChecked())
            tipoSolicitud = 2;
        int tipo = 2;
        if(chkIntermediario.isChecked())
            tipo = 1;
        else
            tipo = 2;
        if(encargado.equals("")){
            Toast.makeText(this, "Ingrese un código de encargado", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!getEncargado(encargado, tipo)){
            Toast.makeText(this, "No existe el usuario o no es un encargado de un local", Toast.LENGTH_SHORT).show();
            return;
        }
        if(selfEncargado(tipo) && chkIntermediario.isChecked()){
            Toast.makeText(this, "No se puede enviar la solicitud a sí mismo", Toast.LENGTH_SHORT).show();
            return;
        }
        if(asunto.equals("")){
            Toast.makeText(this, "Ingrese un asunto a la solicitud", Toast.LENGTH_SHORT).show();
            return;
        }
        if(posTipoLocal == 0){
            Toast.makeText(this, "Especifique a que tipo de locales desea reservar",Toast.LENGTH_SHORT).show();
            return;
        }
        int idTipoLocal = tipoLocalAdapter.getIdTipoLocal(posTipoLocal);
        solicitud.setIdEncargado(encargado);
        solicitud.setIdUsuario(Sesion.getIdusuario(getApplication()));
        solicitud.setAsuntoSolicitud(asunto);
        solicitud.setTipoSolicitud(tipoSolicitud);
        solicitud.setComentario(comentario);
        solicitud.setEstadoSolicitud(false);
        solicitud.setMostrarBoton(true);
        solicitud.setAprobadoTotal(false);
        solicitud.setFechaRealizada(ahora());
        solicitud.setFechaRespuesta("");
        solicitud.setNuevoFinPeriodo("");
        String res = solicitud.guardar(this);
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
        Intent intent;
        if (tipoSolicitud == 1){
            intent = new Intent(this, NuevoDetalleReserva.class);
            intent.putExtra("idSolicitud", solicitud.getLast(getApplication()));
            intent.putExtra("idTipoLocal", idTipoLocal);
            startActivity(intent);
        }else if(tipoSolicitud == 2){
            intent = new Intent(this, NuevoEventoEspecial.class);
            intent.putExtra("idSolicitud", solicitud.getLast(getApplication()));
            intent.putExtra("idTipoLocal", idTipoLocal);
            startActivity(intent);
        }
    }

    public boolean selfEncargado(int tipo){
        boolean mismo = false;
        String sql = "";
        if(tipo == 1)
            if(Sesion.getIdusuario(this).equals(edtEncargado.getText().toString().toUpperCase().trim()))
                mismo = true;
        if (tipo == 2){
            sql = "SELECT COUNT(idEncargado) FROM TipoLocal WHERE idEncargado ='" + Sesion.getIdusuario(this).toUpperCase() + "'";
            helper.abrir();
            Cursor cursor = helper.consultar(sql);
            if(cursor.moveToFirst())
                if(cursor.getInt(0) > 0)
                    mismo = false;
        }
        helper.cerrar();
        return mismo;
    }

    public boolean getEncargado(String usuario, int tipo){
        boolean existe = false;
        String sql = "";
        if(tipo == 1)
            sql = "SELECT COUNT(idUsuario) FROM usuario WHERE idUsuario = '" + usuario.toUpperCase() + "'";
        if (tipo == 2)
            sql = "SELECT COUNT(idEncargado) FROM TipoLocal WHERE idEncargado ='" + usuario.toUpperCase() + "'";
        helper.abrir();
        Cursor cursor = helper.consultar(sql);
        if(cursor.moveToFirst())
            if(cursor.getInt(0) > 0)
                existe = true;
        helper.cerrar();
        return existe;
    }

    public String ahora(){
        int dia, mes, anio;
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);
        return String.format("%d-%02d-%02d", anio, mes + 1, dia);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int[] result = data.getIntArrayExtra("ids");
                String res = "";
                for(int i = 0; i < result.length; i++){
                    idsDetalleReserva.add(result[i]);
                    res += "id: " + String.valueOf(result[i]) + " ";
                }
                Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}

package com.example.grupo9pdm115.Activities.Solicitud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.DetalleReserva.GestionarDetalleReserva;
import com.example.grupo9pdm115.Activities.DetalleReserva.NuevoDetalleReserva;
import com.example.grupo9pdm115.Activities.DetalleReserva.NuevoDetalleReservaEspecial;
import com.example.grupo9pdm115.Activities.EventoEspecial.NuevoEventoEspecial;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Solicitud;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NuevoSolicitud extends AppCompatActivity {

    EditText edtEncargado, edtAsunto, edtComentario;
    CheckBox chkIntermediario;
    List<Integer> idsDetalleReserva;
    RadioButton radioNormal, radioEspecial;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_solicitud);

        helper = new ControlBD(this);

        edtAsunto = (EditText) findViewById(R.id.editAsuntoSolicitudNuevo);
        edtComentario = (EditText) findViewById(R.id.editComentarioNuevo);
        edtEncargado = (EditText) findViewById(R.id.edtEncargadoSolicitudNuevo);
        chkIntermediario = (CheckBox) findViewById(R.id.chkIntermediarioNuevo);
        radioNormal = (RadioButton) findViewById(R.id.radioNormalNuevo);
        radioEspecial = (RadioButton) findViewById(R.id.radioEspecialNuevo);

        idsDetalleReserva = new ArrayList<Integer>();

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
        if(asunto.equals("")){
            Toast.makeText(this, "Ingrese un asunto a la solicitud", Toast.LENGTH_SHORT).show();
            return;
        }

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
        //Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
        Intent intent;
        if (tipoSolicitud == 1){
            intent = new Intent(this, NuevoDetalleReserva.class);
            intent.putExtra("idSolicitud", solicitud.getLast(getApplication()));
            startActivity(intent);
        }else if(tipoSolicitud == 2){
            intent = new Intent(this, NuevoEventoEspecial.class);
            intent.putExtra("idSolicitud", solicitud.getLast(getApplication()));
            startActivity(intent);
        }
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

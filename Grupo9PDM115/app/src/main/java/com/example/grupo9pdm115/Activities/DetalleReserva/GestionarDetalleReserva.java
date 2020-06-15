package com.example.grupo9pdm115.Activities.DetalleReserva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.grupo9pdm115.R;

public class GestionarDetalleReserva extends AppCompatActivity {

    int idSolicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_detalle_reserva);
        if(getIntent().getExtras() != null){
            idSolicitud = getIntent().getIntExtra("idSolicitud", 0);
        }
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

}

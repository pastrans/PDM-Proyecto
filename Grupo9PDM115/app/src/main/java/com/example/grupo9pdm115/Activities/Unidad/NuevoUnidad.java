package com.example.grupo9pdm115.Activities.Unidad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.WebService.ControlServicio;

import org.json.JSONObject;

public class NuevoUnidad extends AppCompatActivity {
    EditText nombreUnidad;
    EditText descripcionUnidad;
    int PERMISO_INTERNET;

    private String urlPublicoUES = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Unidad/ws_unidad_insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "IUN"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_unidad);
        nombreUnidad = (EditText) findViewById(R.id.editNombreUnidad);
        descripcionUnidad = (EditText) findViewById(R.id.editDescripcion);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, PERMISO_INTERNET);
    }

    public void agregarUnidad(View v){
        String regInsertados;
        String nombreU = nombreUnidad.getText().toString();
        String descripcion = descripcionUnidad.getText().toString();

        Unidad unidad = new Unidad();
        unidad.setNombreent(nombreU);
        unidad.setDescripcionent(descripcion);
        if(unidad.getNombreent().isEmpty()){
            regInsertados= "El nombre esta vacio";
        }else{
            if(unidad.getDescripcionent().isEmpty()){
                regInsertados= "La descripción esta vacia";
            }else{
                guardar(unidad);
                regInsertados = unidad.guardar(this);
                limpiar();
            }
        }
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

    private void limpiar() {
        nombreUnidad.setText("");
        descripcionUnidad.setText("");
    }

    public void btnLimpiarTextoNUnidad(View v){
        limpiar();
    }

    public void guardar(Unidad u){
        String nombreU = u.getNombreent();
        String descripcion = u.getDescripcionent();
        String url = null;
        String[] descripcionArray = descripcion.split("\\s+");
        String[] nombreArray = nombreU.split("\\s+");
        String descripcionUrl = "";
        String nombreUrl = "";
        /*for (int i = 0; i < descripcionArray.length; i++){
            if ((i+1) == descripcionArray.length)
                descripcionUrl += descripcionArray[i];
            else
                descripcionUrl += descripcionArray[i] + "+";
        }
        for(int i = 0; i < nombreArray.length; i++){
            if ((i+1) == nombreArray.length)
                nombreUrl += nombreArray[i];
            else
                nombreUrl += nombreArray[i] + "+";
        }*/
        url = urlPublicoUES + "?nombreent=" + u.getNombreent() + "&descripcionent=" + u.getDescripcionent();
        String urlNueva = url.replaceAll(" ", "+");
        ControlServicio.sendRequest(urlNueva, this);
    }
}

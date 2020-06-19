package com.example.grupo9pdm115.Activities.Unidad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.WebService.ControlServicio;

public class EditarUnidad extends AppCompatActivity {
    EditText nombreent;
    EditText descripcion;
    Unidad unidad;

    private String urlPublicoUES = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Unidad/ws_unidad_editar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "EUN"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_unidad);
        unidad = new Unidad();
        nombreent = (EditText) findViewById(R.id.editNombreUnidad);
        descripcion = (EditText) findViewById(R.id.editDescripcion);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            unidad.setIdUnidad(getIntent().getIntExtra("idunidad", 0));
            nombreent.setText(getIntent().getStringExtra("nombreent"));
            descripcion.setText(getIntent().getStringExtra("descripcionent"));
            //prioridad.setText(Integer.toString(getIntent().getIntExtra("prioridad",0)));
        }
    }
    // Método para actualizar
    public void actualizarU(View v) {
        String estado;
        String nombreuni = nombreent.getText().toString();
        unidad.setNombreent(nombreuni);
        String desc  = descripcion.getText().toString();
        unidad.setDescripcionent(desc);
        //unidad.setPrioridad(prio);
        if(unidad.getNombreent().isEmpty()){
            estado= "El nombre esta vacio";
        }else{
            if(unidad.getDescripcionent().isEmpty()){
                estado = "La descripción esta vacia";
            }else{
                actualizarWeb(unidad);
                estado = unidad.actualizar(this);
                Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void actualizarWeb(Unidad u){
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
        url = urlPublicoUES + "?nombreent=" + u.getNombreent() + "&descripcionent=" + u.getDescripcionent() + "&idUnidad=" + u.getIdUnidad();
        String urlNueva = url.replaceAll(" ", "+");
        ControlServicio.sendRequest(urlNueva, this);
    }

    public void btnLimpiarTextoNUnidad(View c){
        nombreent.setText("");
        descripcion.setText("");
    }

}

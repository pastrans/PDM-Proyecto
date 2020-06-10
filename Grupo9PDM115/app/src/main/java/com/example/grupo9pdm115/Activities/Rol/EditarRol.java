package com.example.grupo9pdm115.Activities.Rol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Modelos.AccesoUsuario;
import com.example.grupo9pdm115.Modelos.OpcionCrud;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.R;

import java.util.List;

public class EditarRol extends AppCompatActivity {

    ListView listViewEditarRol;
    TextView txtNombreRol;
    RolAdapter rolAdapter;
    Rol rol;
    AccesoUsuario accesoUsuario;
    List<String> listaAccesoUsuario;
    int[] posiciones;
    OpcionCrud oc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_rol);
        listViewEditarRol = (ListView) findViewById(R.id.listViewAccesosRolEditar);
        txtNombreRol = (TextView) findViewById(R.id.txtNombreRolEditar);
        accesoUsuario = new AccesoUsuario();
        if (getIntent().getExtras() != null){
            txtNombreRol.setText(getIntent().getStringExtra("nombreRol"));
            accesoUsuario.setIdRol(getIntent().getIntExtra("idRol", 0));
        }
        llenarListaOpcionCrud();
    }

    public void llenarListaOpcionCrud(){
        oc = new OpcionCrud();
        List<OpcionCrud> objects = oc.getAll(this);
        String[] opciones = new String[objects.size()];
        String[] ids = new String[objects.size()];
        for (int i = 0; i < objects.size(); i++){
            oc = objects.get(i);
            ids[i] = oc.getIdOpcion();
            opciones[i] = oc.getIdOpcion() + " - " + oc.getDescripcion();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, opciones);
        listViewEditarRol.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewEditarRol.setAdapter(adapter);
        listaAccesoUsuario = accesoUsuario.obtenerAccesoUsuario(this, accesoUsuario.getIdRol());
        if(listaAccesoUsuario.size() > 0){
            posiciones = new int[listaAccesoUsuario.size()];
            for (int i = 0; i < listViewEditarRol.getCount(); i++){
                for (int j = 0; j < listaAccesoUsuario.size(); j++){
                    if (listViewEditarRol.getItemAtPosition(i).toString().equals(listaAccesoUsuario.get(j))){
                        posiciones[j] = i;
                    }
                }
            }
            for (int i = 0; i < posiciones.length; i++){
                listViewEditarRol.setItemChecked(posiciones[i], true);
            }
        }
    }



    public void editarRolGuardar(View v){
        if (accesoUsuario.getIdRol() == 1){
            Toast.makeText(this, "No se pueden modificar los permisos para el Administrador", Toast.LENGTH_SHORT).show();
            return;
        }
        String selected = "", opcion = "", regInsertados = "";
        //List<AccesoUsuario> listaAccesoUsuarioActual = accesoUsuario.obtenerAccesoUsuario(this, accesoUsuario.getIdRol());
        int choice = listViewEditarRol.getCount();
        SparseBooleanArray sparseBooleanArray = listViewEditarRol.getCheckedItemPositions();
        String cantidad = "";
        for (int i = 0; i < choice; i++){
            if(sparseBooleanArray.get(i)){
                if(listaAccesoUsuario.size() > 0){
                    if(!listaAccesoUsuario.contains(listViewEditarRol.getItemAtPosition(i).toString())){
                        accesoUsuario.setIdOpcion(listViewEditarRol.getItemAtPosition(i).toString().substring(0, 3));
                        regInsertados = accesoUsuario.guardar(this);
                    }
                }else{
                    accesoUsuario.setIdOpcion(listViewEditarRol.getItemAtPosition(i).toString().substring(0, 3));
                    regInsertados = accesoUsuario.guardar(this);
                }
            }else{
                if (listaAccesoUsuario.size() > 0){
                    if(listaAccesoUsuario.contains(listViewEditarRol.getItemAtPosition(i).toString())){
                        accesoUsuario.setIdOpcion(listViewEditarRol.getItemAtPosition(i).toString().substring(0, 3));
                        accesoUsuario.deleteAcceso(this, accesoUsuario);
                    }
                }
            }
        }
        Toast.makeText(this, "Accesos del rol actualizados con éxito", Toast.LENGTH_LONG).show();
    }

}

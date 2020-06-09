package com.example.grupo9pdm115.Activities.AccesoUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.AccesoOpcionCrudAdapter;
import com.example.grupo9pdm115.Modelos.AccesoUsuario;
import com.example.grupo9pdm115.Modelos.OpcionCrud;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class NuevoAccesoUsuario extends AppCompatActivity {


    ListView listViewOpcionCrud;
    TextView txtUsuarioAcceso;
    AccesoOpcionCrudAdapter accesoOpcionCrudAdapter;
    AccesoUsuario accesoUsuario;
    List<String> listaAccesoUsuario;
    int[] posiciones;
    OpcionCrud oc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_acceso_usuario);
        listViewOpcionCrud = (ListView) findViewById(R.id.listViewOpcionCrud);
        txtUsuarioAcceso = (TextView) findViewById(R.id.txtUsuarioAcceso);
        accesoUsuario = new AccesoUsuario();
        if(getIntent().getExtras() != null){
            txtUsuarioAcceso.setText(getIntent().getStringExtra("nombrePersonal"));
            //accesoUsuario.setIdRol(getIntent().getStringExtra("idUsuario"));
            //Toast.makeText(this, accesoUsuario.getIdUsuario() + " " + txtUsuarioAcceso.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        llenarListaOpcionCrud();
        //accesoUsuario.deleteAll(this);
        /*oc = new OpcionCrud();
        oc.insertarOpcionesCrud(this);*/
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
        listViewOpcionCrud.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewOpcionCrud.setAdapter(adapter);
        listaAccesoUsuario = accesoUsuario.obtenerAccesoUsuario(this, Integer.valueOf(accesoUsuario.getIdRol()));
        if(listaAccesoUsuario.size() > 0){
            posiciones = new int[listaAccesoUsuario.size()];
            for (int i = 0; i < listViewOpcionCrud.getCount(); i++){
                for (int j = 0; j < listaAccesoUsuario.size(); j++){
                    if (listViewOpcionCrud.getItemAtPosition(i).toString().equals(listaAccesoUsuario.get(j))){
                        posiciones[j] = i;
                    }
                }
            }
            for (int i = 0; i < posiciones.length; i++){
                listViewOpcionCrud.setItemChecked(posiciones[i], true);
            }
        }
    }

    public void guardarAccesoUsuario(View v){
        String selected = "", opcion = "", regInsertados = "";
        //List<AccesoUsuario> listaAccesoUsuarioActual = accesoUsuario.obtenerAccesoUsuario(this, accesoUsuario.getIdUsuario());
        int choice = listViewOpcionCrud.getCount();
        SparseBooleanArray sparseBooleanArray = listViewOpcionCrud.getCheckedItemPositions();
        String cantidad = "";
        for (int i = 0; i < choice; i++){
            if(sparseBooleanArray.get(i)){
                if(listaAccesoUsuario.size() > 0){
                    if(!listaAccesoUsuario.contains(listViewOpcionCrud.getItemAtPosition(i).toString())){
                        accesoUsuario.setIdOpcion(listViewOpcionCrud.getItemAtPosition(i).toString().substring(0, 3));
                        regInsertados = accesoUsuario.guardar(this);
                    }
                }else{
                    accesoUsuario.setIdOpcion(listViewOpcionCrud.getItemAtPosition(i).toString().substring(0, 3));
                    regInsertados = accesoUsuario.guardar(this);
                }
            }else{
                if (listaAccesoUsuario.size() > 0){
                    if(listaAccesoUsuario.contains(listViewOpcionCrud.getItemAtPosition(i).toString())){
                        accesoUsuario.setIdOpcion(listViewOpcionCrud.getItemAtPosition(i).toString().substring(0, 3));
                        accesoUsuario.deleteAcceso(this, accesoUsuario);
                    }
                }
            }
        }
        Toast.makeText(this, "Permisos de usuario actualizados con éxito", Toast.LENGTH_LONG).show();
    }

}

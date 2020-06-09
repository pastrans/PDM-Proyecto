package com.example.grupo9pdm115.Activities.Rol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.AccesoOpcionCrudAdapter;
import com.example.grupo9pdm115.Modelos.AccesoUsuario;
import com.example.grupo9pdm115.Modelos.OpcionCrud;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.R;

import java.util.List;

public class NuevoRol extends AppCompatActivity {

    ListView listViewAccesoRol;
    AccesoOpcionCrudAdapter accesoOpcionCrudAdapter;
    AccesoUsuario accesoUsuario;
    List<String> listaAccesoUsuario;
    EditText edtNombreRol;
    int[] posiciones;
    OpcionCrud oc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_rol);
        listViewAccesoRol = (ListView) findViewById(R.id.listViewAccesosRol);
        edtNombreRol = (EditText) findViewById(R.id.edtNombreRol);
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
        listViewAccesoRol.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listViewAccesoRol.setAdapter(adapter);
        /*listaAccesoUsuario = accesoUsuario.obtenerAccesoUsuario(this, accesoUsuario.getIdUsuario());
        if(listaAccesoUsuario.size() > 0){
            posiciones = new int[listaAccesoUsuario.size()];
            for (int i = 0; i < listViewAccesoRol.getCount(); i++){
                for (int j = 0; j < listaAccesoUsuario.size(); j++){
                    if (listViewAccesoRol.getItemAtPosition(i).toString().equals(listaAccesoUsuario.get(j))){
                        posiciones[j] = i;
                    }
                }
            }
            for (int i = 0; i < posiciones.length; i++){
                listViewAccesoRol.setItemChecked(posiciones[i], true);
            }
        }*/
    }

    public void guardarRol(View v){
        String selected = "", opcion = "", regInsertados = "";
        Rol rol = new Rol();
        rol.setNombreRol(edtNombreRol.getText().toString());
        //List<AccesoUsuario> listaAccesoUsuarioActual = accesoUsuario.obtenerAccesoUsuario(this, accesoUsuario.getIdUsuario());
        int choice = listViewAccesoRol.getCount();
        SparseBooleanArray sparseBooleanArray = listViewAccesoRol.getCheckedItemPositions();
        String cantidad = "";
        String res = "";
        res = rol.guardar(this);
        if (!res.equals("Error al insertar el registro, registro duplicado. Verificar inserción.")){
            for (int i = 0; i < choice; i++){
                if(sparseBooleanArray.get(i)) {
                    accesoUsuario = new AccesoUsuario();
                    accesoUsuario.setIdRol(rol.getLastRol(this).getIdRol());
                    accesoUsuario.setIdOpcion(listViewAccesoRol.getItemAtPosition(i).toString().substring(0, 3));
                    regInsertados = accesoUsuario.guardar(this);
                }
            }
        }else{
            res = "Hubo un error al procesar el rol";
        }
        Toast.makeText(this, res, Toast.LENGTH_LONG).show();
    }

}

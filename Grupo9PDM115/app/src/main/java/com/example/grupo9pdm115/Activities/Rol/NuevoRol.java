package com.example.grupo9pdm115.Activities.Rol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.AccesoOpcionCrudAdapter;
import com.example.grupo9pdm115.Modelos.AccesoUsuario;
import com.example.grupo9pdm115.Modelos.OpcionCrud;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Sesion;
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
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "IRO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

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
        listViewAccesoRol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listViewAccesoRol.getItemAtPosition(position).toString().substring(0, 1).equals("I")){
                    if(listViewAccesoRol.isItemChecked(position))
                        listViewAccesoRol.setItemChecked(position + 3, true);
                    else{
                        if(!listViewAccesoRol.isItemChecked(position + 2) && !listViewAccesoRol.isItemChecked(position + 1))
                            listViewAccesoRol.setItemChecked(position + 3, false);
                    }
                }
                else if(listViewAccesoRol.getItemAtPosition(position).toString().substring(0, 1).equals("E")){
                    if(listViewAccesoRol.isItemChecked(position))
                        listViewAccesoRol.setItemChecked(position + 2, true);
                    else{
                        if(!listViewAccesoRol.isItemChecked(position - 1) && !listViewAccesoRol.isItemChecked(position + 1)){
                            listViewAccesoRol.setItemChecked(position + 2, false);
                        }
                    }
                }
                else if(listViewAccesoRol.getItemAtPosition(position).toString().substring(0, 1).equals("D")){
                    if(listViewAccesoRol.isItemChecked(position))
                        listViewAccesoRol.setItemChecked(position + 1, true);
                    else{
                        if(!listViewAccesoRol.isItemChecked(position - 1) && !listViewAccesoRol.isItemChecked(position - 2))
                            listViewAccesoRol.setItemChecked(position + 1, false);
                    }
                }
                else if(listViewAccesoRol.getItemAtPosition(position).toString().substring(0, 1).equals("C")){
                    for(int i = 1; i < 4; i++){
                        listViewAccesoRol.setItemChecked(position - i, false);
                    }
                }
                //Toast.makeText(getApplicationContext(), String.valueOf(listViewAccesoRol.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void guardarRol(View v){
        String selected = "", opcion = "", regInsertados = "";
        Rol rol = new Rol();
        if (!edtNombreRol.getText().toString().equals("")) {
            rol.setNombreRol(edtNombreRol.getText().toString());
            if (!rol.verificar(1, getApplicationContext())) {
                //List<AccesoUsuario> listaAccesoUsuarioActual = accesoUsuario.obtenerAccesoUsuario(this, accesoUsuario.getIdUsuario());
                int choice = listViewAccesoRol.getCount();
                SparseBooleanArray sparseBooleanArray = listViewAccesoRol.getCheckedItemPositions();
                String cantidad = "";
                String res = "";
                if(sparseBooleanArray.size() > 0){
                    res = rol.guardar(this);
                    if (!res.equals("Error al insertar el registro, registro duplicado. Verificar inserción.")) {
                        for (int i = 0; i < choice; i++) {
                            if (sparseBooleanArray.get(i)) {
                                accesoUsuario = new AccesoUsuario();
                                accesoUsuario.setIdRol(rol.getLastRol(this).getIdRol());
                                accesoUsuario.setIdOpcion(listViewAccesoRol.getItemAtPosition(i).toString().substring(0, 3));
                                regInsertados = accesoUsuario.guardar(this);
                            }
                        }
                    } else {
                        res = "Hubo un error al procesar el rol";
                    }
                    Toast.makeText(this, res, Toast.LENGTH_LONG).show();
                    this.finish();
                }else{
                    Toast.makeText(this, "Seleccione al menos 1 permiso al rol", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(this, String.valueOf(listViewAccesoRol.getCheckedItemIds().length), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ya existe un registro con ese nombre", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Por favor ingrese un nombre al rol", Toast.LENGTH_SHORT).show();
        }
    }

}

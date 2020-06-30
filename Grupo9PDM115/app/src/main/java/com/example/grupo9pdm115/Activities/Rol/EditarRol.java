package com.example.grupo9pdm115.Activities.Rol;

import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.RolAdapter;
import com.example.grupo9pdm115.Modelos.AccesoUsuario;
import com.example.grupo9pdm115.Modelos.OpcionCrud;
import com.example.grupo9pdm115.Modelos.Rol;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

public class EditarRol extends CyaneaAppCompatActivity {

    ListView listViewEditarRol;
    TextView txtNombreRol;
    RolAdapter rolAdapter;
    Rol rol;
    AccesoUsuario accesoUsuario;
    List<String> listaAccesoUsuario;
    int[] posiciones;
    OpcionCrud oc;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ERO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

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
        listViewEditarRol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listViewEditarRol.getItemAtPosition(position).toString().substring(0, 1).equals("I")){
                    if(listViewEditarRol.isItemChecked(position))
                        listViewEditarRol.setItemChecked(position + 3, true);
                    else{
                        if(!listViewEditarRol.isItemChecked(position + 2) && !listViewEditarRol.isItemChecked(position + 1))
                            listViewEditarRol.setItemChecked(position + 3, false);
                    }
                }
                else if(listViewEditarRol.getItemAtPosition(position).toString().substring(0, 1).equals("E")){
                    if(listViewEditarRol.isItemChecked(position))
                        listViewEditarRol.setItemChecked(position + 2, true);
                    else{
                        if(!listViewEditarRol.isItemChecked(position - 1) && !listViewEditarRol.isItemChecked(position + 1)){
                            listViewEditarRol.setItemChecked(position + 2, false);
                        }
                    }
                }
                else if(listViewEditarRol.getItemAtPosition(position).toString().substring(0, 1).equals("D")){
                    if(listViewEditarRol.isItemChecked(position))
                        listViewEditarRol.setItemChecked(position + 1, true);
                    else{
                        if(!listViewEditarRol.isItemChecked(position - 1) && !listViewEditarRol.isItemChecked(position - 2))
                            listViewEditarRol.setItemChecked(position + 1, false);
                    }
                }
                else if(listViewEditarRol.getItemAtPosition(position).toString().substring(0, 1).equals("C")){
                    for(int i = 1; i < 4; i++){
                        listViewEditarRol.setItemChecked(position - i, false);
                    }
                }
                //Toast.makeText(getApplicationContext(), String.valueOf(listViewAccesoRol.getItemAtPosition(position)), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editarRolGuardar(View v){
        mSimpleDialog = new MaterialDialog.Builder(this)
            .setTitle("Editar")
            .setMessage("¿Está seguro de editar los datos?")
            .setCancelable(false)
            .setPositiveButton("Editar", R.drawable.ic_edit, new MaterialDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int j) {
                if (accesoUsuario.getIdRol() == 1){
                    Toast.makeText(getApplicationContext(), "No se pueden modificar los permisos para el Administrador", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                String selected = "", opcion = "", regInsertados = "";
                //List<AccesoUsuario> listaAccesoUsuarioActual = accesoUsuario.obtenerAccesoUsuario(this, accesoUsuario.getIdRol());
                int choice = listViewEditarRol.getCount();
                SparseBooleanArray sparseBooleanArray = listViewEditarRol.getCheckedItemPositions();
                String cantidad = "";
                for (int i = 0; i < choice; i++){
                    if(sparseBooleanArray.size() > 0){
                        if(sparseBooleanArray.get(i)){
                            if(listaAccesoUsuario.size() > 0){
                                if(!listaAccesoUsuario.contains(listViewEditarRol.getItemAtPosition(i).toString())){
                                    accesoUsuario.setIdOpcion(listViewEditarRol.getItemAtPosition(i).toString().substring(0, 3));
                                    regInsertados = accesoUsuario.guardar(getApplicationContext());
                                }
                            }else{
                                accesoUsuario.setIdOpcion(listViewEditarRol.getItemAtPosition(i).toString().substring(0, 3));
                                regInsertados = accesoUsuario.guardar(getApplicationContext());
                            }
                        }else{
                            if (listaAccesoUsuario.size() > 0){
                                if(listaAccesoUsuario.contains(listViewEditarRol.getItemAtPosition(i).toString())){
                                    accesoUsuario.setIdOpcion(listViewEditarRol.getItemAtPosition(i).toString().substring(0, 3));
                                    accesoUsuario.deleteAcceso(getApplicationContext(), accesoUsuario);
                                }
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Seleccione al menos 1 permiso al rol", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(getApplicationContext(), "Accesos del rol actualizados con éxito", Toast.LENGTH_LONG).show();
                finish();
                dialogInterface.dismiss();
                }
            })
            .setNegativeButton("Cancelar", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
                }
            })
            .setAnimation("edit_anim.json")
            .build();
        mSimpleDialog.show();
    }

}

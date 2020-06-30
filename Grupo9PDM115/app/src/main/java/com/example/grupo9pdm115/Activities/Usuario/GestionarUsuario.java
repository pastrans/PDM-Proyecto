package com.example.grupo9pdm115.Activities.Usuario;

import androidx.annotation.NonNull;
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Adapters.UsuarioAdapter;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class GestionarUsuario extends CyaneaAppCompatActivity implements View.OnClickListener{

    // Permisos acciones
    private boolean permisoInsert = false;
    private boolean permisoDelete = false;
    private boolean permisoUpdate = false;
    private MaterialDialog mSimpleDialog;

    Button Voice;
    static final int check=1111;
    SwipeMenuListView listaUsuarios;
    //ListView listaUsuarios;
    EditText editNombreUsuario;
    UsuarioAdapter listaUsuariosAdapter;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando permisos para acciones
        permisoInsert = Sesion.getAccesoUsuario(getApplicationContext(), "IUS");
        permisoDelete = Sesion.getAccesoUsuario(getApplicationContext(), "DUS");
        permisoUpdate = Sesion.getAccesoUsuario(getApplicationContext(), "EUS");
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "CUS"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuario);
        listaUsuarios = (SwipeMenuListView) findViewById(R.id.idListadoUsuarios);
        Voice=(Button) findViewById(R.id.bvoice);
        Voice.setOnClickListener(this);
        //listaUsuarios = (ListView) findViewById(R.id.idListadoUsuarios);
        llenarListaUsuarios(null);
        editNombreUsuario = (EditText) findViewById(R.id.editNombreUsuario);
        registerForContextMenu(listaUsuarios);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //editItem.setBackground(new ColorDrawable(Color.rgb(0xF5, 0xF5,0xF5)));
                // set item width
                editItem.setWidth(170);
                // set a icon
                editItem.setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                //deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFF, 0xFF, 0xFF)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listaUsuarios.setMenuCreator(creator);
        listaUsuarios.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final Usuario usuarioSeleccionado = (listaUsuariosAdapter.getItem(position));
                switch (index) {
                    case 0:
                        if(!permisoUpdate){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        Intent inte = new Intent(getApplicationContext(), EditarUsuario.class);
                        inte.putExtra("idUsuario", usuarioSeleccionado.getIdUsuario());
                        inte.putExtra("nombreUsuario", usuarioSeleccionado.getNombreUsuario());
                        inte.putExtra("nombrePersona", usuarioSeleccionado.getNombrePersonal());
                        inte.putExtra("apellidoPersona", usuarioSeleccionado.getApellidoPersonal());
                        inte.putExtra("claveUsuario", usuarioSeleccionado.getClaveUsuario());
                        inte.putExtra("correoPersona", usuarioSeleccionado.getCorreoPersonal());
                        inte.putExtra("unidad", usuarioSeleccionado.getIdUnidad());
                        inte.putExtra("rol", usuarioSeleccionado.getIdRol());
                        startActivity(inte);
                        return true;
                    case 1:
                        if(!permisoDelete){
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        if (usuarioSeleccionado != null){
                            mSimpleDialog = new MaterialDialog.Builder(GestionarUsuario.this)
                                .setTitle("Eliminar")
                                .setMessage("¿Está seguro de eliminar?")
                                .setCancelable(false)
                                .setPositiveButton("Eliminar", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String regEliminados;
                                        regEliminados = usuarioSeleccionado.eliminar(getApplicationContext());
                                        Toast.makeText(getApplicationContext(), regEliminados, Toast.LENGTH_SHORT).show();
                                        llenarListaUsuarios(null);
                                        dialogInterface.dismiss();
                                    }
                                 })
                                .setNegativeButton("Cancelar", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        Toast.makeText(getApplicationContext(), "Registro no eliminado!", Toast.LENGTH_SHORT).show();
                                        dialogInterface.dismiss();
                                    }
                                })
                                    .setAnimation("delete_anim.json")
                                    .build();
                            mSimpleDialog.show();
                        }
                        //Toast.makeText(this, "Eliminar User", Toast.LENGTH_LONG).show();
                        return true;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public void llenarListaUsuarios(String filtro){
        usuario = new Usuario();
        List objetcts;

        if(filtro == null){
            objetcts = usuario.getAll(this);
        }
        else{

            objetcts = usuario.getAllFiltered1(this, filtro);
        }

        listaUsuariosAdapter = new UsuarioAdapter(this, objetcts);
        listaUsuarios.setAdapter(listaUsuariosAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        llenarListaUsuarios(null);
    }
    // Método para buscar usuario filtrado
    public void buscarUsuario(View v){
        //llenarListaUsuarios(editNombreUsuario.getText().toString());
        buscarUsuario();
    }
    public void buscarUsuario(){
        llenarListaUsuarios(editNombreUsuario.getText().toString());
    }

    public void btnNuevoGUsuario(View v){
        if(!permisoInsert){
            Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent inte = new Intent(this, NuevoUsuario.class);
        startActivity(inte);
    }

    /*public void prueba(View v){
        if(Sesion.getLoggedIn(getApplicationContext())){
            Set<String> accesoUsuario = Sesion.getAllAccesoUsuario(getApplicationContext());
            String accesos = "Los accesos son: \n";
            //Toast.makeText(this, "HOLAAA", Toast.LENGTH_SHORT).show();
            for (String acceso : accesoUsuario){
                accesos += "Acceso a : " + acceso +"\n";
            }
            Log.i("ACCESOS", accesos);
            Toast.makeText(this, accesos, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No se ha iniciado sesión", Toast.LENGTH_SHORT).show();
        }
    }*/

/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String nombreCompleto = listaUsuariosAdapter.getItem(info.position).getNombrePersonal() + " " + listaUsuariosAdapter.getItem(info.position).getApellidoPersonal();
        menu.setHeaderTitle(nombreCompleto);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_usuario, menu);
    }*/

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Usuario usuarioSeleccionado = (listaUsuariosAdapter.getItem(info.position));
        switch (item.getItemId()) {
            case R.id.ctxActualizar:
                if(!permisoUpdate){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent inte = new Intent(this, EditarUsuario.class);
                inte.putExtra("idUsuario", usuarioSeleccionado.getIdUsuario());
                inte.putExtra("nombreUsuario", usuarioSeleccionado.getNombreUsuario());
                inte.putExtra("nombrePersona", usuarioSeleccionado.getNombrePersonal());
                inte.putExtra("apellidoPersona", usuarioSeleccionado.getApellidoPersonal());
                inte.putExtra("claveUsuario", usuarioSeleccionado.getClaveUsuario());
                inte.putExtra("correoPersona", usuarioSeleccionado.getCorreoPersonal());
                inte.putExtra("unidad", usuarioSeleccionado.getIdUnidad());
                inte.putExtra("rol", usuarioSeleccionado.getIdRol());
                startActivity(inte);
                return true;
            case R.id.ctxEliminar:
                if(!permisoDelete){
                    Toast.makeText(getApplicationContext(), this.getString(R.string.mnjPermisoAccion), Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (usuarioSeleccionado != null){
                   String regEliminados;
                   regEliminados = usuarioSeleccionado.eliminar(this);
                   Toast.makeText(this, regEliminados, Toast.LENGTH_SHORT).show();
                   llenarListaUsuarios(null);
                }
                //Toast.makeText(this, "Eliminar User", Toast.LENGTH_LONG).show();
                return true;
            /*case R.id.ctxAsignarAcceso:
                if(usuarioSeleccionado != null){
                    Intent in = new Intent(this, NuevoAccesoUsuario.class);
                    in.putExtra("idUsuario", usuarioSeleccionado.getIdUsuario());
                    in.putExtra("nombrePersonal", usuarioSeleccionado.getNombrePersonal() + " " + usuarioSeleccionado.getApellidoPersonal());
                    startActivity(in);
                }*/
            default:
              return super.onContextItemSelected(item);
        }
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.bvoice) {
            // Si entramos a dar clic en el boton
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora ");
            startActivityForResult(i, check); } }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode==check && resultCode==RESULT_OK){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            editNombreUsuario.setText(results.get(0));
            buscarUsuario();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onDestroy(){ super.onDestroy(); }
    public void onPause(){
        editNombreUsuario.setText("");
        super.onPause();
    }

}

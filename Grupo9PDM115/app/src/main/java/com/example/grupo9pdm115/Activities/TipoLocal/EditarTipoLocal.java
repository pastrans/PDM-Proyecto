package com.example.grupo9pdm115.Activities.TipoLocal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.UsuarioSpinner;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class EditarTipoLocal extends AppCompatActivity {

    ControlBD helper;
    EditText edtNombreTipo;
    EditText editEncargado;
    TipoLocal tipoLocalClass;
    UsuarioSpinner usuarioSpinnerAdapter;
    private MaterialDialog mSimpleDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ETL"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tipo_local);
        helper = new ControlBD(this);
        edtNombreTipo = (EditText) findViewById(R.id.editNombreTipoLocal);
        editEncargado = (EditText) findViewById(R.id.editEncargadoTipoLocal);
        tipoLocalClass = new TipoLocal();
        tipoLocalClass.setIdTipoLocal(getIntent().getIntExtra("idTipoLocal", 0));
        edtNombreTipo.setText(getIntent().getStringExtra("nombreTipo"));
        editEncargado.setText(getIntent().getStringExtra("idEncargado"));
        /*encargadoSpinner.setAdapter(usuarioSpinnerAdapter.getAdapterUsuario(this));
        if (getIntent().getExtras() != null){
            tipoLocalClass.setIdTipoLocal(getIntent().getIntExtra("idTipoLocal", 0));
            edtNombreTipo.setText(getIntent().getStringExtra("nombreTipo"));
            String idEncargado = getIntent().getStringExtra("idEncargado");
            for (int i = 1; i < encargadoSpinner.getAdapter().getCount(); i++){
                String idEncargadoItem = usuarioSpinnerAdapter.getIdUsuario(i);
                if (idEncargadoItem.equals(idEncargado))
                    encargadoSpinner.setSelection(i);
            }
        }*/
    }

    public void btnEditarETipoLocal(View v){
        mSimpleDialog = new MaterialDialog.Builder(this)
                .setTitle("Editar")
                .setMessage("¿Está seguro de editar los datos?")
                .setCancelable(false)
                .setPositiveButton("Editar", R.drawable.ic_edit, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String regInsertados;
                        //int posUsuario = encargadoSpinner.getSelectedItemPosition();
                        if(edtNombreTipo.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Ingrese un nombre al tipo de local", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(editEncargado.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "Ingrese un encargado", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        tipoLocalClass.setNombreTipo(edtNombreTipo.getText().toString());
                        //tipoLocalClass.setIdEncargado(usuarioSpinnerAdapter.getIdUsuario(posUsuario));
                        tipoLocalClass.setIdEncargado(editEncargado.getText().toString());
                        regInsertados = tipoLocalClass.actualizar(getApplicationContext());
                        Toast.makeText(getApplicationContext(), regInsertados, Toast.LENGTH_SHORT).show();
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

    public void btnLimpiarETipoLocal(View v){
        edtNombreTipo.setText("");
        editEncargado.setText("");
    }


}

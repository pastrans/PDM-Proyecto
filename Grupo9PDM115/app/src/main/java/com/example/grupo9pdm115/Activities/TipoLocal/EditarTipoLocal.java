package com.example.grupo9pdm115.Activities.TipoLocal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Encargado;
import com.example.grupo9pdm115.Modelos.TipoLocal;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.UsuarioSpinner;

public class EditarTipoLocal extends AppCompatActivity {

    ControlBD helper;
    EditText edtNombreTipo;
    EditText editEncargado;
    TipoLocal tipoLocalClass;
    Encargado encargadoClass;
    UsuarioSpinner usuarioSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tipo_local);
        helper = new ControlBD(this);
        edtNombreTipo = (EditText) findViewById(R.id.editNombreTipoLocal);
        editEncargado = (EditText) findViewById(R.id.editEncargadoTipoLocal);
        tipoLocalClass = new TipoLocal();
        encargadoClass = new Encargado();
        helper.abrir();
        usuarioSpinnerAdapter = new UsuarioSpinner(helper);
        helper.cerrar();
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
        /*String regInsertados;
        int posUsuario = encargadoSpinner.getSelectedItemPosition();
        tipoLocalClass.setNombreTipo(edtNombreTipo.getText().toString());
        tipoLocalClass.setIdEncargado(usuarioSpinnerAdapter.getIdUsuario(posUsuario));
        regInsertados = tipoLocalClass.actualizar(this);
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();*/
    }

    public void btnLimpiarETipoLocal(View v){
        edtNombreTipo.setText("");
        editEncargado.setText("");
    }

    public void btnRegresarETipoLocal(View v){
        finish();
    }

}

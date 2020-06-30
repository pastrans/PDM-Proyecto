package com.example.grupo9pdm115.Activities.CicloMateria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.CicloSpinner;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class EditarCicloMateria extends AppCompatActivity {
    //Declarando
    EditText editCodMateria;
    Spinner idCiclo;
    CicloSpinner control;
    int posicion, idCicloMateria;
    private MaterialDialog mSimpleDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ECM"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_ciclo_materia);

        editCodMateria = (EditText) findViewById(R.id.editMateria);

        // Spinner
        idCiclo = (Spinner)  findViewById(R.id.spinnerCiclo);
        control= new CicloSpinner(this);
        idCiclo.setAdapter(control.getAdapterCiclo(this));

        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            idCicloMateria= getIntent().getIntExtra("idciclomateria", 0);
            editCodMateria.setText(getIntent().getStringExtra("codmateria"));
            posicion= control.buscarCiclo(getIntent().getIntExtra("idciclo", 0));
            idCiclo.setSelection(posicion);
        }

    }
    public void btnactualizarCicloMateria(View v) {
        mSimpleDialog = new MaterialDialog.Builder(this)
                .setTitle("Editar")
                .setMessage("¿Está seguro de editar los datos?")
                .setCancelable(false)
                .setPositiveButton("Editar", R.drawable.ic_edit, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String codMaateria = editCodMateria.getText().toString();
                        //limpiamos de espacios en blancos al principio y al final
                        codMaateria = codMaateria.trim();
                        codMaateria = codMaateria.toUpperCase();
                        int posicionCiclo = 0, idCiclo = 0;
                        posicionCiclo = EditarCicloMateria.this.idCiclo.getSelectedItemPosition();


                        if(editCodMateria != null){
                            if (posicionCiclo!= 0) {

                                if(validarCodMateria(codMaateria) ==1){                    //Instanciando Materia para guardar
                                    CicloMateria cicloMateria = new CicloMateria();
                                    // Obtenemos la posición del spinner
                                    idCiclo = control.getIdCiclo(posicionCiclo);

                                    if(!cicloMateria.verificarRegistro(getApplicationContext(),codMaateria, idCiclo)){
                                        cicloMateria.setIdCicloMateria(idCicloMateria);
                                        cicloMateria.setCodMateria(codMaateria);
                                        cicloMateria.setIdCiclo(idCiclo);
                                        String regInsertados = cicloMateria.actualizar(getApplicationContext());
                                        Toast.makeText(getApplicationContext(), regInsertados, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), getApplication().getString(R.string.mnjCMYaExiste), Toast.LENGTH_SHORT).show(); //"Ya existe"
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), getApplication().getString(R.string.mnjCMCodNoRegis), Toast.LENGTH_SHORT).show(); // "Código no registrado"
                                }

                            }else{
                                Toast.makeText(getApplicationContext(), getApplication().getString(R.string.mnjCMSelecCiclo), Toast.LENGTH_SHORT).show(); // "Selecicone el ciclo"
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), getApplication().getString(R.string.mnjCMIngCodMat), Toast.LENGTH_SHORT).show(); // "Ingrese código de materia"
                        }
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

    int validarCodMateria(String codigo){
        int num = 0;
        Materia mat = new Materia();
        mat.consultar( this ,codigo );

        if(mat.getNombreMateria() != null){
            num = 1;

        }
        return num;
    }

    //Limpiar campos
    public void btnLimpiarECicloMateria(View v) {
        editCodMateria.setText("");
        idCiclo.setSelection(0);
    }
}

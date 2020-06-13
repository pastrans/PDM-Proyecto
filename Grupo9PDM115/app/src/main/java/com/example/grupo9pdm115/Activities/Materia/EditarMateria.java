package com.example.grupo9pdm115.Activities.Materia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.UnidadSpinner;
import com.example.grupo9pdm115.Utilidades.FechasHelper;

public class EditarMateria extends AppCompatActivity implements View.OnClickListener  {
    //Declarando
    ControlBD helper;
    EditText editCodMateria, editNombre;
    RadioButton masivaRadioButton1, masivaRadioButton2;
    Spinner idUnidad;
    UnidadSpinner control;
    Unidad unidad;
    int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materia);
        unidad= new Unidad();
        helper = new ControlBD(this);
        editCodMateria = (EditText) findViewById(R.id.editcodmateria);
        editCodMateria.setEnabled(false);
        editNombre = (EditText) findViewById(R.id.nombreMat);
        idUnidad = (Spinner)  findViewById(R.id.spnidUnidad);
        masivaRadioButton1 = (RadioButton) findViewById(R.id.masivaRadioButton1);
        masivaRadioButton2 = (RadioButton) findViewById(R.id.masivaRadioButton2);
        helper.abrir();
        control= new UnidadSpinner(helper);
        helper.cerrar();

        idUnidad.setAdapter(control.getAdapterUnidad(getApplicationContext()));
        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            editCodMateria.setText(getIntent().getStringExtra("codMateria"));
            editNombre.setText(getIntent().getStringExtra("nombreMat"));
            unidad.setIdUnidad(getIntent().getIntExtra("idUnidad", 0));
            if(getIntent().getBooleanExtra("masiva", Boolean.FALSE ) == Boolean.FALSE ){
                masivaRadioButton2.setChecked(true);
            }
            else{
                masivaRadioButton1.setChecked(true);
            }

        }
        Log.i("EditarMateria", "Id que trae de la base "+getIntent().getIntExtra("idUnidad", 0) );
        posicion= control.buscarUnidad(getIntent().getIntExtra("idUnidad", 0));
        Log.i("EditarMateria", "Está en la posición:  "+posicion );
        idUnidad.setSelection(posicion);


    }
    public void actualizarMateria(View v) {
        String codMaateria = editCodMateria.getText().toString();
        String nombreMateria = editNombre.getText().toString();

        Boolean masividad = Boolean.FALSE;
        Boolean bandera= Boolean.TRUE;
        codMaateria.trim();
        nombreMateria.trim();
        if(codMaateria.isEmpty()){
            Toast.makeText(this, "Ingrese un código", Toast.LENGTH_LONG).show();
        }else {
            if(nombreMateria.isEmpty()){
                Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_LONG).show();
            }else {
                if (masivaRadioButton1.isChecked()){
                    masividad = Boolean.TRUE;

                }else if(masivaRadioButton2.isChecked()){
                    masividad = Boolean.FALSE;

                }else {
                    bandera= Boolean.FALSE;
                }

                if(bandera){

                    int posicionUnidad = 0, idTP = 0;
                    posicionUnidad = idUnidad.getSelectedItemPosition();
                    if (posicionUnidad!= 0) {
                        //Log.i("posicion: ", String.valueOf(posicion)  );


                        //Instanciando Materia para guardar
                        Materia materia = new Materia();
                        idTP= control.getIdUnidad(posicionUnidad);
                        materia.setIdUnidad(idTP);
                        materia.setCodMateria(codMaateria);
                        materia.setNombreMateria(nombreMateria);
                        materia.setMasiva(masividad);

                        String regInsertados = materia.actualizar(this);
                        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
                        super.onBackPressed();


                    }
                    else {

                        Toast.makeText(this, "Seleccione una Unidad ", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Selecicone la masividad", Toast.LENGTH_SHORT).show();
                }

            }

        }



    }
    public void btnRegresarNMateria(View v){
        super.onBackPressed();
    }

    //Limpiar campos
    public void btnLimpiarNMateria(View v) {
        editNombre.setText("");
        masivaRadioButton1.setChecked(false);
        masivaRadioButton2.setChecked(false);
        idUnidad.setSelection(0);

    }


    @Override
    public void onClick(View v) {

    }
}

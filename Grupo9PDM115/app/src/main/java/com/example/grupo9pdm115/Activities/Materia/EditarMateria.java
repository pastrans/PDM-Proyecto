package com.example.grupo9pdm115.Activities.Materia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.UnidadSpinner;

public class EditarMateria extends Activity implements View.OnClickListener  {
    //Declarando
    ControlBD helper;
    EditText editCodMateria, editNombre;
    RadioButton masivaRadioButton1, masivaRadioButton2;
    Spinner idUnidad;
    UnidadSpinner control;
    Unidad unidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_materia);
        unidad= new Unidad();
        helper = new ControlBD(this);
        editCodMateria = (EditText) findViewById(R.id.editcodmateria);
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

    }

    @Override
    public void onClick(View v) {

    }
}

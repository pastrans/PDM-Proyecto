package com.example.grupo9pdm115.Activities.Materia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Adapters.UnidadAdapter;
import com.example.grupo9pdm115.BD.ControlBD;

import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.NuevoGrupoSpinners;
import com.example.grupo9pdm115.Spinners.UnidadSpinner;

public class NuevoMateria  extends Activity implements View.OnClickListener{
    //Declarando
    ControlBD helper;
    EditText editCodMateria, editNombre;
    RadioButton masivaRadioButton1, masivaRadioButton2;
    Spinner idUnidad;
    UnidadSpinner control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_materia);
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

    }
    @Override
    public void onClick(View v) {

    }


    //Metodo para insertar materia
    public void btnAgregarNMateria(View v){
        //Verificando Radio Button
        if (masivaRadioButton1.isChecked()){
            final String text = "Masivo";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();

        }else if(masivaRadioButton2.isChecked()){
            final String text = "No masivo";
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        //Obteniendo valores elementos
        String nombreCiclo = editCodMateria.getText().toString();
        String inicioCiclo = editNombre.getText().toString();

        Boolean masividad = Boolean.parseBoolean(masivaRadioButton1.getText().toString());
        int posicionUnidad = 0, idTP = 0,posicionMateria= 0,idCM =0;
        posicionUnidad = idUnidad.getSelectedItemPosition();
        if (posicionUnidad!= 0) {
            //Log.i("posicion: ", String.valueOf(posicion)  );


            //Instanciando ciclo para guardar
            Materia materia = new Materia();
            idTP= control.getIdUnidad(posicionUnidad);
            materia.setIdUnidad(idTP);
            materia.setCodMateria(nombreCiclo);
            materia.setNombreMateria(inicioCiclo);
            materia.setMasiva(masividad);

            String regInsertados = materia.guardar(this);
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();


        }
        else {

            Toast.makeText(this, "Seleccione Tipo de grupo ", Toast.LENGTH_SHORT).show();
        }



    }
    // Método para regresar al activity anterior
    public void btnRegresarNCiclo(View v){
        super.onBackPressed();
    }

    //Limpiar campos
    public void btnLimpiarTextoNCiclo(View v) {
        editCodMateria.setText("");
        editNombre.setText("");

    }


}

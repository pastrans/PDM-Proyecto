package com.example.grupo9pdm115.Activities.CicloMateria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.CicloSpinner;

public class NuevoCicloMateria extends AppCompatActivity implements View.OnClickListener {

    ControlBD helper;
    EditText editCodMateria;
    Spinner idCiclo;
    CicloSpinner control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ciclo_materia);

        helper = new ControlBD(this);
        editCodMateria = (EditText) findViewById(R.id.editMateria);
        idCiclo = (Spinner)  findViewById(R.id.spinnerCiclo);
        helper.abrir();
        control= new CicloSpinner(helper);
        helper.cerrar();
        idCiclo.setAdapter(control.getAdapterCiclo(getApplicationContext()));

    }

    //Metodo para insertar materia
    public void btnAgregarNCicloMateria(View v){

        String codMaateria = editCodMateria.getText().toString();
        //limpiamos de espacios en blancos al principio y al final
        codMaateria.trim();
        codMaateria = codMaateria.toUpperCase();
        Log.i("NuevoCicloMateria", "Se pajo a mayúscula " +codMaateria );
        int posicionCiclo = 0, idCiclo = 0;
        posicionCiclo = this.idCiclo.getSelectedItemPosition();


        if(editCodMateria != null){
            if (posicionCiclo!= 0) {

                if(validarCodMateria(codMaateria) ==1){

                    //Instanciando Materia para guardar
                    CicloMateria cicloMateria = new CicloMateria();
                    // Obtenemos la posición del spinner
                    idCiclo = control.getIdCiclo(posicionCiclo);


                    if(!cicloMateria.verificarRegistro(this,codMaateria, idCiclo)){
                        cicloMateria.setCodMateria(codMaateria);
                        cicloMateria.setIdCiclo(idCiclo);
                        String regInsertados = cicloMateria.guardar(this);
                        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Ya existe", Toast.LENGTH_SHORT).show();

                    }


                }else {
                    Toast.makeText(this, "Código no registrado", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Selecicone el ciclo", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Ingrese código de materia", Toast.LENGTH_SHORT).show();

        }


    }
    // Método para regresar al activity anterior
    public void btnRegresarNCicloMateria(View v){
        super.onBackPressed();
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
    public void btnLimpiarNCicloMateria(View v) {
        editCodMateria.setText("");
        idCiclo.setSelection(0);
    }

    @Override
    public void onClick(View v) {

    }

}

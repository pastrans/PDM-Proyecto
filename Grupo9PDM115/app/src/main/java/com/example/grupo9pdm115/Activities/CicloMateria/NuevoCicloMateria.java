package com.example.grupo9pdm115.Activities.CicloMateria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class NuevoCicloMateria extends AppCompatActivity implements View.OnClickListener {

    EditText editCodMateria;
    Spinner idCiclo;
    CicloSpinner control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ICM"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_ciclo_materia);

        editCodMateria = (EditText) findViewById(R.id.editMateria);

        // Spinner
        idCiclo = (Spinner)  findViewById(R.id.spinnerCiclo);
        control= new CicloSpinner(this);
        idCiclo.setAdapter(control.getAdapterCiclo(this));

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


        if(!codMaateria.equals("")){
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
                        limpiar();
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

    private void limpiar() {
        editCodMateria.setText("");
        idCiclo.setSelection(0);
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
        limpiar();
    }

    @Override
    public void onClick(View v) {

    }

}

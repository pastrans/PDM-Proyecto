package com.example.grupo9pdm115.Activities.Materia;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Materia;
import com.example.grupo9pdm115.Modelos.Unidad;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.UnidadSpinner;
import com.example.grupo9pdm115.WebService.ControlServicio;

public class NuevoMateria  extends AppCompatActivity implements View.OnClickListener{
    //Declarando
    ControlBD helper;
    EditText editCodMateria, editNombre;
    RadioButton masivaRadioButton1, masivaRadioButton2;
    Spinner idUnidad;
    UnidadSpinner control;

    private String urlPublicoUES = "https://eisi.fia.ues.edu.sv/eisi09/LE17004/Proyecto/Materia/ws_materia_insert.php";

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
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
    @Override
    public void onClick(View v) {

    }
    //Metodo para insertar materia
    public void btnAgregarNMateria(View v){
        //Verificando Radio Button
        //Obteniendo valores elementos
        String codMaateria = editCodMateria.getText().toString();
        String nombreMateria = editNombre.getText().toString();
        /*
        if (codMaateria.matches("^[A-Za-z]{3}$[0-9]{3}")){
            Toast.makeText(this, "Lo ingreso bien", Toast.LENGTH_LONG).show();
        }
        */
        Boolean masividad = Boolean.FALSE;
        Boolean bandera= Boolean.TRUE;
        codMaateria.trim();
        codMaateria.trim();
        codMaateria = codMaateria.toUpperCase ();
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
                            guardarWeb(materia);
                            String regInsertados = materia.guardar(this);
                            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();


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
    // Método para regresar al activity anterior
    public void btnRegresarNMateria(View v){
        super.onBackPressed();
    }

    public void guardarWeb(Materia m){
        String[] nomMatArray = m.getNombreMateria().split("\\s+");
        String nombreUrl = "";
        /*for(int i = 0; i < nomMatArray.length; i++){
            if ((i+1) == nomMatArray.length)
                nombreUrl += nomMatArray[i];
            else
                nombreUrl += nomMatArray[i] + "+";
        }*/
        String url = null;
        url = urlPublicoUES + "?codmateria=" + m.getCodMateria() + "&nombremat="+ m.getNombreMateria() + "&idUnidad="+ m.getIdUnidad() + "&masiva=" + m.isMasiva();
        String urlNueva = url.replaceAll(" ", "+");
        ControlServicio.sendRequest(urlNueva, this);
    }

    //Limpiar campos
    public void btnLimpiarNMateria(View v) {
        editCodMateria.setText("");
        editNombre.setText("");
        masivaRadioButton1.setChecked(false);
        masivaRadioButton2.setChecked(false);
        idUnidad.setSelection(0);

    }


}

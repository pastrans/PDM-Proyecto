package com.example.grupo9pdm115.Activities.Coordinacion;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.Ciclo;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Coordinacion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Spinners.CicloSpinner;

import java.util.Calendar;

public class NuevoCoordinacion extends AppCompatActivity implements View.OnClickListener {
    ControlBD helper;
    EditText editCodMateria;
    EditText editIdUsuario;
    Spinner TipoCoordinacion;
    String[] contenidoTipoCoor;
    private int dia, mes, anio;
    private CicloMateria cm = new CicloMateria();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_coordinacion);

        helper = new ControlBD(this);
        editCodMateria = (EditText) findViewById(R.id.editCodMateria);
        editIdUsuario = (EditText) findViewById(R.id.editIdUsuario);
        TipoCoordinacion = (Spinner)  findViewById(R.id.SpinnerTipoCoordinacion);
        contenidoTipoCoor = new String[]{"Seleccione","Laboratorio", "Discusión","Teórico"};
        TipoCoordinacion.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,contenidoTipoCoor));
    }
    public void btnAgregarNCoordinador(View v){

        String codMaateria = editCodMateria.getText().toString();
        String IdUsuario = editIdUsuario.getText().toString();
        //limpiamos de espacios en blancos al principio y al final
        codMaateria.trim();
        IdUsuario.trim();
        codMaateria = codMaateria.toUpperCase();
        IdUsuario = IdUsuario.toUpperCase();

        if( !codMaateria.equals("")){
            if ( !IdUsuario.equals("")) {
                if(validarSeleccion()){
                    //Validar que sí exista esa materia en el ciclo actual (CicloMateria)
                    if(validarCicloMateria(codMaateria) ==1){

                        // Validar que exista ese id de usuario
                        if (validarUsuario(IdUsuario) ){
                            //Instanciando coordinacion para guardar
                            Coordinacion coordinacion = new Coordinacion();
                            // valor que se seleeciono en el spinner
                            String tipoCoordinacion= contenidoTipoCoor[TipoCoordinacion.getSelectedItemPosition()];

                            //Validar que por si ya existe una coordinación ( con la condición de tipo de coordinación y idCicloMateria)
                            if(!coordinacion.verificarRegistro(this,cm.getIdCicloMateria(),tipoCoordinacion )){
                                coordinacion.setIdCicloMateria(cm.getIdCicloMateria());
                                coordinacion.setIdUsuario(IdUsuario);
                                coordinacion.setTipoCoordinacion(tipoCoordinacion);
                                String regInsertados = coordinacion.guardar(this);
                                Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(this, "Ya existe está coordinación", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(this, "Código de materia no registrado", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(this, "Seleccione un Tipo de Grupo", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(this, "Ingrese Un código de Usuario", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Ingrese código de materia", Toast.LENGTH_SHORT).show();

        }


    }

    int validarCicloMateria(String codigo){
        int num = 0;

        Boolean bandera = Boolean.FALSE;
        bandera = cm.consultar( this ,codigo, getCicloActual().getIdCiclo() );
        if(bandera){
            num = 1;

        }
        return num;

    }
    Boolean validarUsuario(String IdUsuario){
        int num = 0;
        Usuario usu = new Usuario();
        Boolean bandera = Boolean.FALSE;
        bandera = usu.consultar( this ,IdUsuario );
        return bandera;

    }

    public Ciclo getCicloActual(){
        ahora();
        Ciclo ciclo = new Ciclo();
        String sqlCiclo = "SELECT * from CICLO WHERE '" + String.format("%d-%02d-%02d", anio, mes + 1, dia) + "' BETWEEN INICIO AND FIN";
        helper.abrir();
        Cursor c3 = helper.consultar(sqlCiclo);
        if(c3.moveToFirst()){
            ciclo.setIdCiclo(c3.getInt(0));
            ciclo.setInicioPeriodoClase(c3.getString(5));
            ciclo.setFinPeriodoClase(c3.getString(6));
        }
        helper.cerrar();
        return ciclo;
    }
    public void ahora(){
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);
    }


    Boolean validarSeleccion(){
        Boolean bandera= Boolean.TRUE;
        int pos = TipoCoordinacion.getSelectedItemPosition();
        if(pos == 0){
            bandera= Boolean.FALSE;
        }
        return bandera;
    }


    public void btnRegresarNCoordinador(View v){
        super.onBackPressed();
    }
    //Limpiar campos
    public void btnLimpiarNCoordinador(View v) {
        editCodMateria.setText("");
        editIdUsuario.setText("");
        TipoCoordinacion.setSelection(0);
    }

@Override
public void onClick(View v) {

        }
}

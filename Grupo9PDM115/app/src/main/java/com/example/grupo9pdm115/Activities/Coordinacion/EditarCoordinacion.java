package com.example.grupo9pdm115.Activities.Coordinacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo9pdm115.Activities.ErrorDeUsuario;
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.Modelos.Coordinacion;
import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.Modelos.Usuario;
import com.example.grupo9pdm115.R;

public class EditarCoordinacion extends AppCompatActivity {
    ControlBD helper;
    EditText editCodMateria;
    EditText editIdUsuario;
    Spinner TipoCoordinacion;
    String[] contenidoTipoCoor;

    private CicloMateria cm = new CicloMateria();
    private Integer idCiclomateria, idCiclo,idCoordinacion ;
    private String tipo, idUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Validando usuario y sesión
        if((Sesion.getLoggedIn(getApplicationContext()) && !Sesion.getAccesoUsuario(getApplicationContext(), "ECO"))
                || !Sesion.getLoggedIn(getApplicationContext())){
            Intent intent = new Intent(this, ErrorDeUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Estas banderas borran la tarea actual y crean una nueva con la actividad iniciada
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_coordinacion);
        helper = new ControlBD(this);
        editCodMateria = (EditText) findViewById(R.id.editCodMateria);
        editCodMateria.setEnabled(false);
        editIdUsuario = (EditText) findViewById(R.id.editIdUsuario);
        TipoCoordinacion = (Spinner)  findViewById(R.id.SpinnerTipoCoordinacion);
        contenidoTipoCoor = new String[]{this.getString(R.string.txtSelecTipo),"Laboratorio", "Discusión","Teórico"};
        TipoCoordinacion.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,contenidoTipoCoor));
        // Verificando paso de datos por intent
        if(getIntent().getExtras() != null){
            //Captura de datos
            tipo = getIntent().getStringExtra("tipocoordinacion");
            idCiclomateria = getIntent().getIntExtra("idciclomateria", 0);
            idUsuario = getIntent().getStringExtra("idusuario");
            idCoordinacion =  getIntent().getIntExtra("idcoordinacion", 0);

            // instancia de objetos
            cm.consultar(this, Integer.toString(idCiclomateria));

            // LLenando los campos
            llenarSpinner(tipo);
            editCodMateria.setText(cm.getCodMateria());
            editIdUsuario.setText(idUsuario);

        }
    }
    private void llenarSpinner(String cadena){
        int pos=0;
        for(int i= 0; i<contenidoTipoCoor.length; i++){
            if(contenidoTipoCoor[i].equals(cadena) ){
                pos = i;
            }
        }
        TipoCoordinacion.setSelection(pos);
    }
    public void btnRegresareCoordinador(View v){
        super.onBackPressed();
    }

    //Limpiar campos
    public void btnLimpiarECoordinador(View v) {
        editIdUsuario.setText("");
        TipoCoordinacion.setSelection(0);
    }

    public void btnEditarECoordinador(View v) {
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
                        // Validar que exista ese id de usuario
                        if (validarUsuario(IdUsuario) ){
                            //Instanciando coordinacion para guardar
                            Coordinacion coordinacion = new Coordinacion();
                            // valor que se seleeciono en el spinner
                            String tipoCoordinacion= contenidoTipoCoor[TipoCoordinacion.getSelectedItemPosition()];

                            //Validar que por si ya existe una coordinación ( con la condición de tipo de coordinación y idCicloMateria)
                            if(!coordinacion.verificarRegistro(this,cm.getIdCicloMateria(),tipoCoordinacion )){
                                coordinacion.setIdCoodinacion(idCoordinacion);
                                coordinacion.setIdCicloMateria(cm.getIdCicloMateria());
                                coordinacion.setIdUsuario(IdUsuario);
                                coordinacion.setTipoCoordinacion(tipoCoordinacion);
                                String regInsertados = coordinacion.actualizar(this);
                                Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(this, "Ya existe está coordinación", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
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
    Boolean validarSeleccion(){
        Boolean bandera= Boolean.TRUE;
        int pos = TipoCoordinacion.getSelectedItemPosition();
        if(pos == 0){
            bandera= Boolean.FALSE;
        }
        return bandera;
    }
    Boolean validarUsuario(String IdUsuario){
        int num = 0;
        Usuario usu = new Usuario();
        Boolean bandera = Boolean.FALSE;
        bandera = usu.consultar( this ,IdUsuario );
        return bandera;

    }

}

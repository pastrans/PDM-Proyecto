package com.example.grupo9pdm115.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    // Views principales que solo desplegan sub menús
    public final int[] idOpcionesPrincipales = {R.id.layoutControlUsuario, R.id.layoutAdminAcademica,
            R.id.layoutCargaAcademica, R.id.layoutControlLocales, R.id.layoutReservas};

    // Views que contienen varias opciones de menú
    public final int[] idGruposOpciones = {R.id.layoutOpcionesUsuario, R.id.layoutOpcionesAdminAcademica,
            R.id.layoutOpcionesCargaAcademica, R.id.layoutOpcionesControlLocales, R.id.layoutOpcionesReservas};

    // Views que son opciones de menú que envían a otras activities
    public final int[] idOpcionesDeMenu = {R.id.txtUsuario, R.id.txtRol, R.id.txtCiclo, R.id.txtFeriado,
            R.id.txtUnidad, R.id.txtMateria, R.id.txtCicloMateria, R.id.txtCoordinacion, R.id.txtGrupo,
            R.id.txtTipoGrupo,  R.id.txtHorario, R.id.txtLocal, R.id.txtTipoLocal, R.id.txtSolicitud,
            R.id.txtSolicitudesRecibidas};

    // Activities
    String[] activities = {"Usuario.GestionarUsuario", "Rol.GestionarRol", "Ciclo.GestionarCiclo", "Feriado.GestionarFeriado",
            "Unidad.GestionarUnidad", "Materia.GestionarMateria", "CicloMateria.GestionarCicloMateria", "Coordinacion.GestionarCoordinacion",
            "Grupo.GestionarGrupo", "TipoGrupo.GestionarTipoGrupo", "Horario.GestionarHorario", "Local.GestionarLocal",
            "TipoLocal.GestionarTipoLocal", "Solicitud.GestionarSolicitud","Solicitud.GestionarSolicitudesEncargado"};

    // Opciones crud
    String[] idOpcionCrud = {"CUS", "CRO", "CCL", "CFE", "CUN", "CMA", "CCM", "CCO", "CGR", "CTG", "CHO", "CLO",
            "CTL", "CSO", "GSO"};

    // Método que verifica que el usuario tenga acceso a las opciones respectivas del menú y las muestra de
    // ser así, o las oculta de no serlo
    public void verificarOpcionesCrud(){
        for(int i = 0; i < idOpcionesDeMenu.length; i++){
            if(Sesion.getAccesoUsuario(getApplicationContext(), idOpcionCrud[i])){
                findViewById(idOpcionesDeMenu[i]).setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Verificando opciones de crud
        verificarOpcionesCrud();

        // Asignando OnClickListener a los views
        asignarOnClickListener(idOpcionesPrincipales);
        asignarOnClickListener(idOpcionesDeMenu);

        // Asignar OnClickListener a cerrar sesión
        findViewById(R.id.txtCerrarSesion).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            // Views que despliegan grupos de opciones
            case R.id.layoutControlUsuario:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesUsuario);
                break;
            case R.id.layoutAdminAcademica:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesAdminAcademica);
                break;
            case R.id.layoutCargaAcademica:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesCargaAcademica);
                break;
            case R.id.layoutControlLocales:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesControlLocales);
                break;
            case R.id.layoutReservas:
                cambiarVisibilidadGrupoDeOpciones(R.id.layoutOpcionesReservas);
                break;
        }

        int posicion = obtenerPosicionEnArreglo(v, idOpcionesDeMenu);
        if(posicion != -1){
            String nombreValue = activities[posicion];
            try {
                Class<?> clase = Class.forName("com.example.grupo9pdm115.Activities." + nombreValue);
                Intent inte = new Intent(this, clase);
                this.startActivity(inte);
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        // Cerrar sesión
        if(v.getId() == R.id.txtCerrarSesion){
            // Código para cerrar sesión
            Sesion.setLooggedIn(this, false);
            Sesion.setNombreUsuario(this, "");
            Sesion.setAccesoUsuario(this, null);
            Sesion.setIdUsuario(this, null);
            Intent intent = new Intent(this, IniciarSesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Esta bandera borra el resto de actividades de la cola
            startActivity(intent);
            String tost = "No hago nada lol";
            Toast.makeText(this, tost, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Método para asignar OnClickListener a un grupo de views
    public void asignarOnClickListener(int[] idViews){
        for(int id : idViews){
            View v = (View) findViewById(id);
            v.setOnClickListener(this);
        }
    }

    // Método para cambiar visibilidad de una view, de GONE a VISIBLE y viceversa
    public void cambiarVisibilidadView(int idView){
        View v = (View) findViewById(idView);
        v.setVisibility(v.isShown() ? View.GONE : View.VISIBLE);
    }

    // Método que muestra u oculta un grupo de opciones y oculta el resto, si estaban visibles
    public void cambiarVisibilidadGrupoDeOpciones(int idView){
        for(int id : idGruposOpciones){
            // Cambiar la visibilidad del grupo seleccionado
            if(id == idView)
                cambiarVisibilidadView(idView);
            // Modificar visibilidad del resto de grupos a GONE
            else
                findViewById(id).setVisibility(View.GONE);
        }
    }

    /*
    public boolean viewEsGrupoOpciones(View v){
        boolean estado = false;
        for(int id : idGruposOpciones){
            if(v.getId() == id){
                estado = true;
            }
        }
        return estado;
    }
     */
    public boolean viewEsOpcionMenu(View v){
        boolean estado = false;
        for(int id : idOpcionesDeMenu){
            if(v.getId() == id){
                estado = true;
            }
        }
        return estado;
    }

    public int obtenerPosicionEnArreglo(View v, int[] arregloEnteros){
        int posicion = -1;

        for(int i = 0; i < arregloEnteros.length; i++){
            if(v.getId() == arregloEnteros[i]){
                posicion = i;
            }
        }

        return posicion;
    }



}

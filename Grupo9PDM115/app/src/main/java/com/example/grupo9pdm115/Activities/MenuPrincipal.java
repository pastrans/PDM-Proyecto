package com.example.grupo9pdm115.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grupo9pdm115.Modelos.Sesion;
import com.example.grupo9pdm115.R;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener {
    // Views que contienen todas las opciones de cada grupo, tanto las opciones principales como los
    // submenú de cada uno que contienen opciones de menú
    public final int[] idContenedores = {R.id.layoutContenedorUsuario, R.id.layoutContenedorAdminAcademica,
            R.id.layoutContenedorCargaAcademina, R.id.layoutContenedorLocales, R.id.layoutContenedorReservas};

    // Views principales que solo desplegan sub menús
    public final int[] idOpcionesPrincipales = {R.id.layoutControlUsuario, R.id.layoutAdminAcademica,
            R.id.layoutCargaAcademica, R.id.layoutControlLocales, R.id.layoutReservas};

    // Views que contienen varias opciones de menú
    public final int[] idGruposOpciones = {R.id.layoutOpcionesUsuario, R.id.layoutOpcionesAdminAcademica,
            R.id.layoutOpcionesCargaAcademica, R.id.layoutOpcionesControlLocales, R.id.layoutOpcionesReservas};

    // Views que son opciones de menú que envían a otras activities
    public final int[] idOpcionesDeMenu = {R.id.txtUsuario, R.id.txtRol, R.id.txtCiclo, R.id.txtFeriado,
            R.id.txtUnidad, R.id.txtMateria, R.id.txtTipoGrupo, R.id.txtHorario, R.id.txtCicloMateria,
            R.id.txtCoordinacion, R.id.txtGrupo, R.id.txtLocal, R.id.txtTipoLocal, R.id.txtSolicitud,
            R.id.txtSolicitudesRecibidas};

    // Activities
    String[] activities = {"Usuario.GestionarUsuario", "Rol.GestionarRol", "Ciclo.GestionarCiclo", "Feriado.GestionarFeriado",
            "Unidad.GestionarUnidad", "Materia.GestionarMateria", "TipoGrupo.GestionarTipoGrupo", "Horario.GestionarHorario",
            "CicloMateria.GestionarCicloMateria", "Coordinacion.GestionarCoordinacion", "Grupo.GestionarGrupo",
            "Local.GestionarLocal", "TipoLocal.GestionarTipoLocal", "Solicitud.GestionarSolicitud",
            "Solicitud.GestionarSolicitudesEncargado"};

    // Posiciones de inicio y fin en los arreglos activities y idOpcionesMenu que corresponden a cada
    // opción principal
    public final int[] opcionesUsuario = {0, 1};
    public final int[] opcionesAdminAcademica = {2, 7};
    public final int[] opcionesCargaAcademica = {8, 10};
    public final int[] opcionesControlLocales = {11, 12};
    public final int[] opcionesReservas = {13, 14};
    public final int[][] opcionesPrincipalesIndices = {opcionesUsuario, opcionesAdminAcademica, opcionesCargaAcademica,
            opcionesControlLocales, opcionesReservas};

    // Opciones crud
    String[] idOpcionCrud = {"CUS", "CRO", "CCL", "CFE", "CUN", "CMA", "CTG", "CHO", "CCM", "CCO", "CGR",
             "CLO", "CTL", "RSO", "GSO"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        // Muestra las opciones que el usuario tiene permitido acceder
        verificarOpcionesCrud();

        // Muestra las opciones principales si tiene alguna opción de menú visible
        verificarContenedores();

        // Asignando OnClickListener a los views
        asignarOnClickListener(idOpcionesPrincipales);
        asignarOnClickListener(idOpcionesDeMenu);

        // Asignar OnClickListener a cerrar sesión
        findViewById(R.id.txtCerrarSesion).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        // Para desplegar submenú de opciones de cada opción principal
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

        // Al hacer click en una opción para dirigir al layout correspondiente
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
            finish();
        }
    }

    // Método que verifica si una opción principal (las que tienen incono) tiene al menos una opción menú (las que
    // envían a otros activities) disponible, si es así muestra el contenedor completo, si no, lo oculta
    public void verificarContenedores(){
        boolean tieneVisibles;

        for(int i = 0; i < idOpcionesPrincipales.length; i++){
            tieneVisibles = false;
            for(int j = opcionesPrincipalesIndices[i][0]; j <= opcionesPrincipalesIndices[i][1] && !tieneVisibles; j++){
                tieneVisibles = findViewById(idOpcionesDeMenu[j]).getVisibility() == View.VISIBLE;
            }
            // Si tiene opciones menú visibles se muestra, si no, se oculta
            findViewById(idContenedores[i]).setVisibility((tieneVisibles ? View.VISIBLE : View.GONE));
        }
    }

    // Método que verifica que el usuario tenga acceso a las opciones respectivas del menú y las muestra de
    // ser así, o las oculta de no serlo
    public void verificarOpcionesCrud(){
        for(int i = 0; i < idOpcionesDeMenu.length; i++){
            if(Sesion.getAccesoUsuario(getApplicationContext(), idOpcionCrud[i])){
                findViewById(idOpcionesDeMenu[i]).setVisibility(View.VISIBLE);
            }
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

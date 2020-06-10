package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.example.grupo9pdm115.BD.ControlBD;

import java.util.HashSet;
import java.util.Set;

public class Sesion {

    ControlBD helper;
    public static final String LOGGED_IN_PREF = "logged_in_status";
    public static final String ACCESOS = "accesos_usuario";

    static SharedPreferences getPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setNombreUsuario(Context context, String nombreUsuario){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("nombre_usuario", nombreUsuario);
        editor.apply();
    }

    public static String getNombreUsuario(Context context){
        return getPreferences(context).getString("nombre_usuario", "No existe");
    }

    public static void setLooggedIn(Context context, boolean loggedIn){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedIn(Context context){
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static void setAccesoUsuario(Context context, Set<String> accesos){
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putStringSet(ACCESOS, accesos);
        editor.apply();
    }

    public static Set<String> getAllAccesoUsuario(Context context){
        return getPreferences(context).getStringSet(ACCESOS, null);
    }

    public static boolean getAccesoUsuario(Context context, String acceso){
        Set<String> accesos = getAllAccesoUsuario(context);
        for (String accesoUsuario : accesos){
            if (accesoUsuario.equals(acceso))
                return true;
        }
        return false;
    }

    public void setPermisosUsuario(Usuario user, Context context){
        helper = new ControlBD(context);
        Cursor cursor;
        helper.abrir();
        String sql = "SELECT idopcion FROM accesousuario, ROL WHERE rol.IDROL=" + user.getIdRol() + " and ROL.IDROL = ACCESOUSUARIO.IDROL";
        Set<String> opcionesAcceso = new HashSet<String>();
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            opcionesAcceso.add(cursor.getString(0));
        }
        helper.cerrar();
        Sesion.setAccesoUsuario(context, opcionesAcceso);
    }

    public boolean iniciarSesion(Usuario usuario, Context context){
        helper = new ControlBD(context);
        Cursor cursor;
        helper.abrir();
        String sql = "SELECT idusuario, idrol FROM usuario WHERE nombreusuario='" + usuario.getNombreUsuario() + "' AND claveusuario='" + usuario.getClaveUsuario() +"'";
        String idUsuario = "";
        int idRol = 0;
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            idUsuario = cursor.getString(0);
            idRol = cursor.getInt(1);
        }
        helper.cerrar();
        if(!idUsuario.isEmpty() && idRol > 0) {
            usuario.setIdUsuario(idUsuario);
            usuario.setIdRol(idRol);
            setPermisosUsuario(usuario, context);
            return true;
        }
        else
            return false;
    }


}

package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

import java.util.ArrayList;
import java.util.List;

public class AccesoUsuario extends TablaBD {

    private int idAccesoUsuario;
    private String idOpcion;
    private int idRol;

    public AccesoUsuario() {
        setNombreTabla("accesousuario");
        setNombreLlavePrimaria("idAccesoUsuario");
        setCamposTabla(new String[]{"idAccesoUsuario", "idOpcion", "idRol"});
    }

    public AccesoUsuario(int idAccesoUsuario, String idOpcion, int idRol) {
        this();
        this.idAccesoUsuario = idAccesoUsuario;
        this.idOpcion = idOpcion;
        this.idRol = idRol;
    }

    public int getIdAccesoUsuario() {
        return idAccesoUsuario;
    }

    public void setIdAccesoUsuario(int idAccesoUsuario) {
        this.idAccesoUsuario = idAccesoUsuario;
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(getIdAccesoUsuario());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idAccesoUsuario", getIdAccesoUsuario());
        this.valoresCamposTabla.put("idOpcion", getIdOpcion());
        this.valoresCamposTabla.put("idRol", getIdRol());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdAccesoUsuario(Integer.parseInt(arreglo[0]));
        setIdOpcion(arreglo[1]);
        setIdRol(Integer.valueOf(arreglo[2]));
    }

    @Override
    public AccesoUsuario getInstanceOfModel(String[] arreglo) {
        AccesoUsuario au = new AccesoUsuario();
        au.setAttributesFromArray(arreglo);
        return au;
    }

    public List<String> obtenerAccesoUsuario(Context context, int idRol){
        ControlBD helper = new ControlBD(context);
        List<String> accesosUsuario = new ArrayList<String>();
        String sql = "SELECT OPCIONCRUD.IDOPCION, OPCIONCRUD.DESOPCION FROM OPCIONCRUD, ACCESOUSUARIO WHERE OPCIONCRUD.IDOPCION = ACCESOUSUARIO.IDOPCION AND ACCESOUSUARIO.IDROL = '"+ idRol +"'";
        helper.abrir();
        Cursor cursor = helper.consultar(sql);
        AccesoUsuario au = new AccesoUsuario();
        while (cursor.moveToNext()){
            accesosUsuario.add(cursor.getString(0) + " - " + cursor.getString(1));
        }
        helper.cerrar();
        return accesosUsuario;
    }

    public List<String> getAllOpcionesCrud(Context context, String idUsuario){
        ControlBD helper = new ControlBD(context);
        List<String> accesosUsuario = new ArrayList<String>();
        String sql = "SELECT OPCIONCRUD.IDOPCION, OPCIONCRUD.DESOPCION FROM OPCIONCRUD, ACCESOUSUARIO WHERE OPCIONCRUD.IDOPCION = ACCESOUSUARIO.IDOPCION";
        helper.abrir();
        Cursor cursor = helper.consultar(sql);
        AccesoUsuario au = new AccesoUsuario();
        while (cursor.moveToNext()){
            accesosUsuario.add(cursor.getString(0) + " - " + cursor.getString(1));
        }
        helper.cerrar();
        return accesosUsuario;
    }

    public String deleteAll(Context context){
        ControlBD helper = new ControlBD(context);
        helper.abrir();
        helper.getDb().execSQL("DELETE FROM ACCESOUSUARIO");
        helper.cerrar();
        return "Registros eliminados";
    }

    @Override
    public String guardar(Context context) {
        String mensaje = "Registro insertado N° = : ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("idOpcion", getIdOpcion());
        this.valoresCamposTabla.put("idRol", getIdRol());

        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            mensaje = mensaje+control;
        }

        return mensaje;
    }

    public String deleteAcceso(Context context, AccesoUsuario accesoUsuario) {
        String mensaje = "Eliminado con éxito";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        helper.abrir();
        control = helper.getDb().delete(this.getNombreTabla(), "idRol = '" + accesoUsuario.getIdRol() + "' AND idOpcion = '" + accesoUsuario.getIdOpcion() +"'", null);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            mensaje = mensaje+control;
        }

        return mensaje;
    }
}

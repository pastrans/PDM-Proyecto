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
    private String idUsuario;

    public AccesoUsuario() {
        setNombreTabla("accesousuario");
        setNombreLlavePrimaria("idAccesoUsuario");
        setCamposTabla(new String[]{"idAccesoUsuario", "idOpcion", "idUsuario"});
    }

    public AccesoUsuario(int idAccesoUsuario, String idOpcion, String idUsuario) {
        this();
        this.idAccesoUsuario = idAccesoUsuario;
        this.idOpcion = idOpcion;
        this.idUsuario = idUsuario;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(getIdAccesoUsuario());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idAccesoUsuario", getIdAccesoUsuario());
        this.valoresCamposTabla.put("idOpcion", getIdOpcion());
        this.valoresCamposTabla.put("idUsuario", getIdUsuario());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdAccesoUsuario(Integer.parseInt(arreglo[0]));
        setIdOpcion(arreglo[1]);
        setIdUsuario(arreglo[2]);
    }

    @Override
    public AccesoUsuario getInstanceOfModel(String[] arreglo) {
        AccesoUsuario au = new AccesoUsuario();
        au.setAttributesFromArray(arreglo);
        return au;
    }

    public List<String> obtenerAccesoUsuario(Context context, String idUsuario){
        ControlBD helper = new ControlBD(context);
        List<String> accesosUsuario = new ArrayList<String>();
        String sql = "SELECT OPCIONCRUD.IDOPCION, OPCIONCRUD.DESOPCION FROM OPCIONCRUD, ACCESOUSUARIO WHERE OPCIONCRUD.IDOPCION = ACCESOUSUARIO.IDOPCION AND ACCESOUSUARIO.IDUSUARIO = '"+ idUsuario +"'";
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
        this.valoresCamposTabla.put("idUsuario", getIdUsuario());

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
        control = helper.getDb().delete(this.getNombreTabla(), "idUsuario = '" + accesoUsuario.getIdUsuario() + "' AND idOpcion = '" + accesoUsuario.getIdOpcion() +"'", null);
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

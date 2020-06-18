package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends TablaBD {

    private String idUsuario;
    private String nombreUsuario;
    private String claveUsuario;
    private String nombrePersonal;
    private String apellidoPersonal;
    private String correoPersonal;
    private int idUnidad;
    private int idRol;

    public Usuario() {
        setNombreTabla("usuario");
        setNombreLlavePrimaria("idUsuario");
        setCamposTabla(new String[]{"idUsuario", "idUnidad", "idRol", "nombreUsuario", "claveUsuario", "nombrePersonal", "apellidoPersonal", "correoPersonal"});
    }

    public Usuario(String nombreUsuario, String claveUsuario, String nombrePersonal, String apellidoPersonal, String correoPersonal) {
        this();
        this.nombreUsuario = nombreUsuario;
        this.claveUsuario = claveUsuario;
        this.nombrePersonal = nombrePersonal;
        this.apellidoPersonal = apellidoPersonal;
        this.correoPersonal = correoPersonal;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public String getApellidoPersonal() {
        return apellidoPersonal;
    }

    public void setApellidoPersonal(String apellidoPersonal) {
        this.apellidoPersonal = apellidoPersonal;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public String getValorLlavePrimaria() {
        return this.getIdUsuario();
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idUsuario", getIdUsuario());
        this.valoresCamposTabla.put("claveUsuario", getClaveUsuario());
        this.valoresCamposTabla.put("nombreUsuario", getNombreUsuario());
        this.valoresCamposTabla.put("nombrePersonal", getNombrePersonal());
        this.valoresCamposTabla.put("apellidoPersonal", getApellidoPersonal());
        this.valoresCamposTabla.put("correoPersonal", getCorreoPersonal());
        this.valoresCamposTabla.put("idUnidad", getIdUnidad());
        this.valoresCamposTabla.put("idRol", getIdRol());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdUsuario(arreglo[0]);
        setIdUnidad(Integer.parseInt(arreglo[1]));
        setIdRol(Integer.parseInt(arreglo[2]));
        setClaveUsuario(arreglo[4]);
        setNombreUsuario(arreglo[3]);
        setNombrePersonal(arreglo[5]);
        setApellidoPersonal(arreglo[6]);
        setCorreoPersonal(arreglo[7]);
    }

    @Override
    public Usuario getInstanceOfModel(String[] arreglo) {
        Usuario usuario = new Usuario();
        usuario.setAttributesFromArray(arreglo);
        return usuario;
    }

    /*public void insertUnidad(Context context){
        ControlBD helper = new ControlBD(context);
        String[] unidades = new String[]{"UCB", "EISI", "EII"};
        String[] descripcion = new String[] {"Unidad de Ciencias básica", "Escuela de Ingeniería de Sistemas Informáticos", "Escuela de Ingeniería Industrial"};
        int[] prioridades = new int[]{1, 2, 2};
        helper.abrir();
        helper.getDb().execSQL("DELETE FROM UNIDAD");
        Unidad unidad = new Unidad();
        for(int i = 0; i < unidades.length; i++){
            unidad.setNombreent(unidades[i]);
            unidad.setDescripcionent(descripcion[i]);
            unidad.setPrioridad(prioridades[i]);
            helper.insertar(unidad.getNombreTabla(), unidad.getValores());
        }
        helper.cerrar();
    }*/

    public int countUsuario(Context context, Usuario usuario){
        ControlBD helper = new ControlBD(context);
        Cursor cursor;
        int cantidad = 0;
        String sql = "SELECT COUNT(idUsuario) FROM usuario WHERE idUsuario LIKE '" + usuario.getNombrePersonal().substring(0, 1) + usuario.getApellidoPersonal().substring(0, 1) +"%'";
        helper.abrir();
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            cantidad = cursor.getInt(0);
        }
        helper.cerrar();
        return cantidad;
    }

    public boolean verificar(int accion, Context context){
        ControlBD helper = new ControlBD(context);
        String sql = "";
        Cursor cursor;
        helper.abrir();
        switch (accion){
            case 1:
                sql = "SELECT COUNT(idUsuario) FROM USUARIO WHERE NOMBREUSUARIO = '" + getNombreUsuario() + "'";
                cursor = helper.consultar(sql);
                if (cursor.moveToFirst())
                    if(cursor.getInt(0) > 0)
                        return true;
                break;
            case 2:
                sql = "SELECT COUNT(idUsuario) FROM USUARIO WHERE CORREOPERSONAL = '" + getCorreoPersonal() + "'";
                cursor = helper.consultar(sql);
                if (cursor.moveToFirst())
                    if(cursor.getInt(0) > 0)
                        return true;
        }
        helper.cerrar();
        return false;
    }

    public List<Usuario> getAllFiltered(Context context, String filtro){
        List<Usuario> listaTablaBD = new ArrayList<>();
        ControlBD helper = new ControlBD(context);
        String[] valores = new String[getCamposTabla().length];

        String consulta = "select idusuario, idunidad, idrol, nombreusuario, claveusuario, nombrepersonal, apellidopersonal, correopersonal ,nombrepersonal || ' ' || apellidopersonal as   \n" +
                "FullName from usuario  where   FullName like '%" + filtro +"%' ";

        helper.abrir();
        Cursor cursor = helper.consultar(consulta);

        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < getCamposTabla().length; i++){
                    valores[i] = cursor.getString(i);
                }
                listaTablaBD.add((Usuario) getInstanceOfModel(valores) );
            }while (cursor.moveToNext());
        }

        helper.cerrar();

        return listaTablaBD;
    }

}

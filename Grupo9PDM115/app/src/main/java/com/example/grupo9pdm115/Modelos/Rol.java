package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

public class Rol extends TablaBD {

    private int idRol;
    private String nombreRol;

    public Rol() {
        setNombreTabla("rol");
        setNombreLlavePrimaria("idRol");
        setCamposTabla(new String[]{"idRol", "nombreRol"});
    }

    public Rol(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(this.getIdRol());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idRol", getIdRol());
        this.valoresCamposTabla.put("nombreRol", getNombreRol());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdRol(Integer.parseInt(arreglo[0]));
        setNombreRol(arreglo[1]);
    }

    @Override
    public Rol getInstanceOfModel(String[] arreglo) {
        Rol rol = new Rol();
        rol.setAttributesFromArray(arreglo);
        return rol;
    }

    @Override
    public String guardar(Context context) {
        String mensaje = context.getString(R.string.mnjRegInsertExit); //"Se ha insertado el registro con éxito. ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombreRol", getNombreRol());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0) {
            mensaje= context.getString(R.string.mnjErrorInsercion); //"Error al insertar el registro, registro duplicado. Verificar inserción.";
        }

        return mensaje;
    }

    @Override
    public String eliminar(Context context){
        if (getIdRol() == 1)
            return context.getString(R.string.mnjRolNoElim); //"No se puede eliminar el rol del Administrador";
        String res = "";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        helper.abrir();
        Cursor cursor;
//        int cantidad = 0;

        if (!verificar(2, context)){
            control = helper.getDb().delete("accesousuario", "idRol = " + getIdRol(), null);
            control = helper.getDb().delete(this.getNombreTabla(), "idRol = " + getIdRol(), null);
            helper.cerrar();
            if(control == 0)
                res = context.getString(R.string.mnjRegNoExiste); //"Registro no existente.";
            else
            if(control == 1)
                res = context.getString(R.string.mnjRegEliminado); // "Registro eliminado correctamente.";
            else
                res = context.getString(R.string.mnjFilasAfectadas) + control; // "Filas afectadas = " + control;
        }else{
            res = context.getString(R.string.mnjRolNoElimIntegridad); // "No se puede eliminar debido a que se tiene un registro relacionado con un usuario";
        }
        return res;
    }

    public Rol getLastRol(Context context){
        Rol rol = new Rol();
        ControlBD helper = new ControlBD(context);
        Cursor cursor;
        String sql = "SELECT * FROM rol ORDER BY idRol DESC LIMIT 1;";
        helper.abrir();
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            rol.setIdRol(cursor.getInt(0));
            rol.setNombreRol(cursor.getString(1));
        }
        helper.cerrar();
        return rol;
    }

    public boolean verificar(int accion, Context context){
        ControlBD helper = new ControlBD(context);
        Cursor cursor;
        String sql = "";
        helper.abrir();
        switch (accion){
            //VERIFICAR QUE NO EXISTA EL ROL
            case 1:
                sql = "SELECT COUNT(idRol) FROM ROL WHERE NOMBREROL = '" + getNombreRol() + "'";
                cursor = helper.consultar(sql);
                if (cursor.moveToFirst()){
                    if (cursor.getInt(0) > 0)
                        return true;
                }
                break;
            //VERIFICAR QUE NO EXISTA UN USUARIO RELACIONADO CON EL ROL
            case 2:
                sql = "SELECT COUNT(idUsuario) FROM usuario WHERE idRol = " + getIdRol();
                cursor = helper.consultar(sql);
                if(cursor.moveToFirst()){
                    if (cursor.getInt(0) > 0)
                        return true;
                }
                break;
        }
        helper.cerrar();
        return false;
    }

}

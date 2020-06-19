package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

public class TipoGrupo extends TablaBD {

    // Atributos
    private String nombreTipoGrupo;
    private int  idTipoGrupo;


    // -------------Constructor---------------
    public TipoGrupo() {
        setNombreTabla("TIPOGRUPO");
        setNombreLlavePrimaria("idtipogrupo");
        setCamposTabla(new String[]{"idtipogrupo", "nombretipogrupo"});
    }
    public TipoGrupo(int idTipoGrupo, String nombreTipoGrupo ) {
        setIdTipoGrupo(idTipoGrupo);
        setNombreTipoGrupo(nombreTipoGrupo);
    }
    // -------------Fin del Constructor---------------

    // -------------Métodos getter y setter---------------
    //nombretipogrupo
    public String getNombreTipoGrupo() {
        return nombreTipoGrupo;
    }

    public void setNombreTipoGrupo(String nombreTipoGrupo) {
        this.nombreTipoGrupo = nombreTipoGrupo;
    }
    //IdTipoGrupo
    public int getIdTipoGrupo() {
        return idTipoGrupo;
    }

    public void setIdTipoGrupo(int idTipoGrupo) {
        this.idTipoGrupo = idTipoGrupo;
    }
    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(this.getIdTipoGrupo());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idtipogrupo", getIdTipoGrupo());
        this.valoresCamposTabla.put("nombretipogrupo", getNombreTipoGrupo());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdTipoGrupo(Integer.parseInt(arreglo[0]));
        setNombreTipoGrupo(arreglo[1]);
    }

    @Override
    public TipoGrupo getInstanceOfModel(String[] arreglo) {
        TipoGrupo tipoGrupo = new TipoGrupo();
        tipoGrupo.setAttributesFromArray(arreglo);
        return tipoGrupo;
    }

    @Override
    public String guardar(Context context){
        String mensaje =  context.getString(R.string.mnjRegInsert); //"Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombretipogrupo", getNombreTipoGrupo());

        helper.abrir();
        control = helper.getDb().insert("tipogrupo", null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje= context.getString(R.string.mnjErrorInsercion); //"Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            mensaje = mensaje+control;
        }
        return mensaje;
    }

    public boolean verificar(int accion, Context context){
        ControlBD helper = new ControlBD(context);
        String sql = "";
        Cursor cursor;
        helper.abrir();
        switch (accion){
            case 1:
                sql = "SELECT COUNT(IDTIPOGRUPO) FROM TIPOGRUPO WHERE NOMBRETIPOGRUPO= '" + getNombreTipoGrupo() + "'";
                cursor = helper.consultar(sql);
                if (cursor.moveToFirst())
                    if (cursor.getInt(0) > 0)
                        return true;
                break;
            case 2:
                sql = "SELECT COUNT(IDGRUPO) FROM GRUPO WHERE IDTIPOGRUPO =" + getIdTipoGrupo();
                cursor = helper.consultar(sql);
                if (cursor.moveToFirst())
                    if(cursor.getInt(0) > 0)
                        return true;
        }
        helper.cerrar();
        return false;
    }
}
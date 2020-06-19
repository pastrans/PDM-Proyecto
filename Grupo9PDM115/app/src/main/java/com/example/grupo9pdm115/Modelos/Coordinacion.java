package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class Coordinacion  extends TablaBD {

    // Atributos para BD
    private final String nombreTabla = "coordinacion";
    private ContentValues valores = new ContentValues();


    // Atributos
    private int idCoodinacion;
    private int idCicloMateria;
    private String idUsuario;
    private String tipoCoordinacion;
    public Coordinacion() {
        setNombreTabla("coordinacion");
        setNombreLlavePrimaria("idcoordinacion");
        setCamposTabla(new String[]{"idcoordinacion","idusuario", "idciclomateria","tipocoordinacion" });
    }

    @Override
    public String getNombreTabla() {
        return nombreTabla;
    }

    public ContentValues getValores() {
        return valores;
    }

    public void setValores(ContentValues valores) {
        this.valores = valores;
    }

    public int getIdCoodinacion() {
        return idCoodinacion;
    }

    public void setIdCoodinacion(int idCoodinacion) {
        this.idCoodinacion = idCoodinacion;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoCoordinacion() {
        return tipoCoordinacion;
    }

    public void setTipoCoordinacion(String tipoCoordinacion) {
        this.tipoCoordinacion = tipoCoordinacion;
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdCoodinacion(Integer.parseInt(arreglo[0]));
        setIdUsuario(arreglo[1]);
        setIdCicloMateria(Integer.parseInt(arreglo[2]));
        setTipoCoordinacion(arreglo[3]);

    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(getIdCoodinacion());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put(" idcoordinacion", getIdCoodinacion());
        this.valoresCamposTabla.put("idusuario", getIdUsuario());
        this.valoresCamposTabla.put("idciclomateria" , getIdCicloMateria());
        this.valoresCamposTabla.put("tipocoordinacion" , getTipoCoordinacion());

    }



    @Override
    public TablaBD getInstanceOfModel(String[] arreglo) {
        Coordinacion coordinacion = new Coordinacion();
        coordinacion.setAttributesFromArray(arreglo);
        return coordinacion;
    }
    public boolean verificarRegistro (Context context, int idCiclomateria, String tipo){
        boolean resultado = false;
        ControlBD helper = new ControlBD(context);
        String consulta = "SELECT * FROM " + this.getNombreTabla() + " WHERE "
                + " idciclomateria  = "+idCiclomateria +  " and tipocoordinacion = "+"\"" +tipo +"\"" ;

        Log.i("Coordinacion", "se hizo la consulta");
        helper.abrir();
        Cursor cursor = helper.consultar(consulta);
        if(cursor.moveToNext()){
            Log.i("Coordinacion", "Se encontro registo: ");
            resultado = true;
        }
        Log.i("Coordinacion", "me retorna: " + resultado);
        helper.cerrar();

        return resultado;
    }

    // Obtener todos los registros de la tabla
    public List<Coordinacion> getAllFiltered1(Context context, String filtro){
        List<Coordinacion> listaTablaBD = new ArrayList<>();
        ControlBD helper = new ControlBD(context);
        String[] valores = new String[getCamposTabla().length];

        String consulta = "select c.idcoordinacion, c.idusuario, c.idciclomateria,  c.tipocoordinacion from coordinacion c, ciclomateria cm" +
                " where cm.idciclomateria = c.idciclomateria and cm.codmateria like '%" + filtro+ "%'";

        helper.abrir();
        Cursor cursor = helper.consultar(consulta);

        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < getCamposTabla().length; i++){
                    valores[i] = cursor.getString(i);
                }
                listaTablaBD.add((Coordinacion) getInstanceOfModel(valores) );
            }while (cursor.moveToNext());
        }

        helper.cerrar();

        return listaTablaBD;
    }

    @Override
    public String guardar(Context context){
        String mensaje = context.getString(R.string.mnjRegInsert); // "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        //this.valoresCamposTabla.put("idcoordinacion", getIdCoodinacion());
        this.valoresCamposTabla.put("idusuario", getIdUsuario());
        this.valoresCamposTabla.put("idciclomateria", getIdCicloMateria());
        this.valoresCamposTabla.put("tipocoordinacion", getTipoCoordinacion());


        helper.abrir();
        control = helper.getDb().insert("coordinacion", null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje= context.getString(R.string.mnjErrorInsercion); // "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            mensaje = mensaje+control;
        }

        return mensaje;
    }
}

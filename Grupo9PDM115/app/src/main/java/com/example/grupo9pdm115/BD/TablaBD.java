package com.example.grupo9pdm115.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.Modelos.Grupo;

import java.util.ArrayList;
import java.util.List;

public abstract class TablaBD <ChildClass extends TablaBD> {
    // Atributos BD
    private String nombreTabla;
    private String nombreLlavePrimaria;
    private String[] camposTabla;
    protected ContentValues valoresCamposTabla = new ContentValues();

    // Métodos BD
    // Nombre tabla
    public String getNombreTabla() { return nombreTabla; }
    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    // Nombre llave primaria
    public String getNombreLlavePrimaria() { return nombreLlavePrimaria; }
    public void setNombreLlavePrimaria(String nombreLlavePrimaria) {
        this.nombreLlavePrimaria = nombreLlavePrimaria;
    }

    // Campos tabla
    public String[] getCamposTabla() {
        return camposTabla;
    }
    public void setCamposTabla(String[] camposTabla) {
        this.camposTabla = camposTabla;
    }

    // Valores campos tabla
    public ContentValues getValoresCamposTabla() {
        setValoresCamposTabla();
        return valoresCamposTabla;
    }

    // Métodos a sobreescribir
    public abstract String getValorLlavePrimaria();
    public abstract void setValoresCamposTabla();
    public abstract void setAttributesFromArray(String[] arreglo);
    public abstract ChildClass getInstanceOfModel(String[] arreglo);

    // Inserción
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);

        helper.abrir();
        control = helper.insertar2(this.getNombreTabla(), this.getValoresCamposTabla());
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

    // Actualización
    public String actualizar(Context context){
        String mensaje;
        int control = 0;
        ControlBD helper = new ControlBD(context);

        helper.abrir();
        control = helper.actualizar(this.getNombreTabla(), this.getValoresCamposTabla(),
                this.getNombreLlavePrimaria(), this.getValorLlavePrimaria());
        helper.cerrar();

        if(control == 0)
            mensaje = "Registro no existente.";
        else
            mensaje = "Registro actualizado correctamente.";

        return mensaje;
    }

    // Eliminación
    public String eliminar(Context context){
        String mensaje = "";
        int control = 0;
        ControlBD helper = new ControlBD(context);

        helper.abrir();
        control = helper.eliminar(this.getNombreTabla(), this.getNombreLlavePrimaria(),
                this.getValorLlavePrimaria());
        helper.cerrar();

        if(control == 0)
            mensaje = "Registro no existente.";
        else
        if(control == 1)
            mensaje = "Registro eliminado correctamente.";
        else
            mensaje = "Filas afectadas = " + control;

        return mensaje;
    }

    // Consulta
    public boolean consultar (Context context, String valorLlavePrimaria){
        boolean resultado = false;
        String[] valores = new String[getCamposTabla().length];
        ControlBD helper = new ControlBD(context);

        helper.abrir();
        Cursor cursor = helper.consultar(this.getNombreTabla(), this.getCamposTabla(),
                this.getNombreLlavePrimaria(), valorLlavePrimaria);


        if(cursor.moveToFirst()){
            for(int i = 0; i < getCamposTabla().length; i++){
                valores[i] = cursor.getString(i);
            }
            this.setAttributesFromArray(valores);
            resultado = true;
        }

        helper.cerrar();

        return resultado;
    }

    // Obtener todos los registros de la tabla
    public List<ChildClass> getAll(Context context){
        List<ChildClass> listaTablaBD = new ArrayList<>();
        ControlBD helper = new ControlBD(context);
        String[] valores = new String[getCamposTabla().length];

        helper.abrir();
        Cursor cursor = helper.getAll(this.getNombreTabla(), this.getCamposTabla());

        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < getCamposTabla().length; i++){
                    valores[i] = cursor.getString(i);
                }
                listaTablaBD.add(this.getInstanceOfModel(valores));
            }while (cursor.moveToNext());
        }

        helper.cerrar();

        return listaTablaBD;
    }

    // Obtener todos los registros de la tabla
    public List<ChildClass> getAllFiltered(Context context, String columna, String filtro){
        List<ChildClass> listaTablaBD = new ArrayList<>();
        ControlBD helper = new ControlBD(context);
        String[] valores = new String[getCamposTabla().length];

        String consulta = "SELECT * FROM " + this.getNombreTabla() + " WHERE "
                + columna + " LIKE '%"+filtro+"%'";

        helper.abrir();
        Cursor cursor = helper.consultar(consulta);

        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < getCamposTabla().length; i++){
                    valores[i] = cursor.getString(i);
                }
                listaTablaBD.add(this.getInstanceOfModel(valores));
            }while (cursor.moveToNext());
        }

        helper.cerrar();

        return listaTablaBD;
    }

}

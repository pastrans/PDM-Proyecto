package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

public class CicloMateria extends TablaBD {
    // Atributos
    private int idCicloMateria;
    private int idCiclo;
    private String codMateria;

    // Métodos
    public CicloMateria (){

        setNombreTabla("CICLOMATERIA");
        setNombreLlavePrimaria("IDCICLOMATERIA");
        setCamposTabla(new String[]{"IDCICLOMATERIA", "IDCICLO", "CODMATERIA"});
    }

    public CicloMateria(int idCicloMateria, int idCiclo, String codMateria) {
        this.idCicloMateria = idCicloMateria;
        this.idCiclo = idCiclo;
        this.codMateria = codMateria;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

    public int getIdCiclo() {
        return idCiclo;
    }

    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }

    public String getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(String codMateria) {
        this.codMateria = codMateria;
    }

    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(getIdCicloMateria());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        this.valoresCamposTabla.put("idCiclo", getIdCiclo());
        this.valoresCamposTabla.put("codMateria", getCodMateria());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdCicloMateria(Integer.valueOf(arreglo[0]));
        setIdCiclo(Integer.valueOf(arreglo[1]));
        setCodMateria(arreglo[2]);
    }

    @Override
    public CicloMateria getInstanceOfModel(String[] arreglo) {
        CicloMateria cicloMateria = new CicloMateria();
        cicloMateria.setAttributesFromArray(arreglo);
        return cicloMateria;
    }

    @Override
    public String guardar(Context context){
        String mensaje = context.getString(R.string.mnjRegInsert); // "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("IDCICLO", getIdCiclo());
        this.valoresCamposTabla.put("CODMATERIA", getCodMateria());


        helper.abrir();
        control = helper.getDb().insert("CICLOMATERIA", null, valoresCamposTabla);
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
    public boolean verificarRegistro (Context context, String codMateria, int idCiclo){
        boolean resultado = false;
        ControlBD helper = new ControlBD(context);
        String consulta = "SELECT * FROM " + this.getNombreTabla() + " WHERE "
                + "codmateria = "+ "\"" +codMateria +"\"" + " AND  "+ "idciclo = " +idCiclo;

        helper.abrir();
        Cursor cursor = helper.consultar(consulta);
        if(cursor.moveToNext()){
            resultado = true;
        }
        helper.cerrar();

        return resultado;
    }



    @Override
    public String toString(){
        return getIdCicloMateria() + " " + getIdCiclo() + " " + getCodMateria() ;
    }

    public boolean consultar (Context context, String codMateria, int idCiclo){
        boolean resultado = false;
        ControlBD helper = new ControlBD(context);
        String[] valores = new String[getCamposTabla().length];
        String consulta = "SELECT * FROM " + this.getNombreTabla() + " WHERE "
                + "codmateria = "+ "\"" +codMateria +"\"" + " AND  "+ "idciclo = " +idCiclo;

        helper.abrir();
        Cursor cursor = helper.consultar(consulta);
        if(cursor.moveToNext()){
            for(int i = 0; i < getCamposTabla().length; i++){
                valores[i] = cursor.getString(i);
            }
            this.setAttributesFromArray(valores);
            resultado = true;
        }
        helper.cerrar();

        return resultado;
    }
}

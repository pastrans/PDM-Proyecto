package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

import java.util.ArrayList;
import java.util.List;

public class Feriado extends TablaBD {
    // Atributos para BD
    private final String nombreTabla = "feriados";
    private ContentValues valores = new ContentValues();

    private int idFeriado;
    private int idCiclo;
    private String fechaInicioFeriado;
    private String fechaFinFeriado;
    private String nombreFeriado;
    private String descripcionFeriado;
    private boolean bloquearReservas;

    private ControlBD helper;

    //Constructores
    public Feriado() {
        setNombreTabla("feriados");
        setNombreLlavePrimaria("idferiado");
        setCamposTabla(new String[]{"idferiado", "idCiclo", "fechainicioferiado", "fechafinferiado", "nombreferiado", "descripcionferiado","bloquearreservas"});
    }

    public Feriado(int idFeriado, int idCiclo,String fechaInicioFeriado, String fechaFinFeriado, String nombreFeriado, String descripcionFeriado, boolean bloquearReservas) {
        this.idFeriado= idFeriado;
        this.idCiclo = idCiclo;
        this.fechaInicioFeriado = fechaInicioFeriado;
        this.fechaFinFeriado = fechaFinFeriado;
        this.nombreFeriado = nombreFeriado;
        this.descripcionFeriado = descripcionFeriado;
        this.bloquearReservas = bloquearReservas;
    }
    //idFeriado
    public int getIdFeriado() {
        return idFeriado;
    }
    public void setIdFeriado(int idFeriado) {
        this.idFeriado = idFeriado;
    }
    //idCiclo
    public int getIdCiclo() {
        return idCiclo;
    }
    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }
    //fechaInicioFeriado
    public String getFechaInicioFeriado() {
        return fechaInicioFeriado;
    }
    public void setFechaInicioFeriado(String fechaInicioFeriado) {
        this.fechaInicioFeriado = fechaInicioFeriado;
    }
    //fechaFinFeriado
    public String getFechaFinFeriado() {
        return fechaFinFeriado;
    }
    public void setFechaFinFeriado(String fechaFinFeriado) {
        this.fechaFinFeriado = fechaFinFeriado;
    }
    //nombreFeriado
    public String getNombreFeriado() {
        return nombreFeriado;
    }
    public void setNombreFeriado(String nombreFeriado) {
        this.nombreFeriado = nombreFeriado;
    }
    //descripcionFeriado
    public String getDescripcionFeriado() {
        return descripcionFeriado;
    }
    public void setDescripcionFeriado(String descripcionFeriado) { this.descripcionFeriado = descripcionFeriado;
    }
    //bloquearReservas
    public boolean isBloquearReservas() { return bloquearReservas; }
    public void setBloquearReservas(boolean bloquearReservas) { this.bloquearReservas = bloquearReservas; }


    @Override
    public String getValorLlavePrimaria(){
        return Integer.toString(this.getIdFeriado());
    }

    @Override
    public void setValoresCamposTabla(){
        this.valoresCamposTabla.put("idferiado", getIdFeriado());
        this.valoresCamposTabla.put("idciclo", getIdCiclo());
        this.valoresCamposTabla.put("fechainicioferiado", getFechaInicioFeriado());
        this.valoresCamposTabla.put("fechafinferiado",getFechaFinFeriado());
        this.valoresCamposTabla.put("nombreferiado", getNombreFeriado());
        this.valoresCamposTabla.put("descripcion", getDescripcionFeriado());
        this.valoresCamposTabla.put("bloquearreservas", isBloquearReservas());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdFeriado(Integer.parseInt(arreglo[0]));
        setIdCiclo(Integer.parseInt(arreglo[1]));
        setFechaInicioFeriado(arreglo[2]);
        setFechaFinFeriado(arreglo[3]);
        setNombreFeriado(arreglo[4]);
        setDescripcionFeriado(arreglo[5]);
        setBloquearReservas(Boolean.parseBoolean(arreglo[6]));
    }
    @Override
    public Feriado getInstanceOfModel(String[] arreglo) {
        Feriado feriado = new Feriado();
        feriado.setAttributesFromArray(arreglo);
        return feriado;
    }

    // Métodos para BD
    public String getNombreTabla() { return nombreTabla; }

    public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("idCiclo", getIdCiclo());
        valores.put("fechaInicioFeriado", getFechaInicioFeriado());
        valores.put("fechaFinFeriado", getFechaFinFeriado());
        valores.put("nombreFeriado", getNombreFeriado());
        valores.put("descripcionFeriado", getDescripcionFeriado());
        valores.put("bloquearReservas", isBloquearReservas());

        return valores;
    }


    public List<Ciclo> getCiclos(Context context){
        helper = new ControlBD(context);
        List<Ciclo> listaCiclo = new ArrayList<Ciclo>();
        //insertarCiclo(context);
        helper.abrir();
        Cursor cursor = helper.getDb().rawQuery("SELECT idCiclo, nombreCiclo FROM ciclo", null);
        if (cursor.moveToFirst()){
            do {
                Ciclo ciclo = new Ciclo();
                ciclo.setIdCiclo(cursor.getInt(0));
                ciclo.setNombreCiclo(cursor.getString(1));
                listaCiclo.add(ciclo);
            }while (cursor.moveToNext());
        }
        helper.cerrar();
        return listaCiclo;
    }

    public void insertarCiclo(Context context){
        helper = new ControlBD(context);
        ContentValues ciclo1 = new ContentValues();
        ciclo1.put("nombreCiclo", "I-2020");
        ciclo1.put("inicioPeriodoClase","2020/02/20");
        ciclo1.put("finPeriodoClase","2020/06/15");
        ciclo1.put("inicio","2020/01/17");
        ciclo1.put("fin","2020/07/31");
        ciclo1.put("estadoCiclo",true);
        ContentValues ciclo2 = new ContentValues();
        ciclo2.put("nombreCiclo", "II-2020");
        ciclo2.put("inicioPeriodoClase","2020/08/08");
        ciclo2.put("finPeriodoClase","2020/12/15");
        ciclo2.put("inicio","2020/08/01");
        ciclo2.put("fin","2021/01/15");
        ciclo2.put("estadoCiclo",false);
        helper.abrir();
        helper.getDb().insert("ciclo", null, ciclo1);
        helper.getDb().insert("ciclo", null, ciclo2);
        helper.cerrar();
    }

    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("idciclo", getIdCiclo());
        this.valoresCamposTabla.put("fechainicioferiado", getFechaInicioFeriado());
        this.valoresCamposTabla.put("fechafinferiado", getFechaFinFeriado());
        this.valoresCamposTabla.put("nombreferiado", getNombreFeriado());
        this.valoresCamposTabla.put("descripcionferiado", getDescripcionFeriado());
        this.valoresCamposTabla.put("bloquearreservas", isBloquearReservas());

        helper.abrir();
        control = helper.getDb().insert("feriados", null, valoresCamposTabla);
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


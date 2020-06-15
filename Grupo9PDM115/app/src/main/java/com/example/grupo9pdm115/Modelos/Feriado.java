package com.example.grupo9pdm115.Modelos;

import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.Utilidades.FechasHelper;

public class Feriado extends TablaBD {
    // Constantes
    private final String BLOQUEADAS = "Bloqueadas";
    private final String PERMITIDAS = "Permitidas";

    // Atributos
    private int idFeriado;
    private int idCiclo;
    private String fechaInicioFeriado;
    private String fechaFinFeriado;
    private String nombreFeriado;
    private String descripcionFeriado;
    private boolean bloquearReservas;

    //Constructores
    public Feriado() {
        setNombreTabla("feriado");
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
    public String getFechaInicioFeriadoToLocal() {
        return FechasHelper.cambiarFormatoIsoALocal(fechaInicioFeriado);
    }
    public void setFechaInicioFeriadoFromLocal(String fechaInicioFeriado) {
        this.fechaInicioFeriado = FechasHelper.cambiarFormatoLocalAIso(fechaInicioFeriado);
    }

    //fechaFinFeriado
    public String getFechaFinFeriado() {
        return fechaFinFeriado;
    }
    public void setFechaFinFeriado(String fechaFinFeriado) {
        this.fechaFinFeriado = fechaFinFeriado;
    }
    public String getFechaFinFeriadoToLocal() {
        return FechasHelper.cambiarFormatoIsoALocal(fechaFinFeriado);
    }
    public void setFechaFinFeriadoToLocal(String fechaFinFeriado) {
        this.fechaFinFeriado = FechasHelper.cambiarFormatoLocalAIso(fechaFinFeriado);
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
    //
    public String getBloquearReservasToText(){
        if(isBloquearReservas())
            return BLOQUEADAS;
        else
            return PERMITIDAS;
    }
    public void setBloquearReservas(boolean bloquearReservas) { this.bloquearReservas = bloquearReservas; }
    /* Para ingresar el valor cuando se lee de la base de datos donde puede tener el valor
        "1" (true) y "0" (false) */
    public void setBloquearReservas(String bloquearReservas) {
        boolean estado = false;
        if(bloquearReservas.equals("1"))
            estado = true;
        else{
            if(bloquearReservas.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(bloquearReservas);
            }
        }
        this.bloquearReservas = estado;
    }
    /* Para ingresar el valor cuando se lee desde la aplicación, donde solo puede tener el valor de
       "Bloqueadas" (true) y "Permitidas" (false) */
    public boolean setBloquearReservasFromText(String bloquearReservas){
        boolean estado = false;

        if(bloquearReservas.equals(BLOQUEADAS)){
            this.bloquearReservas = true;
            estado = true;
        }
        else if(bloquearReservas.equals(PERMITIDAS)){
                this.bloquearReservas = false;
                estado = true;
        }

        return estado;
    }

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
        this.valoresCamposTabla.put("descripcionferiado", getDescripcionFeriado());
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
        setBloquearReservas(arreglo[6]);
    }
    @Override
    public Feriado getInstanceOfModel(String[] arreglo) {
        Feriado feriado = new Feriado();
        feriado.setAttributesFromArray(arreglo);
        return feriado;
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


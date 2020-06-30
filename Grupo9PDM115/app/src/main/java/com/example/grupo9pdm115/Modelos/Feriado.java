package com.example.grupo9pdm115.Modelos;

import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Comun.FechasHelper;

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
    private boolean bloquearReservas; // true -> Reservas bloqueadas , false -> Reservas permitidas

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
    public void setFechaFinFeriadoFromLocal(String fechaFinFeriado) {
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
        String mensaje =  context.getString(R.string.mnjRegInsert); // "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("idciclo", getIdCiclo());
        this.valoresCamposTabla.put("fechainicioferiado", getFechaInicioFeriado());
        this.valoresCamposTabla.put("fechafinferiado", getFechaFinFeriado());
        this.valoresCamposTabla.put("nombreferiado", getNombreFeriado());
        this.valoresCamposTabla.put("descripcionferiado", getDescripcionFeriado());
        this.valoresCamposTabla.put("bloquearreservas", isBloquearReservas());

        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje= context.getString(R.string.mnjErrorInsercion);
        }
        else {
            mensaje = mensaje+control;
        }

        return mensaje;
    }

    /* Método para validar los campos del feriado
        Parámetros:
        context -> el contexto de la aplicación
        caso -> 1 si fecha única
        caso -> 0 si varias fechas
        Valores de retorno:
        0 -> Los campos están correctamente agregados
        1 -> Campos vacíos
        2 -> Fechas no están dentro de la duración del ciclo
        3 -> Fecha fin no es posterior a fecha inicio
     */
    public int verificarCampos(Context context, int caso){
        Ciclo ciclo = new Ciclo();
        ciclo.consultar(context, Integer.toString(getIdCiclo()));

        // Fechas de inicio y fin
        if(caso == 0){
            // Campos vacíos
            if(getNombreFeriado().equals("") || getDescripcionFeriado().equals("") || getFechaInicioFeriado().equals("")
                    || getFechaFinFeriado().equals("") || getIdCiclo() < 0) {
                return 1;
            }
            // Fechas durante ciclo
            if(!FechasHelper.fechaEstaEnmedio(ciclo.getInicio(), ciclo.getFin(), getFechaInicioFeriado())
                    || !FechasHelper.fechaEstaEnmedio(ciclo.getInicio(), ciclo.getFin(), getFechaFinFeriado())){
                return 2;
            }
            // Fecha fin posterior a fecha inicio
            if(!FechasHelper.fechaEsPosterior(getFechaInicioFeriado(), getFechaFinFeriado())){
                return 3;
            }
        }

        // Fecha única
        if(caso == 1){
            // Campos vacíos
            if(getNombreFeriado().equals("") || getDescripcionFeriado().equals("") || getFechaInicioFeriado().equals("")
                    || getIdCiclo() < 0) {
                return 1;
            }
            // Fechas durante ciclo
            if(!FechasHelper.fechaEstaEnmedio(ciclo.getInicio(), ciclo.getFin(), getFechaInicioFeriado())){
                return 2;
            }
        }

        // No hay error
        return 0;
    }
}


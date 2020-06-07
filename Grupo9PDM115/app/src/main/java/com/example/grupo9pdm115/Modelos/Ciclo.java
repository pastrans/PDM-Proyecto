package com.example.grupo9pdm115.Modelos;

import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class Ciclo extends TablaBD {
    // Atributos
    private int idCiclo;
    private String inicio;
    private String fin;
    private String nombreCiclo;
    private boolean estadoCiclo;
    private String inicioPeriodoClase;
    private String finPeriodoClase;

    // Métodos
    //Constructores
    public Ciclo(){
        setNombreTabla("ciclo");
        setNombreLlavePrimaria("idciclo");
        setCamposTabla(new String[]{"idciclo", "inicio","fin","nombreciclo","estadociclo","inicioperiodoclase","finperiodoclase"});
    }

    public Ciclo(int idCiclo, String inicio, String fin, String nombreCiclo, boolean estadoCiclo, String inicioPeriodoClase, String finPeriodoClase) {
        this.idCiclo = idCiclo;
        this.inicio = inicio;
        this.fin = fin;
        this.nombreCiclo = nombreCiclo;
        this.estadoCiclo = estadoCiclo;
        this.inicioPeriodoClase = inicioPeriodoClase;
        this.finPeriodoClase = finPeriodoClase;
    }

    //idCiclo
    public int getIdCiclo() {
        return idCiclo;
    }
    public void setIdCiclo(int idCiclo) {
        this.idCiclo = idCiclo;
    }
    //Inicio
    public String getInicio() {
        return inicio;
    }
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }
    //Fin
    public String getFin() {
        return fin;
    }
    public void setFin(String fin) {
        this.fin = fin;
    }
    //NombreCiclo
    public String getNombreCiclo() {
        return nombreCiclo;
    }
    public void setNombreCiclo(String nombreCiclo) {
        this.nombreCiclo = nombreCiclo;
    }
    //EstadoCiclo
    public boolean isEstadoCiclo() {
        return estadoCiclo;
    }
    public void setEstadoCiclo(boolean estadoCiclo) {
        this.estadoCiclo = estadoCiclo;
    }
    //InicioPeriodoClase
    public String getInicioPeriodoClase() {
        return inicioPeriodoClase;
    }
    public void setInicioPeriodoClase(String inicioPeriodoClase) {
        this.inicioPeriodoClase = inicioPeriodoClase;
    }
    //FinPeriodoClase
    public String getFinPeriodoClase() {
        return finPeriodoClase;
    }
    public void setFinPeriodoClase(String finPeriodoClase) {
        this.finPeriodoClase = finPeriodoClase;
    }
    @Override
    public String getValorLlavePrimaria() {
        return Integer.toString(this.getIdCiclo());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idciclo", getIdCiclo());
        this.valoresCamposTabla.put("inicio", getInicio());
        this.valoresCamposTabla.put("fin" , getFin());
        this.valoresCamposTabla.put("nombreciclo" , getNombreCiclo());
        this.valoresCamposTabla.put("estadociclo" , isEstadoCiclo());
        this.valoresCamposTabla.put("inicioperiodoclase" , getInicioPeriodoClase());
        this.valoresCamposTabla.put("finperiodoclase" , getFinPeriodoClase());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdCiclo(Integer.parseInt(arreglo[0]));
        setInicio(arreglo[1]);
        setFin(arreglo[2]);
        setNombreCiclo(arreglo[3]);
        setEstadoCiclo(Boolean.parseBoolean(arreglo[4]));
        setInicioPeriodoClase(arreglo[5]);
        setFinPeriodoClase(arreglo[6]);
    }
    @Override
    public Ciclo getInstanceOfModel(String[] arreglo) {
        Ciclo ciclo = new Ciclo();
        ciclo.setAttributesFromArray(arreglo);
        return ciclo;
    }
    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("inicio", getInicio());
        this.valoresCamposTabla.put("fin", getFin());
        this.valoresCamposTabla.put("nombreciclo", getNombreCiclo());
        this.valoresCamposTabla.put("estadociclo", isEstadoCiclo());
        this.valoresCamposTabla.put("inicioperiodoclase", getInicioPeriodoClase());
        this.valoresCamposTabla.put("finperiodoclase", getFinPeriodoClase());

        helper.abrir();
        control = helper.getDb().insert("ciclo", null, valoresCamposTabla);
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
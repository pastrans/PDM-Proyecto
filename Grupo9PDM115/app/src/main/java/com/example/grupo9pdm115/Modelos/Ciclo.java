package com.example.grupo9pdm115.Modelos;

import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;
import com.example.grupo9pdm115.Comun.FechasHelper;

public class Ciclo extends TablaBD {
    // Atributos
    private int idCiclo;

    private String nombreCiclo;
    private boolean estadoCiclo;
    private String inicio;
    private String fin;
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
    public void setEstadoCiclo(String estadoCiclo) {
        boolean estado = false;
        if(estadoCiclo.equals("1"))
            estado = true;
        else{
            if(estadoCiclo.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(estadoCiclo);
            }
        }
        this.estadoCiclo = estado;
    }

    //Inicio
    public String getInicio() {
        return inicio;
    }
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getInicioToLocal() {
        return FechasHelper.cambiarFormatoIsoALocal(inicio);
    }
    public void setInicioFromLocal(String inicio) {
        this.inicio = FechasHelper.cambiarFormatoLocalAIso(inicio);
    }

    //Fin
    public String getFin() {
        return fin;
    }
    public void setFin(String fin) {
        this.fin = fin;
    }
    public String getFinToLocal() {
        return FechasHelper.cambiarFormatoIsoALocal(fin);
    }
    public void setFinFromLocal(String fin) {
        this.fin = FechasHelper.cambiarFormatoLocalAIso(fin);
    }

    //InicioPeriodoClase
    public String getInicioPeriodoClase() {
        return inicioPeriodoClase;
    }
    public void setInicioPeriodoClase(String inicioPeriodoClase) {
        this.inicioPeriodoClase = inicioPeriodoClase;
    }

    public String getInicioPeriodoClaseToLocal() {
        return FechasHelper.cambiarFormatoIsoALocal(inicioPeriodoClase);
    }
    public void setInicioPeriodoClaseFromLocal(String inicioPeriodoClase) {
        this.inicioPeriodoClase = FechasHelper.cambiarFormatoLocalAIso(inicioPeriodoClase);
    }

    //FinPeriodoClase
    public String getFinPeriodoClase() {
        return finPeriodoClase;
    }
    public void setFinPeriodoClase(String finPeriodoClase) {
        this.finPeriodoClase = finPeriodoClase;
    }
    public String getFinPeriodoClaseToLocal() {
        return FechasHelper.cambiarFormatoIsoALocal(finPeriodoClase);
    }
    public void setFinPeriodoClaseFromLocal(String finPeriodoClase) {
        this.finPeriodoClase = FechasHelper.cambiarFormatoLocalAIso(finPeriodoClase);
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
        setEstadoCiclo(arreglo[4]);
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
        String mensaje = context.getString(R.string.mnjRegInsert); // "Registro insertado N° = ";
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
            mensaje= context.getString(R.string.mnjErrorInsercion); // "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            mensaje = mensaje+control;
        }

        return mensaje;
    }

    @Override
    public String toString(){
        return getIdCiclo() + " " + getInicio() + " " + getFin() + " " +
                getInicioPeriodoClase() + " " + getFinPeriodoClase() + " " + isEstadoCiclo();
    }

    /*
        Descripción:
            Método que actualiza el estado del ciclo a activo (true), dejando el resto de ciclos como
            inactivos (false).
        Parámetros:
            > contexto : contexto de la aplicación
        Valores de retorno:
            > -1 : el ciclo ya está activo
            > 0 : No existe el registro
            > 1 : Ciclo activado
     */
    public int activarCiclo(Context context){
        int control = 0;

        ControlBD helper = new ControlBD(context);
        helper.abrir();

        // Verificando que el ciclo no esté activo, si lo está, devolver -1
        if(isEstadoCiclo()){
            return -1;
        }

        // Actualizar todos los ciclos a estado inactivo (false -> 0)
        valoresCamposTabla.put("estadociclo", 0);
        control = helper.getDb().update(getNombreTabla(), valoresCamposTabla, "estadociclo = 1", null);

        // Estableciendo ciclo actual como activo (true -> 1)
        valoresCamposTabla.remove("estadociclo");
        valoresCamposTabla.put("estadociclo", 1);
        String[] whereArgs = {getValorLlavePrimaria()};
        // Si no hay existe el registro retorno valdrá 0 porque no habrán registros afectados
        // Si hace el cambio valdrá 1, porque solo debe actualizar a un registro
        control = helper.getDb().update(getNombreTabla(), valoresCamposTabla, getNombreLlavePrimaria()+" = ?", whereArgs);

        helper.cerrar();

        return control;
    }

}
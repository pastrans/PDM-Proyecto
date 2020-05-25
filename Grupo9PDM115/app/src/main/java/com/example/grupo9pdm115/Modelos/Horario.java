package com.example.grupo9pdm115.Modelos;
import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

import java.util.Date;
import java.lang.System;

public class Horario extends TablaBD {
    private int idHora;
    private String horaInicio;
    private String horaFinal;
    public Horario(){
        setNombreTabla("horario");
        setNombreLlavePrimaria("idhora");
        setCamposTabla(new String[]{"idhora", "horainicio","horafinal"});
    }

    public Horario(int idHora, String horaInicio, String horaFinal) {
        this.idHora = idHora;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }
    @Override
    public String getValorLlavePrimaria() {

        return Integer.toString(this.getIdHora());
    }
    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idhorario", getIdHora());
        this.valoresCamposTabla.put("horainicio",getHoraInicio());
        this.valoresCamposTabla.put("horafinal", getHoraFinal());

    }
    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdHora(Integer.parseInt(arreglo[0]));
        setHoraInicio(arreglo[1]);
        setHoraFinal(arreglo[2]);
    }
    @Override
    public Horario getInstanceOfModel(String[] arreglo) {
        Horario horario = new Horario();
        horario.setAttributesFromArray(arreglo);
        return horario;
    }
    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("horainicio", getHoraInicio());
        this.valoresCamposTabla.put("horafinal", getHoraFinal());

        helper.abrir();
        control = helper.getDb().insert("horario", null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje= "Error al insertar el registro, registro duplicado. Verificar inserción.";
        }
        else {
            return mensaje;
        }

        return mensaje;
    }
}

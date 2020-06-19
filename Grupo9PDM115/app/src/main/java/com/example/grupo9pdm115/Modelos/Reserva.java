package com.example.grupo9pdm115.Modelos;

import android.content.Context;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

public class Reserva extends TablaBD {

    private int idReserva;
    private int idDetalleResera;
    private int idSolicitud;

    public Reserva() {
        setNombreTabla("reserva");
        setNombreLlavePrimaria("idReserva");
        setCamposTabla(new String[]{"idResera", "idDetalleReserva", "idSolicitud"});
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdDetalleResera() {
        return idDetalleResera;
    }

    public void setIdDetalleResera(int idDetalleResera) {
        this.idDetalleResera = idDetalleResera;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(getIdReserva());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idReserva", getIdReserva());
        this.valoresCamposTabla.put("idDetalleReserva", getIdDetalleResera());
        this.valoresCamposTabla.put("idSolicitud", getIdSolicitud());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdReserva(Integer.valueOf(arreglo[0]));
        setIdDetalleResera(Integer.valueOf(arreglo[1]));
        setIdSolicitud(Integer.valueOf(arreglo[2]));
    }

    @Override
    public Reserva getInstanceOfModel(String[] arreglo) {
        Reserva r = new Reserva();
        r.setAttributesFromArray(arreglo);
        return r;
    }

    @Override
    public String guardar(Context context) {
        String mensaje = context.getString(R.string.mnjRegInsertExit); //"Se ha insertado el registro con éxito. ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("idDetalleReserva", getIdDetalleResera());
        this.valoresCamposTabla.put("idSolicitud", getIdSolicitud());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0) {
            mensaje= context.getString(R.string.mnjErrorInsercion); //"Error al insertar el registro, registro duplicado. Verificar inserción.";
        }

        return mensaje;
    }

}

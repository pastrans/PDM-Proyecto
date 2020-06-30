package com.example.grupo9pdm115.Utilidades;

import com.example.grupo9pdm115.Modelos.DetalleReserva;

public class DetallesQR {

    private DetalleReserva detalleReserva;
    private String horaInicio;
    private String horaFinal;

    public DetallesQR(DetalleReserva detalleReserva, String horaInicio, String horaFinal) {
        this.detalleReserva = detalleReserva;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
    }

    public DetallesQR() {
    }

    public DetalleReserva getDetalleReserva() {
        return detalleReserva;
    }

    public void setDetalleReserva(DetalleReserva detalleReserva) {
        this.detalleReserva = detalleReserva;
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

}

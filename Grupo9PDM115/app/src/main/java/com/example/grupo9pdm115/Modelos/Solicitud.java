package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class Solicitud extends TablaBD {

    private int idSolicitud;
    private String idUsuario;
    private String idEncargado;
    private String asuntoSolicitud;
    private int tipoSolicitud;
    private String fechaRealizada;
    private boolean estadoSolicitud;
    private String fechaRespuesta;
    private String comentario;
    private String nuevoFinPeriodo;
    private boolean aprobadoTotal;
    private boolean mostrarBoton;

    public Solicitud() {
        setNombreTabla("solicitud");
        setNombreLlavePrimaria("idSolicitud");
        setCamposTabla(new String[]{"IDSOLICITUD", "IDUSUARIO", "IDENCARGADO", "ASUNTOSOLICITUD", "TIPOSOLICITUD", "FECHAREALIZADA", "ESTADOSOLICITUD", "FECHARESPUESTA", "COMETARIO", "NUEVOFINPERIODO", "APROBADOTOTAL", "MOSTRARBOTON"});
    }

    public Solicitud(int idSolicitud, String idUsuario, String idEncargado, String asuntoSolicitud, int tipoSolicitud, String fechaRealizada, boolean estadoSolicitud, String fechaRespuesta, String comentario, String nuevoFinPeriodo, boolean aprobadoTotal, boolean mostrarBoton) {
        this.idSolicitud = idSolicitud;
        this.idUsuario = idUsuario;
        this.idEncargado = idEncargado;
        this.asuntoSolicitud = asuntoSolicitud;
        this.tipoSolicitud = tipoSolicitud;
        this.fechaRealizada = fechaRealizada;
        this.estadoSolicitud = estadoSolicitud;
        this.fechaRespuesta = fechaRespuesta;
        this.comentario = comentario;
        this.nuevoFinPeriodo = nuevoFinPeriodo;
        this.aprobadoTotal = aprobadoTotal;
        this.mostrarBoton = mostrarBoton;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(String idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getAsuntoSolicitud() {
        return asuntoSolicitud;
    }

    public void setAsuntoSolicitud(String asuntoSolicitud) {
        this.asuntoSolicitud = asuntoSolicitud;
    }

    public int getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(int tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(String fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public boolean isEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(boolean estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        boolean estado = false;
        if(estadoSolicitud.equals("1"))
            estado = true;
        else{
            if(estadoSolicitud.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(estadoSolicitud);
            }
        }
        this.estadoSolicitud = estado;
    }

    public String getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(String fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNuevoFinPeriodo() {
        return nuevoFinPeriodo;
    }

    public void setNuevoFinPeriodo(String nuevoFinPeriodo) {
        this.nuevoFinPeriodo = nuevoFinPeriodo;
    }

    public boolean isAprobadoTotal() {
        return aprobadoTotal;
    }

    public void setAprobadoTotal(boolean aprobadoTotal) {
        this.aprobadoTotal = aprobadoTotal;
    }

    public void setAprobadoTotal(String aprobadoTotal) {
        boolean estado = false;
        if(aprobadoTotal.equals("1"))
            estado = true;
        else{
            if(aprobadoTotal.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(aprobadoTotal);
            }
        }
        this.aprobadoTotal = estado;
    }

    public boolean isMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
    }

    public void setMostrarBoton(String mostrarBoton) {
        boolean estado = false;
        if(mostrarBoton.equals("1"))
            estado = true;
        else{
            if(mostrarBoton.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(mostrarBoton);
            }
        }
        this.mostrarBoton = estado;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(this.getIdSolicitud());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idSolicitud", getIdSolicitud());
        this.valoresCamposTabla.put("IDUSUARIO",getIdUsuario());
        this.valoresCamposTabla.put("IDENCARGADO", getIdEncargado());
        this.valoresCamposTabla.put("ASUNTOSOLICITUD", getAsuntoSolicitud());
        this.valoresCamposTabla.put("TIPOSOLICITUD", getTipoSolicitud());
        this.valoresCamposTabla.put("FECHAREALIZADA", getFechaRealizada());
        this.valoresCamposTabla.put("ESTADOSOLICITUD", isEstadoSolicitud());
        this.valoresCamposTabla.put("FECHARESPUESTA", getFechaRespuesta());
        this.valoresCamposTabla.put("COMETARIO", getComentario());
        this.valoresCamposTabla.put("NUEVOFINPERIODO", getNuevoFinPeriodo());
        this.valoresCamposTabla.put("APROBADOTOTAL", isAprobadoTotal());
        this.valoresCamposTabla.put("MOSTRARBOTON", isMostrarBoton());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdSolicitud(Integer.valueOf(arreglo[0]));
        setIdUsuario(arreglo[1]);
        setIdEncargado(arreglo[2]);
        setAsuntoSolicitud(arreglo[3]);
        setTipoSolicitud(Integer.valueOf(arreglo[4]));
        setFechaRealizada(arreglo[5]);
        setEstadoSolicitud(arreglo[6]);
        setFechaRespuesta(arreglo[7]);
        setComentario(arreglo[8]);
        setNuevoFinPeriodo(arreglo[9]);
        setAprobadoTotal(arreglo[10]);
        setMostrarBoton(arreglo[11]);
    }

    @Override
    public Solicitud getInstanceOfModel(String[] arreglo) {
        Solicitud solicitud = new Solicitud();
        solicitud.setAttributesFromArray(arreglo);
        return solicitud;
    }


    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("IDUSUARIO",getIdUsuario());
        this.valoresCamposTabla.put("IDENCARGADO", getIdEncargado());
        this.valoresCamposTabla.put("ASUNTOSOLICITUD", getAsuntoSolicitud());
        this.valoresCamposTabla.put("TIPOSOLICITUD", getTipoSolicitud());
        this.valoresCamposTabla.put("FECHAREALIZADA", getFechaRealizada());
        this.valoresCamposTabla.put("ESTADOSOLICITUD", isEstadoSolicitud());
        if(!getFechaRespuesta().equals(""))
            this.valoresCamposTabla.put("FECHARESPUESTA", getFechaRespuesta());
        if(!getComentario().equals(""))
            this.valoresCamposTabla.put("COMETARIO", getComentario());
        if(!getNuevoFinPeriodo().equals(""))
            this.valoresCamposTabla.put("NUEVOFINPERIODO", getNuevoFinPeriodo());
        this.valoresCamposTabla.put("APROBADOTOTAL", isAprobadoTotal());
        this.valoresCamposTabla.put("MOSTRARBOTON", isMostrarBoton());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
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

    public int getLast(Context context){
        int lastId = 0;
        ControlBD helper = new ControlBD(context);
        Cursor cursor;
        String sql = "SELECT * FROM solicitud ORDER BY idSolicitud DESC LIMIT 1;";
        helper.abrir();
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            lastId = cursor.getInt(0);
        }
        helper.cerrar();
        return lastId;
    }

    public String getEnargadoLocal(Context context){
        String encargado = "";
        ControlBD helper = new ControlBD(context);
        Cursor cursor;
        String sql = "select tl.IDENCARGADO from TIPOLOCAL tl, LOCAL l, RESERVA r, DETALLERESERVA dr, SOLICITUD s\n" +
                "WHERE tl.IDTIPOLOCAL = l.IDTIPOLOCAL\n" +
                "AND l.IDLOCAL = dr.IDLOCAL\n" +
                "AND dr.IDDETALLERESERVA = r.IDDETALLERESERVA\n" +
                "AND r.IDSOLICITUD = s.IDSOLICITUD\n" +
                "AND s.IDSOLICITUD = " + this.getIdSolicitud() +"\n" +
                "LIMIT 1;";
        helper.abrir();
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            encargado = cursor.getString(0);
        }
        helper.cerrar();
        return encargado;
    }

    //método para la busqueda sobre escritura



}

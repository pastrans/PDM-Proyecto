package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class DetalleReserva extends TablaBD {

    private int idDetalleReserva;
    private int idDia;
    private int idHora;
    private int idLocal;
    private int idEventoEspecial;
    private int idGrupo;
    private boolean estadoReserva;
    private boolean aprobado;
    private int cupo;
    private String inicioPeriodoReserva;
    private String finPeriodoReserva;

    public DetalleReserva() {
        setNombreTabla("detalleReserva");
        setNombreLlavePrimaria("idDetalleReserva");
        setCamposTabla(new String[]{"idDetalleReserva", "idDia", "idHora", "idLocal", "idEventoEspecial", "idGrupo", "estadoReserva", "aprobado", "cupo", "inicioPeriodoReserva", "finPeriodoReserva"});
    }

    public DetalleReserva(int idDetalleReserva, int idDia, int idHora, int idLocal, int idEventoEspecial, int idGrupo, boolean estadoReserva, boolean aprobado, int cupo, String inicioPeriodoReserva, String finPeriodoReserva) {
        this.idDetalleReserva = idDetalleReserva;
        this.idDia = idDia;
        this.idHora = idHora;
        this.idLocal = idLocal;
        this.idEventoEspecial = idEventoEspecial;
        this.idGrupo = idGrupo;
        this.estadoReserva = estadoReserva;
        this.aprobado = aprobado;
        this.cupo = cupo;
        this.inicioPeriodoReserva = inicioPeriodoReserva;
        this.finPeriodoReserva = finPeriodoReserva;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(this.getIdDetalleReserva());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idDetalleReserva", getIdDetalleReserva());
        this.valoresCamposTabla.put("idDia", getIdDia());
        this.valoresCamposTabla.put("idHora", getIdHora());
        this.valoresCamposTabla.put("idLocal", getIdLocal());
        this.valoresCamposTabla.put("idEventoEspecial", getIdEventoEspecial());
        this.valoresCamposTabla.put("idGrupo", getIdGrupo());
        this.valoresCamposTabla.put("estadoReserva", isEstadoReserva());
        this.valoresCamposTabla.put("aprobado", isAprobado());
        this.valoresCamposTabla.put("cupo", getCupo());
        this.valoresCamposTabla.put("inicioPeriodoReserva", getInicioPeriodoReserva());
        this.valoresCamposTabla.put("finPeriodoReserva", getFinPeriodoReserva());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        String idEventoEspecial = arreglo[4];
        String idGrupo = arreglo[5];
        setIdDetalleReserva(Integer.valueOf(arreglo[0]));
        setIdDia(Integer.valueOf(arreglo[1]));
        setIdHora(Integer.valueOf(arreglo[2]));
        setIdLocal(Integer.valueOf(arreglo[3]));
        setIdEventoEspecial(Integer.valueOf(arreglo[4]));
        setIdGrupo(Integer.valueOf(arreglo[5]));
        setEstadoReserva(arreglo[6]);
        setAprobado(arreglo[7]);
        setCupo(Integer.valueOf(arreglo[8]));
        setInicioPeriodoReserva(arreglo[9]);
        setFinPeriodoReserva(arreglo[10]);
    }

    @Override
    public DetalleReserva getInstanceOfModel(String[] arreglo) {
        DetalleReserva detalleReserva = new DetalleReserva();
        detalleReserva.setAttributesFromArray(arreglo);
        return detalleReserva;
    }

    public int getIdDetalleReserva() {
        return idDetalleReserva;
    }

    public void setIdDetalleReserva(int idDetalleReserva) {
        this.idDetalleReserva = idDetalleReserva;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public int getIdHora() {
        return idHora;
    }

    public void setIdHora(int idHora) {
        this.idHora = idHora;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdEventoEspecial() {
        return idEventoEspecial;
    }

    public void setIdEventoEspecial(int idEventoEspecial) {
        this.idEventoEspecial = idEventoEspecial;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public boolean isEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(boolean estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        boolean estado = false;
        if(estadoReserva.equals("1"))
            estado = true;
        else{
            if(estadoReserva.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(estadoReserva);
            }
        }
        this.estadoReserva = estado;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public void setAprobado(String aprobado) {
        boolean estado = false;
        if(aprobado.equals("1"))
            estado = true;
        else{
            if(aprobado.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(aprobado);
            }
        }
        this.aprobado = estado;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getInicioPeriodoReserva() {
        return inicioPeriodoReserva;
    }

    public void setInicioPeriodoReserva(String inicioPeriodoReserva) {
        this.inicioPeriodoReserva = inicioPeriodoReserva;
    }

    public String getFinPeriodoReserva() {
        return finPeriodoReserva;
    }

    public void setFinPeriodoReserva(String finPeriodoReserva) {
        this.finPeriodoReserva = finPeriodoReserva;
    }

    @Override
    public String guardar(Context context){
        String mensaje = context.getString(R.string.mnjRegInsert); // "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("idDia", getIdDia());
        this.valoresCamposTabla.put("idHora", getIdHora());
        this.valoresCamposTabla.put("idLocal", getIdLocal());
        if(getIdEventoEspecial() != 0){
            this.valoresCamposTabla.put("idEventoEspecial", getIdEventoEspecial());
            this.valoresCamposTabla.put("idGrupo", 0);
        }
        if (getIdGrupo() != 0){
            this.valoresCamposTabla.put("idGrupo", getIdGrupo());
            this.valoresCamposTabla.put("idEventoEspecial", 0);
        }
        this.valoresCamposTabla.put("estadoReserva", isEstadoReserva());
        this.valoresCamposTabla.put("aprobado", isAprobado());
        this.valoresCamposTabla.put("cupo", getCupo());
        this.valoresCamposTabla.put("inicioPeriodoReserva", getInicioPeriodoReserva());
        this.valoresCamposTabla.put("finPeriodoReserva", getFinPeriodoReserva());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
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

    public int getLast(Context context){
        int lastId = 0;
        ControlBD helper = new ControlBD(context);
        Cursor cursor;
        String sql = "SELECT * FROM DetalleReserva ORDER BY idDetalleReserva DESC LIMIT 1;";
        helper.abrir();
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            lastId = cursor.getInt(0);
        }
        helper.cerrar();
        return lastId;
    }

    public List<DetalleReserva> getDetalleReservaSolicitud(Context context, int idSolicitud){
        ControlBD helper = new ControlBD(context);
        List<DetalleReserva> detalleReservas = new ArrayList<DetalleReserva>();
        helper.abrir();
        Cursor cursor;
        String[] valores = new String[getCamposTabla().length];
        String sql = "select dr.IDDETALLERESERVA, dr.IDDIA, dr.IDHORA, dr.IDLOCAL, dr.IDEVENTOESPECIAL, dr.IDGRUPO, dr.ESTADORESERVA, dr.APROBADO, dr.CUPO, dr.INICIOPERIODORESERVA, dr.FINPERIODORESERVA \n" +
                "from DETALLERESERVA dr, RESERVA r, SOLICITUD s, HORARIO h\n" +
                "where dr.IDDETALLERESERVA = r.IDDETALLERESERVA\n" +
                "and s.IDSOLICITUD = r.IDSOLICITUD\n" +
                "and dr.IDHORA = h.IDHORA\n" +
                "AND s.IDSOLICITUD = " + idSolicitud +"\n" +
                "ORDER BY h.HORAINICIO, dr.IDLOCAL;";
        cursor = helper.consultar(sql);
        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < getCamposTabla().length; i++){
                    valores[i] = cursor.getString(i);
                }
                detalleReservas.add(this.getInstanceOfModel(valores));
            }while (cursor.moveToNext());
        }
        return detalleReservas;
    }

    public String aprobar(Context context, List detalles){
        boolean seguir = true;
        String resultado = "";
        ControlBD helper = new ControlBD(context);
        //int control;
        helper.abrir();
        for(int i = 0; i < detalles.size(); i++){
            DetalleReserva detalle = (DetalleReserva) detalles.get(i);
            if(!validar(context, 1, detalle)){
                seguir = false;
                resultado = "";
            }
        }

        if(!seguir)
            return "";

        for(int i = 0; i< detalles.size(); i++){
            DetalleReserva detalle = (DetalleReserva) detalles.get(i);
            detalle.setAprobado(true);
            detalle.setEstadoReserva(true);
            detalle.valoresCamposTabla.put("aprobado", true);
            detalle.valoresCamposTabla.put("estadoReserva", true);
            String res = detalle.actualizar(context);
            //String res = "Registro no existente";
            //control = helper.actualizar(detalle.getNombreTabla(), detalle.getValoresCamposTabla(), detalle.getNombreLlavePrimaria(), detalle.getValorLlavePrimaria());
            if(res.equals(context.getString(R.string.mnjRegNoExiste))) //  "Registro no existente."
                resultado = "";
            else
                resultado = String.valueOf(detalle.isAprobado()); // true o false
        }
        helper.cerrar();
        return resultado;
    }


    public boolean validar(Context context, int opcion, DetalleReserva detalle){
        boolean result = false;
        ControlBD helper = new ControlBD(context);
        String sql = "";
        Cursor resp;
        helper.abrir();
        switch (opcion){
            case 1 :
                int count = 0;
                sql = "SELECT COUNT(de.IDLOCAL) FROM DETALLERESERVA de\n" +
                        "WHERE de.IDDIA = " + detalle.getIdDia() +"\n" +
                        "AND de.IDLOCAL = " + detalle.getIdLocal() + "\n" +
                        "AND de.IDHORA = " + detalle.getIdHora() + "\n" +
                        "AND (de.INICIOPERIODORESERVA = '" + detalle.getInicioPeriodoReserva() + "' OR '"+ detalle.getInicioPeriodoReserva() +"' BETWEEN de.INICIOPERIODORESERVA AND de.FINPERIODORESERVA)\n" +
                        "AND APROBADO = 1 AND ESTADORESERVA = 1";
                resp = helper.consultar(sql);
                resp.moveToFirst();
                if(resp.getInt(0) == 0)
                    result = true;
                else
                    result = false;
                break;
            case 2 :
                sql = "SELECT COUNT(f.IDFERIADO) FROM FERIADO f \n" +
                        "WHERE (('"+ detalle.getInicioPeriodoReserva() + "' BETWEEN f.FECHAINICIOFERIADO AND f.FECHAFINFERIADO)\n" +
                        "OR '"+ detalle.getFinPeriodoReserva() +"' BETWEEN f.FECHAFINFERIADO AND f.FECHAFINFERIADO)\n" +
                        "AND f.BLOQUEARRESERVAS = 1;";
                resp = helper.consultar(sql);
                resp.moveToFirst();
                if(resp.getInt(0) == 0)
                    result = true;
                else
                    result = false;
                break;
            case 3:
                sql = "SELECT COUNT(IDDETALLERESERVA) FROM DETALLERESERVA WHERE IDGRUPO = " + detalle.getIdGrupo() + " AND IDDIA = " + detalle.getIdDia();
                resp = helper.consultar(sql);
                resp.moveToFirst();
                if(resp.getInt(0) == 0)
                    result = true;
                else
                    result = false;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

}

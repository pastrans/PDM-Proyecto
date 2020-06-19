package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

public class EventoEspecial extends TablaBD {

    private int idEventoEspecial;
    private int idCicloMateria;
    private String nombreEvento;
    private String fechaEvento;
    private String descripcionEvento;

    public EventoEspecial() {
        setNombreTabla("eventoEspecial");
        setNombreLlavePrimaria("idEventoEspecial");
        setCamposTabla(new String[]{"idEventoEspecial", "idCicloMateria", "nombreEvento", "fechaEvento", "descripcionEvento"});
    }

    public EventoEspecial(int idEventoEspecial, int idCicloMateria, String nombreEvento, String fechaEvento, String descripcionEvento) {
        this.idEventoEspecial = idEventoEspecial;
        this.idCicloMateria = idCicloMateria;
        this.nombreEvento = nombreEvento;
        this.fechaEvento = fechaEvento;
        this.descripcionEvento = descripcionEvento;
    }

    public int getIdEventoEspecial() {
        return idEventoEspecial;
    }

    public void setIdEventoEspecial(int idEventoEspecial) {
        this.idEventoEspecial = idEventoEspecial;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(this.getIdEventoEspecial());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idEventoEspecial", getIdEventoEspecial());
        this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        this.valoresCamposTabla.put("nombreEvento", getNombreEvento());
        this.valoresCamposTabla.put("fechaEvento", getFechaEvento());
        this.valoresCamposTabla.put("descripcionEvento", getDescripcionEvento());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdEventoEspecial(Integer.valueOf(arreglo[0]));
        setIdCicloMateria(Integer.valueOf(arreglo[1]));
        setNombreEvento(arreglo[2]);
        setFechaEvento(arreglo[3]);
        setDescripcionEvento(arreglo[4]);
    }

    @Override
    public EventoEspecial getInstanceOfModel(String[] arreglo) {
        EventoEspecial eventoEspecial = new EventoEspecial();
        eventoEspecial.setAttributesFromArray(arreglo);
        return eventoEspecial;
    }

    @Override
    public String guardar(Context context){
        String mensaje =  context.getString(R.string.mnjRegInsert); // "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombreEvento",getNombreEvento());
        this.valoresCamposTabla.put("descripcionEvento", getDescripcionEvento());
        this.valoresCamposTabla.put("fechaEvento", getFechaEvento());
        if(getIdCicloMateria() != 0)
            this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0)
        {
            mensaje=  context.getString(R.string.mnjErrorInsercion);
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
        String sql = "SELECT * FROM EventoEspecial ORDER BY idEventoEspecial DESC LIMIT 1;";
        helper.abrir();
        cursor = helper.consultar(sql);
        while (cursor.moveToNext()){
            lastId = cursor.getInt(0);
        }
        helper.cerrar();
        return lastId;
    }

    public boolean validar(Context context, int opcion, EventoEspecial eventoEspecial){
        boolean result = false;
        ControlBD helper = new ControlBD(context);
        String sql = "";
        Cursor resp;
        helper.abrir();
        switch (opcion){
            case 2 :
                sql = "SELECT COUNT(f.IDFERIADO) FROM FERIADO f \n" +
                        "WHERE (('" + eventoEspecial.getFechaEvento() +"' BETWEEN f.FECHAINICIOFERIADO AND f.FECHAFINFERIADO)\n" +
                        "OR '"+ eventoEspecial.getFechaEvento() + "' = f.FECHAINICIOFERIADO)\n" +
                        "AND f.BLOQUEARRESERVAS = 1;";
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

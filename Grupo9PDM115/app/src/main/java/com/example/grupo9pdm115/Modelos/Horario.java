package com.example.grupo9pdm115.Modelos;
import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

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
        String mensaje = context.getString(R.string.mnjRegInsert);
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("horainicio", getHoraInicio());
        this.valoresCamposTabla.put("horafinal", getHoraFinal());

        helper.abrir();
        control = helper.getDb().insert("horario", null, valoresCamposTabla);
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
    public String actualizar(Context context){
        String mensaje;
        String hi,hf;
        int control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("horainicio", getHoraInicio());
        this.valoresCamposTabla.put("horafinal", getHoraFinal());
        Horario h = new Horario();
        //hi = new String(h.horaInicio);
        //hf = new String(h.horaFinal);
        //hi =h.horaInicio;
        //hf = h.horaFinal;
        //if (hi.equals(hf)){
        //mensaje = "Son horas iguales";
        //}else{
        //if(hi.compareTo(hf) <0){
        //mensaje= "La hora inicial es mayor que la  final";
        //}else{
                helper.abrir();
                control = helper.actualizar("horario", valoresCamposTabla,"idhora",Integer.toString(this.getIdHora()));
                helper.cerrar();

                if(control == 0)
                    mensaje = context.getString(R.string.mnjRegNoExiste); //"Registro no existente.";
                else
                    mensaje = context.getString(R.string.mnjRegActualizado); //"Registro actualizado correctamente.";
            //}

        //}
        return mensaje;
    }

    public List<Horario> getAllFiltered1(Context context, String filtro){
        List<Horario> listaTablaBD = new ArrayList<>();
        ControlBD helper = new ControlBD(context);
        String[] valores = new String[getCamposTabla().length];

        String consulta ="select * from horario where horainicio like '%"+filtro+"%' or horafinal like '%"+filtro+"%'";

        helper.abrir();
        Cursor cursor = helper.consultar(consulta);

        if(cursor.moveToFirst()){
            do{
                for(int i = 0; i < getCamposTabla().length; i++){
                    valores[i] = cursor.getString(i);
                }
                listaTablaBD.add((Horario) getInstanceOfModel(valores) );
            }while (cursor.moveToNext());
        }

        helper.cerrar();

        return listaTablaBD;
    }
}

package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class Local extends TablaBD {

    // Atributos para BD
    private final String nombreTabla = "local";
    private ContentValues valores = new ContentValues();

    private int idLocal;
    private int idTipoLocal;
    private String nombreLocal;
    private int capacidad;

    public Local(){
        setNombreLlavePrimaria("idLocal");
        setNombreTabla("local");
        setCamposTabla(new String[]{"idLocal", "idTipoLocal", "nombreLocal", "capacidad"});
    }

    public Local(int idTipoLocal, String nombreLocal, int capacidad) {
        this.idTipoLocal = idTipoLocal;
        this.nombreLocal = nombreLocal;
        this.capacidad = capacidad;
    }

    public int getIdlocal() {
        return idLocal;
    }

    public void setIdlocal(int idlocal) {
        this.idLocal = idlocal;
    }

    public int getIdtipolocal() {
        return idTipoLocal;
    }

    public void setIdtipolocal(int idtipolocal) {
        this.idTipoLocal = idtipolocal;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(this.getIdlocal());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idLocal", getIdlocal());
        this.valoresCamposTabla.put("nombreLocal", getNombreLocal());
        this.valoresCamposTabla.put("idTipoLocal", getIdtipolocal());
        this.valoresCamposTabla.put("capacidad", getCapacidad());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdlocal(Integer.valueOf(arreglo[0]));
        setIdtipolocal(Integer.valueOf(arreglo[1]));
        setNombreLocal(arreglo[2]);
        setCapacidad(Integer.valueOf(arreglo[3]));
    }

    @Override
    public Local getInstanceOfModel(String[] arreglo) {
        Local local = new Local();
        local.setAttributesFromArray(arreglo);
        return local;
    }

    @Override
    public String guardar(Context context) {
        String mensaje = context.getString(R.string.mnjRegInsertExit); //"Se ha insertado el registro con éxito. ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("nombreLocal", getNombreLocal());
        this.valoresCamposTabla.put("idTipoLocal", getIdtipolocal());
        this.valoresCamposTabla.put("capacidad", getCapacidad());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0) {
            mensaje= context.getString(R.string.mnjErrorInsercion); //"Error al insertar el registro, registro duplicado. Verificar inserción.";
        }

        return mensaje;
    }

    public List<Local> getLocalesDisponibles(String encargado, String fecha, int idHora, int idDia, Context context){
        List<Local> listaLocalesDisponibles = new ArrayList<Local>();
        ControlBD helper = new ControlBD(context);
        //String[] valores = new String[getCamposTabla().length];
        helper.abrir();
        String sql = "SELECT l.IDLOCAL, l.NOMBRELOCAL, l.IDTIPOLOCAL, l.CAPACIDAD FROM LOCAL l, TIPOLOCAL tl\n" +
                "WHERE l.IDLOCAL NOT IN (\n" +
                "SELECT de.IDLOCAL FROM DETALLERESERVA de\n" +
                "WHERE de.IDDIA = " + idDia +"\n" +
                "AND de.IDHORA = "+ idHora +"\n" +
                "AND (de.INICIOPERIODORESERVA = '" + fecha + "' OR '" + fecha + "' BETWEEN de.INICIOPERIODORESERVA AND de.FINPERIODORESERVA)\n" +
                "AND ESTADORESERVA = 1 AND APROBADO = 1)\n" +
                "AND l.IDTIPOLOCAL = tl.IDTIPOLOCAL\n" +
                "AND tl.IDENCARGADO = '"+ encargado +"';";
        Cursor cursor = helper.consultar(sql);
        if(cursor.moveToFirst()){
            do{
                Local localDis = new Local();
                //valores[i] = cursor.getString(i);
                localDis.setIdlocal(cursor.getInt(0));
                localDis.setNombreLocal(cursor.getString(1));
                localDis.setIdtipolocal(cursor.getInt(2));
                localDis.setCapacidad(cursor.getInt(3));
                listaLocalesDisponibles.add(localDis);
            }while (cursor.moveToNext());
        }
        helper.cerrar();
        return listaLocalesDisponibles;
    }

    /*public ContentValues getValores(){
        // Agregando los valores de los atributos al content value
        valores.put("idTipoLocal", getIdtipolocal());
        valores.put("nombreLocal", getNombreLocal());
        valores.put("capacidad", getCapacidad());

        return valores;
    }*/

}

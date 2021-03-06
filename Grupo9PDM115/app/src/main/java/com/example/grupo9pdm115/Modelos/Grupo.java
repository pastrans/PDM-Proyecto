package com.example.grupo9pdm115.Modelos;

import android.content.Context;
import android.database.Cursor;

import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;
import com.example.grupo9pdm115.R;

import java.util.ArrayList;
import java.util.List;

public class Grupo extends TablaBD {
    // Atributos
    private int idGrupo;
    private int numero;
    private int idTipoGrupo;
    private int idCicloMateria;
    private CicloMateria cicloMateria;
    private TipoGrupo tipoGrupo;
    // -------------Constructor---------------
    public Grupo(int idGrupo, int numero) {
        this.idGrupo = idGrupo;
        this.numero = numero;
    }
    public Grupo() {
        setNombreTabla("grupo");
        setNombreLlavePrimaria("idGrupo");
        setCamposTabla(new String[]{"idGrupo", "idTipoGrupo", "idCicloMateria", "numero"});
    }
    // -------------Fin del Constructor---------------
    // -------------Métodos getter y setter---------------
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    //public String getNombreTabla() { return nombreTabla; }

    public CicloMateria getCicloMateria() {
        return cicloMateria;
    }

    public void setCicloMateria(CicloMateria cicloMateria) {
        this.cicloMateria = cicloMateria;
    }

    public TipoGrupo getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(TipoGrupo tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    @Override
    public String getValorLlavePrimaria() {
        return String.valueOf(getIdGrupo());
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idGrupo", getIdGrupo());
        this.valoresCamposTabla.put("idTipoGrupo", getIdTipoGrupo());
        this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        this.valoresCamposTabla.put("numero", getNumero());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdGrupo(Integer.valueOf(arreglo[0]));
        setIdTipoGrupo(Integer.valueOf(arreglo[1]));
        setIdCicloMateria(Integer.valueOf(arreglo[2]));
        setNumero(Integer.valueOf(arreglo[3]));
    }

    @Override
    public Grupo getInstanceOfModel(String[] arreglo) {
        Grupo grupo = new Grupo();
        grupo.setAttributesFromArray(arreglo);
        return grupo;
    }

    public int getIdTipoGrupo() {
        return idTipoGrupo;
    }

    public void setIdTipoGrupo(int idTipoGrupo) {
        this.idTipoGrupo = idTipoGrupo;
    }

    public int getIdCicloMateria() {
        return idCicloMateria;
    }

    public void setIdCicloMateria(int idCicloMateria) {
        this.idCicloMateria = idCicloMateria;
    }

// -------------Fin de Métodos getter y setter---------------
    // -------------Métodos para BD ------------------------------

    /*public ContentValues getValores() {
        valores.put("idGrupo",getIdGrupo());
        valores.put("numero", getNumero());
        valores.put("idCicloMateria", getIdCicloMateria());
        valores.put("idTipoGrupo", getIdTipoGrupo());
        return valores;
    }*/
    // -------------Fin de métodos para BD ------------------------------

    @Override
    public String guardar(Context context) {
        String mensaje =  context.getString(R.string.mnjRegInsertExit); // "Se ha insertado el registro con éxito. ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("numero", getNumero());
        this.valoresCamposTabla.put("idTipoGrupo", getIdTipoGrupo());
        this.valoresCamposTabla.put("idCicloMateria", getIdCicloMateria());
        helper.abrir();
        control = helper.getDb().insert(getNombreTabla(), null, valoresCamposTabla);
        helper.cerrar();

        if(control==-1 || control==0) {
            mensaje= context.getString(R.string.mnjErrorInsercion);
        }
        return mensaje;
    }

    public boolean verificar(int accion, Context context){
        String sql = "";
        Cursor cursor;
        ControlBD helper = new ControlBD(context);
        helper.abrir();
        switch (accion){
            case 1:
                sql = "SELECT COUNT(idGrupo) FROM Grupo WHERE Numero=" + getNumero() + " AND IDTIPOGRUPO =" + getIdTipoGrupo() + " AND IDCICLOMATERIA =" + getIdCicloMateria();
                cursor = helper.consultar(sql);
                if(cursor.moveToFirst())
                    if(cursor.getInt(0) > 0)
                        return true;
                break;
        }
        helper.cerrar();
        return false;
    }
    public List<Grupo> getAllFiltered1(Context context, String filtro) {
        List<Grupo> listaTablaBD = new ArrayList<>();
        ControlBD helper = new ControlBD(context);
        String[] valores = new String[getCamposTabla().length];

        String consulta = "select * from grupo where numero like '%" + filtro + "%'";
        helper.abrir();
        Cursor cursor = helper.consultar(consulta);
        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i < getCamposTabla().length; i++) {
                    valores[i] = cursor.getString(i);
                }
                listaTablaBD.add((Grupo) getInstanceOfModel(valores));
            } while (cursor.moveToNext());
        }
        helper.cerrar();

        return listaTablaBD;
    }
}


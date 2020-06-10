package com.example.grupo9pdm115.Modelos;

import android.content.ContentValues;
import android.content.Context;

// Importación de ControlBD
import com.example.grupo9pdm115.BD.ControlBD;
import com.example.grupo9pdm115.BD.TablaBD;

public class Materia  extends TablaBD {
    // Atributos para BD
    private final String nombreTabla = "materia";
    private ContentValues valores = new ContentValues();



    // Atributos
    private String codMateria;
    private int idUnidad;
    private String nombreMateria;
    private boolean masiva;

    // Métodos
    public Materia(){
    }

    public Materia(String codMateria, int idUnidad, String nombreMateria, boolean masiva) {
        this.codMateria = codMateria;
        this.idUnidad = idUnidad;
        this.nombreMateria = nombreMateria;
        this.masiva = masiva;
    }

    public String getCodMateria() { return codMateria; }

    public void setCodMateria(String codMateria) { this.codMateria = codMateria; }

    public int getIdUnidad() { return idUnidad; }

    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }

    public String getNombreMateria() { return nombreMateria; }

    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public boolean isMasiva() { return masiva; }

    public void setMasiva(String masivaEstado) {
        boolean estado = false;

        if(masivaEstado.equals("1"))
            estado = true;
        else{
            if(masivaEstado.equals("0"))
                estado = false;
            else{
                estado = Boolean.parseBoolean(masivaEstado);
            }
        }


        this.masiva = estado;}


    public void setValores(ContentValues valores) { this.valores = valores; }
    // Métodos para BD
    public String getNombreTabla() { return nombreTabla; }

    @Override
    public String getValorLlavePrimaria() {
       return codMateria;    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("codMateria", getCodMateria());
        this.valoresCamposTabla.put("idUnidad", getIdUnidad());
        this.valoresCamposTabla.put("nombreMat" , getNombreMateria());
        this.valoresCamposTabla.put("masiva" , isMasiva());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setCodMateria(arreglo[0]);
        setIdUnidad(Integer.parseInt(arreglo[1]));
        setNombreMateria(arreglo[2]);
        setMasiva(arreglo[3]);

    }

    @Override
    public TablaBD getInstanceOfModel(String[] arreglo) {
        Materia materia = new Materia();
        materia.setAttributesFromArray(arreglo);
        return materia;
    }

    @Override
    public String guardar(Context context){
        String mensaje = "Registro insertado N° = ";
        long control = 0;
        ControlBD helper = new ControlBD(context);
        this.valoresCamposTabla.put("codMateria", getCodMateria());
        this.valoresCamposTabla.put("idUnidad", getIdUnidad());
        this.valoresCamposTabla.put("nombreMat", getNombreMateria());
        this.valoresCamposTabla.put("masiva", isMasiva());


        helper.abrir();
        control = helper.getDb().insert("materia", null, valoresCamposTabla);
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

}

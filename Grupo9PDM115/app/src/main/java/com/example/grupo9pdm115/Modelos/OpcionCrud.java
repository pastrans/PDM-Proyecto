package com.example.grupo9pdm115.Modelos;

import android.content.Context;

import com.example.grupo9pdm115.BD.TablaBD;

public class OpcionCrud extends TablaBD {

    private String idOpcion;
    private String descripcion;
    private int numCrud;

    public OpcionCrud() {
        setNombreTabla("opcioncrud");
        setNombreLlavePrimaria("idOpcion");
        setCamposTabla(new String[]{"idOpcion", "desopcion", "numCrud"});
    }

    public OpcionCrud(String idOpcion, String desOpcion, int numCrud){
        this();
        this.setIdOpcion(idOpcion);
        this.setDescripcion(desOpcion);
        this.setNumCrud(numCrud);
    }

    public String getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(String idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumCrud() {
        return numCrud;
    }

    public void setNumCrud(int numCrud) {
        this.numCrud = numCrud;
    }

    @Override
    public String getValorLlavePrimaria() {
        return this.getIdOpcion();
    }

    @Override
    public void setValoresCamposTabla() {
        this.valoresCamposTabla.put("idOpcion", getIdOpcion());
        this.valoresCamposTabla.put("desopcion", getDescripcion());
        this.valoresCamposTabla.put("numcrud", getNumCrud());
    }

    @Override
    public void setAttributesFromArray(String[] arreglo) {
        setIdOpcion(arreglo[0]);
        setDescripcion(arreglo[1]);
        setNumCrud(Integer.parseInt(arreglo[2]));
    }

    @Override
    public OpcionCrud getInstanceOfModel(String[] arreglo) {
        OpcionCrud oc = new OpcionCrud();
        oc.setAttributesFromArray(arreglo);
        return oc;
    }

    public void insertarOpcionesCrud(Context context){
        String[] idOpcion = new String[]{
                "IAU", "EAU", "DAU", "CAU",
                "ICL", "ECL", "DCL", "CCL",
                "ICM", "ECM", "DCM", "CCM",
                "ICO", "ECO", "DCO", "CCO",
                "IDR", "EDR", "DDR", "CDR",
                "IDI", "EDI", "DDI", "CDI",
                "IEN", "EEN", "DEN", "CEN",
                "IEE", "EEE", "DEE", "CEE",
                "IFE", "EFI", "DFE", "CFE",
                "IGR", "EGR", "DGR", "CGR",
                "IHO", "EHO", "DHO", "CHO",
                "ILO", "ELO", "DLO", "CLO",
                "IMA", "EMA", "DMA", "CMA",
                "ISO", "ESO", "DSO", "CSO",
                "ITG", "ETG", "DTG", "CTG",
                "ITL", "ETL", "DTL", "CTL",
                "IUS", "EUS", "DUS", "CUS",
                "IUN", "EUN", "DUN", "CUN"
        };
        String[] desOpcion = new String[]{
                "Insertar acceso usuario", "Editar acceso usuario", "Eliminar acceso usuario", "Consultar acceso usuario",
                "Insertar ciclo", "Editar ciclo", "Eliminar cilo", "Consultar ciclo",
                "Insertar ciclo-materia", "Editar ciclo-materia", "Eliminar ciclo-materia", "Consultar ciclo-materia",
                "Insertar coordinación", "Editar coordinación", "Eliminar coordinación", "Consultar coordinación",
                "Insertar detalle reserva", "Editar detalle reserva", "Eliminar detalle reserva", "Consultar detalle reserva",
                "Insertar día", "Editar día", "Eliminar día", "Consultar día",
                "Insertar encargado", "Editar encargado", "Eliminar encargado", "Consultar encargado",
                "Insertar evento especial", "Editar evento especial", "Eliminar evento especial", "Consultar evento especial",
                "Insertar feriado", "Editar feriado", "Eliminar feriado", "Consultar feriado",
                "Insertar grupo", "Editar grupo", "Eliminar grupo", "Consultar grupo",
                "Insertar horario", "Editar horario", "Eliminar horario", "Consultar horario",
                "Insertar local", "Editar local", "Eliminar local", "Consultar local",
                "Insertar materia", "Editar materia", "Eliminar materia", "Consultar materia",
                "Insertar solicitud", "Editar solicitud", "Eliminar solicitud", "Consultar solicitud",
                "Insertar tipo grupo", "Editar tipo grupo", "Eliminar tipo grupo", "Consultar tipo grupo",
                "Insertar tipo local", "Editar tipo local", "Eliminar tipo local", "Consultar tipo local",
                "Insertar usuario", "Editar usuario", "Eliminar usuario", "Consultar usuario",
                "Insertar unidad", "Editar unidad", "Eliminar unidad", "Consultar unidad",
        };
        int[] numCrud = new int[]{
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
                1, 2, 3, 4,
        };
        OpcionCrud oc = new OpcionCrud();
        for(int i = 0; i < idOpcion.length; i++){
            oc.setIdOpcion(idOpcion[i]);
            oc.setDescripcion(desOpcion[i]);
            oc.setNumCrud(numCrud[i]);
            oc.guardar(context);
        }
    }

}

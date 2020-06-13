package com.example.grupo9pdm115.Utilidades;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FechasHelper {

    // Método para convertir un String a un Date
    public static Date stringToDate(String fechaCadena) throws ParseException {
        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaCadena);
        return fecha;
    }

    // Método para convertir un Date a String
    public static String dateToString(Date fecha){
        String fechaCadena;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        fechaCadena = dateFormat.format(fecha);

        return fechaCadena;
    }

    // Método que devuelve true si fechaACompararCad es anterior a fechaBaseCad y false si no
    public static boolean fechaEsAnterior(String fechaBaseCad, String fechaACompararCad){
        boolean resultado;
        Date fechaBase = new Date();
        Date fechaAComparar = new Date();

        try {
            fechaBase  = stringToDate(fechaBaseCad);
            fechaAComparar = stringToDate(fechaACompararCad);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        resultado = fechaAComparar.before(fechaBase);

        return resultado;
    }

    // Método que devuelve true si fechaACompararCad es posterior a fechaBaseCad y false si no
    public static boolean fechaEsPosterior(String fechaBaseCad, String fechaACompararCad){
        boolean resultado;
        Date fechaBase = new Date();
        Date fechaAComparar = new Date();

        try {
            fechaBase  = stringToDate(fechaBaseCad);
            fechaAComparar = stringToDate(fechaACompararCad);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        resultado = fechaAComparar.after(fechaBase);

        return resultado;
    }

    // Método que devuelve true si fechaAComparar se encuentra en medio de fehcaInicio y fechaFin
    // y false si no
    public static boolean fechaEstaEnmedio(String fechaInicio, String fechaFin, String fechaAComparar){
        boolean resultado = false;

        if(fechaEsPosterior(fechaInicio, fechaAComparar) && fechaEsAnterior(fechaFin, fechaAComparar)){
            resultado = true;
        }

        return resultado;
    }
}

package com.example.grupo9pdm115.Modelos;

public class Usuario {

    private String idUsuario;
    private String nombreUsuario;
    private String claveUsuario;
    private String nombrePersonal;
    private String apellidoPersonal;
    private String correoPersonal;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String claveUsuario, String nombrePersonal, String apellidoPersonal, String correoPersonal) {
        this.nombreUsuario = nombreUsuario;
        this.claveUsuario = claveUsuario;
        this.nombrePersonal = nombrePersonal;
        this.apellidoPersonal = apellidoPersonal;
        this.correoPersonal = correoPersonal;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public String getApellidoPersonal() {
        return apellidoPersonal;
    }

    public void setApellidoPersonal(String apellidoPersonal) {
        this.apellidoPersonal = apellidoPersonal;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }
}

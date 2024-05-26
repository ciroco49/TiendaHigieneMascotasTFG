package com.example.tiendahigienemascotas.Modelos;

public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String residencia;

    public Cliente() {
    }

    public Cliente(String DNI, String nombre, String apellidos, String telefono, String correo, String residencia) {
        this.dni = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.residencia = residencia;
    }

    public String getDNI() {
        return dni;
    }

    public void setDNI(String DNI) {
        this.dni = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    @Override
    public String toString() {
        return "{" +
                "\"DNI\":\"" + dni + "\"," +
                "\"nombre\":\"" + nombre + "\"," +
                "\"apellidos\":\"" + apellidos + "\"," +
                "\"telefono\":\"" + telefono + "\"," +
                "\"correo\":\"" + correo + "\"," +
                "\"residencia\":\"" + residencia + "\"" +
                '}';
    }

}

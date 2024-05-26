package com.example.tiendahigienemascotas.Modelos;

import java.util.List;

public class EspecialistaDTO {
    private String DNI;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String residencia;
    private Double sueldo;
    private List<String> DNIMascotaList;

    public EspecialistaDTO() {
    }

    public EspecialistaDTO(String DNI, String nombre, String apellidos, String telefono, String correo, String residencia, Double sueldo, List<String> DNIMascotaList) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.residencia = residencia;
        this.sueldo = sueldo;
        this.DNIMascotaList = DNIMascotaList;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
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

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public List<String> getDNIMascotaList() {
        return DNIMascotaList;
    }

    public void setDNIMascotaList(List<String> DNIMascotaList) {
        this.DNIMascotaList = DNIMascotaList;
    }
}


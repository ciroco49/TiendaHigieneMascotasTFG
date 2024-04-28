package com.ciroiencom.tfg.modelo;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Cliente")

public class Cliente {
    @Id
    private String DNI;
    @Column
    private String nombre;
    @Column
    private String apellidos;
    @Column
    private String telefono;
    @Column
    private String correo;
    @Column
    private String residencia;

    public Cliente(String DNI, String nombre, String apellidos, String telefono, String correo, String residencia) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.residencia = residencia;
    }

    public Cliente() {
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
}

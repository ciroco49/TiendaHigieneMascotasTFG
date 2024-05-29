package com.example.tiendahigienemascotas.Modelos;

public class MascotaDTO {
    private String dni;
    private String nombre;
    private int edad;
    private char sexo;
    private String especie;
    private String raza;
    private String dni_especialista;

    public MascotaDTO() {
    }

    public MascotaDTO(String dni, String nombre, int edad, char sexo, String especie, String raza, String dni_especialista) {
        this.dni = dni;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.especie = especie;
        this.raza = raza;
        this.dni_especialista = dni_especialista;
    }

    public String getDNI() {
        return dni;
    }

    public void setDNI(String DNI) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDNI_especialista() {
        return dni_especialista;
    }

    public void setDNI_especialista(String DNI_especialista) {
        this.dni_especialista = DNI_especialista;
    }


}

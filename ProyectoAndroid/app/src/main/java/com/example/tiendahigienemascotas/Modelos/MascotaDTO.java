package com.example.tiendahigienemascotas.Modelos;

public class MascotaDTO {
    private String DNI;
    private String nombre;
    private int edad;
    private char sexo;
    private String especie;
    private String raza;
    private String DNI_especialista;

    public MascotaDTO() {
    }

    public MascotaDTO(String DNI, String nombre, int edad, char sexo, String especie, String raza, String DNI_especialista) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.especie = especie;
        this.raza = raza;
        this.DNI_especialista = DNI_especialista;
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
        return DNI_especialista;
    }

    public void setDNI_especialista(String DNI_especialista) {
        this.DNI_especialista = DNI_especialista;
    }


}

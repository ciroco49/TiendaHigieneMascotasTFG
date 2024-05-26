package com.ciroiencom.tfg.modelo;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class TenerID implements Serializable {
    private String DNICliente;
    private String DNIMascota;

    public TenerID(String DNICliente, String DNI_Mascota) {
        this.DNICliente = DNICliente;
        this.DNIMascota = DNI_Mascota;
    }

    public TenerID() {
    }

    public String getDNICliente() {
        return DNICliente;
    }

    public void setDNICliente(String DNICliente) {
        this.DNICliente = DNICliente;
    }

    public String getDNI_Mascota() {
        return DNIMascota;
    }

    public void setDNI_Mascota(String DNI_Mascota) {
        this.DNIMascota = DNI_Mascota;
    }

}

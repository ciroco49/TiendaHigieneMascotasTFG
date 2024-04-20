package com.ciroiencom.tfg.modelo;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class TenerID implements Serializable {
    private String DNI_Cliente;
    private String DNI_Mascota;

    public TenerID(String DNI_Cliente, String DNI_Mascota) {
        this.DNI_Cliente = DNI_Cliente;
        this.DNI_Mascota = DNI_Mascota;
    }

    public TenerID() {
    }

    public String getDNI_Cliente() {
        return DNI_Cliente;
    }

    public void setDNI_Cliente(String DNI_Cliente) {
        this.DNI_Cliente = DNI_Cliente;
    }

    public String getDNI_Mascota() {
        return DNI_Mascota;
    }

    public void setDNI_Mascota(String DNI_Mascota) {
        this.DNI_Mascota = DNI_Mascota;
    }

}

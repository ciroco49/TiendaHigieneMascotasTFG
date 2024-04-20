package com.ciroiencom.tfg.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Tener")
@IdClass(TenerID.class)
public class Tener {

    @Id
    private String DNI_Cliente;
    @Id
    private String DNI_Mascota;

    public Tener(String DNI_Cliente, String DNI_Mascota) {
        this.DNI_Cliente = DNI_Cliente;
        this.DNI_Mascota = DNI_Mascota;
    }

    public Tener() {
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

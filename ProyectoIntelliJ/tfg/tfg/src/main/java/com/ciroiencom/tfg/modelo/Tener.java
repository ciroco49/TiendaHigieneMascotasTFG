package com.ciroiencom.tfg.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Tener")
@IdClass(TenerID.class)
public class Tener {

    @Id
    @ManyToOne
    @JoinColumn(name = "Cliente_DNI", referencedColumnName = "DNI")
    private Cliente DNICliente;
    @Id
    @ManyToOne
    @JoinColumn(name = "Mascota_DNI", referencedColumnName = "DNI")
    private Mascota DNIMascota;

    public Tener(Cliente DNI_Cliente, Mascota DNI_Mascota) {
        this.DNICliente = DNI_Cliente;
        this.DNIMascota = DNI_Mascota;
    }

    public Tener() {
    }

    public Cliente getDNI_Cliente() {
        return DNICliente;
    }

    public void setDNI_Cliente(Cliente DNI_Cliente) {
        this.DNICliente = DNI_Cliente;
    }

    public Mascota getDNI_Mascota() {
        return DNIMascota;
    }

    public void setDNI_Mascota(Mascota DNI_Mascota) {
        this.DNIMascota = DNI_Mascota;
    }

}

package com.ciroiencom.tfg.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Tener")
@IdClass(TenerID.class)
public class Tener {

    @Id
    @ManyToOne
    @JoinColumn(name = "Cliente_DNI", referencedColumnName = "DNI")
    private Cliente DNI_Cliente;
    @Id
    @ManyToOne
    @JoinColumn(name = "Mascota_DNI", referencedColumnName = "DNI")
    private Mascota DNI_Mascota;

    public Tener(Cliente DNI_Cliente, Mascota DNI_Mascota) {
        this.DNI_Cliente = DNI_Cliente;
        this.DNI_Mascota = DNI_Mascota;
    }

    public Tener() {
    }

    public Cliente getDNI_Cliente() {
        return DNI_Cliente;
    }

    public void setDNI_Cliente(Cliente DNI_Cliente) {
        this.DNI_Cliente = DNI_Cliente;
    }

    public Mascota getDNI_Mascota() {
        return DNI_Mascota;
    }

    public void setDNI_Mascota(Mascota DNI_Mascota) {
        this.DNI_Mascota = DNI_Mascota;
    }

}

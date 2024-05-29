package com.example.tiendahigienemascotas.Modelos;

public class TenerDTO {
    private Cliente cliente;
    private MascotaDTO mascota;

    public TenerDTO(Cliente cliente, MascotaDTO mascota) {
        this.cliente = cliente;
        this.mascota = mascota;
    }

    public TenerDTO() {
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente DNICliente) {
        this.cliente = DNICliente;
    }

    public MascotaDTO getMascota() {
        return mascota;
    }

    public void setMascota(MascotaDTO DNIMascota) {
        this.mascota = mascota;
    }
}

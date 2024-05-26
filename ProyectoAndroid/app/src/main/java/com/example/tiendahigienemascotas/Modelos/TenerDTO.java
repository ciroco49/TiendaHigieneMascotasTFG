package com.example.tiendahigienemascotas.Modelos;

public class TenerDTO {
    private Cliente DNICliente;
    private MascotaDTO DNIMascota;

    public TenerDTO(Cliente DNICliente, MascotaDTO DNIMascota) {
        this.DNICliente = DNICliente;
        this.DNIMascota = DNIMascota;
    }

    public TenerDTO() {
    }

    public Cliente getDNICliente() {
        return DNICliente;
    }

    public void setDNICliente(Cliente DNICliente) {
        this.DNICliente = DNICliente;
    }

    public MascotaDTO getDNIMascota() {
        return DNIMascota;
    }

    public void setDNIMascota(MascotaDTO DNIMascota) {
        this.DNIMascota = DNIMascota;
    }
}

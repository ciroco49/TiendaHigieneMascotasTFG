package com.example.tiendahigienemascotas.Modelos;

import java.util.Base64;

public class CuentaDTO {
    private String correo;
    private String contrasenha;
    private byte[] imagen;

    public CuentaDTO() {
    }

    public CuentaDTO(String correo, String contrasenha, byte[] imagen) {
        this.correo = correo;
        this.contrasenha = contrasenha;
        this.imagen = imagen;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        String base64Image = imagen != null ? Base64.getEncoder().encodeToString(imagen) : null;
        return "{" +
                "\"correo\":\"" + correo + "\"," +
                "\"contrasenha\":\"" + contrasenha + "\"," +
                "\"imagen\":\"" + base64Image + "\"" +
                '}';
    }

}

package com.example.tiendahigienemascotas.Modelos;

public class Cuenta {
    private String correo;
    private String contrasenha;

    public Cuenta() {
    }

    public Cuenta(String correo, String contrasenha) {
        this.correo = correo;
        this.contrasenha = contrasenha;
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

    @Override
    public String toString() {
        return "Cuenta{" +
                "correo='" + correo + '\'' +
                ", contrasenha='" + contrasenha + '\'' +
                '}';
    }

}

package com.ciroiencom.tfg.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cuentas")
public class Cuenta {
    @Id
    private String correo;
    @Column
    private String contrasenha;

    public Cuenta(String correo, String contrasenha) {
        this.correo = correo;
        this.contrasenha = contrasenha;
    }

    public Cuenta() {
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
}

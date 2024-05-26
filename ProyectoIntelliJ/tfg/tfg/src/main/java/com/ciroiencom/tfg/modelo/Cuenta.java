package com.ciroiencom.tfg.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Blob;

@Entity
@Table(name = "Cuentas")
public class Cuenta {
    @Id
    private String correo;
    @Column
    private String contrasenha;
    @Column
    private Blob imagen;

    public Cuenta() {
    }

    public Cuenta(String correo, String contrasenha, Blob imagen) {
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

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }
}

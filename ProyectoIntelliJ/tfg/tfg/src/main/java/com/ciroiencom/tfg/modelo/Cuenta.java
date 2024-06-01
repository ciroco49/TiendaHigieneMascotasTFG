package com.ciroiencom.tfg.modelo;


import jakarta.persistence.*;

import java.sql.Blob;

@Entity
@Table(name = "Cuentas")
public class Cuenta {
    @Id
    private String correo;
    @Column
    private String contrasenha;
    @Column
    @Lob
    private String imagen;

    public Cuenta() {
    }

    public Cuenta(String correo, String contrasenha, String imagen) {
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

package modelo.vo;

import javax.persistence.*;

@Entity
@Table(name = "Cuentas")
public class Cuenta {
    @Id
    private String correo;
    @Column
    private String contrasenha;
    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    public Cuenta() {
    }

    public Cuenta(String correo, String contrasenha, byte[] imagen) {
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
}

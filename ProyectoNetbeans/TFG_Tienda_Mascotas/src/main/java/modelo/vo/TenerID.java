package modelo.vo;

import java.io.Serializable;
import java.util.Objects;

public class TenerID implements Serializable {
    private String DNICliente;
    private String DNIMascota;

    public TenerID(String DNICliente, String DNIMascota) {
        this.DNICliente = DNICliente;
        this.DNIMascota = DNIMascota;
    }

    public TenerID() {
    }

    public String getDNICliente() {
        return DNICliente;
    }

    public void setDNICliente(String DNICliente) {
        this.DNICliente = DNICliente;
    }

    public String getDNIMascota() {
        return DNIMascota;
    }

    public void setDNIMascota(String DNIMascota) {
        this.DNIMascota = DNIMascota;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenerID tenerID = (TenerID) o;
        return Objects.equals(DNICliente, tenerID.DNICliente) &&
                Objects.equals(DNIMascota, tenerID.DNIMascota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DNICliente, DNIMascota);
    }

    
}

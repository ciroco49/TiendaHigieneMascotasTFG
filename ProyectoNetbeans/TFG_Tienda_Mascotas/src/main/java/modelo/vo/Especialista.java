package modelo.vo;

import java.util.ArrayList;
import javax.persistence.*;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Especialista")
public class Especialista {
    @Id
    private String DNI;
    @Column
    private String nombre;
    @Column
    private String apellidos;
    @Column
    private String telefono;
    @Column
    private String correo;
    @Column
    private String residencia;
    @Column
    private Double sueldo;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "DNI_especialista")
    private List<Mascota> mascotasList = new ArrayList<Mascota>();

    public Especialista(String DNI, String nombre, String apellidos, String telefono, String correo, String residencia, Double sueldo) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.residencia = residencia;
        this.sueldo = sueldo;
    }

    public Especialista() {
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public List<Mascota> getMascotasList() {
        return mascotasList;
    }

    public void setMascotasList(List<Mascota> mascotasList) {
        this.mascotasList = mascotasList;
    }
}

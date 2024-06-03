package modelo.vo;

import javax.persistence.*;

@Entity
@Table(name = "Mascota")
public class Mascota {
    @Id
    @Column(name = "DNI")
    private String DNI;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "edad")
    private int edad;
    @Column(name = "sexo")
    private char sexo;
    @Column(name = "especie")
    private String especie;
    @Column(name = "raza")
    private String raza;
    @ManyToOne
    @JoinColumn(name = "DNI_especialista", referencedColumnName = "DNI")
    private Especialista DNI_especialista;

    public Mascota(String DNI, String nombre, int edad, char sexo, String especie, String raza, Especialista DNI_especialista) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.edad = edad;
        this.sexo = sexo;
        this.especie = especie;
        this.raza = raza;
        this.DNI_especialista = DNI_especialista;
    }

    public Mascota() {
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Especialista getDNI_especialista() {
        return DNI_especialista;
    }

    public void setDNI_especialista(Especialista DNI_especialista) {
        this.DNI_especialista = DNI_especialista;
    }
}

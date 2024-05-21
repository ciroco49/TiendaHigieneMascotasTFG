package com.ciroiencom.tfg.modelo.DTO;

import com.ciroiencom.tfg.modelo.Especialista;
import com.ciroiencom.tfg.modelo.Mascota;

import java.util.ArrayList;
import java.util.List;

public class EspecialistaDTO {

    private String DNI;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String residencia;
    private Double sueldo;
    private List<MascotaDTO> mascotasList;

    public EspecialistaDTO() {
    }

    public EspecialistaDTO(String DNI, String nombre, String apellidos, String telefono, String correo, String residencia, Double sueldo, List<MascotaDTO> mascotasList) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
        this.residencia = residencia;
        this.sueldo = sueldo;
        this.mascotasList = mascotasList;
    }

    public static EspecialistaDTO especialistaAEspecialistaDTO(Especialista especialista) {
        EspecialistaDTO especialistaDTO = new EspecialistaDTO();
        List<MascotaDTO> mascotaDTOList = new ArrayList<>();
            especialistaDTO.setDNI(especialista.getDNI());
            especialistaDTO.setNombre(especialista.getNombre());
            especialistaDTO.setApellidos(especialista.getApellidos());
            especialistaDTO.setTelefono(especialista.getTelefono());
            especialistaDTO.setCorreo(especialista.getCorreo());
            especialistaDTO.setResidencia(especialista.getResidencia());
            especialistaDTO.setSueldo(especialista.getSueldo());

        //Le añado a la lista de mascotasDTO la lista original de mascotas pero convertidas
            for(Mascota masc: especialista.getMascotasList()) {
                mascotaDTOList.add(MascotaDTO.mascotaAMascotaDTO(masc));
            }

            especialistaDTO.setMascotasList(mascotaDTOList);
        return especialistaDTO;
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

    public List<MascotaDTO> getMascotasList() {
        return mascotasList;
    }

    public void setMascotasList(List<MascotaDTO> mascotasList) {
        this.mascotasList = mascotasList;
    }
}

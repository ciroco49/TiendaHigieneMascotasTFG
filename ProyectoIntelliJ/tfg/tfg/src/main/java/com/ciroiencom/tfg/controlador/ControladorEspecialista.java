package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.DTO.EspecialistaDTO;
import com.ciroiencom.tfg.modelo.Especialista;
import com.ciroiencom.tfg.repositorio.RepoCliente;
import com.ciroiencom.tfg.repositorio.RepoEspecialista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorEspecialista {

@Autowired
private RepoEspecialista repoEspecialista;

    @GetMapping(value = "/especialistas")
    public List<EspecialistaDTO> getEspecialistas() {
        List<Especialista> listaEspecialistas = repoEspecialista.findAll();

        if(!listaEspecialistas.isEmpty()) {
            List<EspecialistaDTO> listaEspecialistasDTO = new ArrayList<>();
            //Cada especialista del repositorio lo convierto a EspecialistaDTO
            for(Especialista esp: listaEspecialistas) {
                listaEspecialistasDTO.add(EspecialistaDTO.especialistaAEspecialistaDTO(esp));
            }

            return listaEspecialistasDTO;
        }

        return null;
    }

    @PostMapping(value = "/saveEspecialistas")
    public String saveEspecialista(@RequestBody Especialista especialista) {
        repoEspecialista.save(especialista);
        return "Especialista guardado";
    }

    @PutMapping(value = "/updateEspecialista/{DNI}")
    public String updateEspecialista(@PathVariable String DNI, @RequestBody Especialista especialista) {
        Especialista updatedEspecialista = repoEspecialista.findById(DNI).get();

        updatedEspecialista.setNombre(especialista.getNombre());
        updatedEspecialista.setApellidos(especialista.getApellidos());
        updatedEspecialista.setTelefono(especialista.getTelefono());
        updatedEspecialista.setCorreo(especialista.getCorreo());
        updatedEspecialista.setResidencia(especialista.getResidencia());
        updatedEspecialista.setSueldo(especialista.getSueldo());
        updatedEspecialista.setMascotasList(especialista.getMascotasList());

        repoEspecialista.save(updatedEspecialista);

        return "Especialista actualizado";
    }

    @DeleteMapping(value = "/deleteEspecialista/{DNI}")
    public String deleteEspecialista(@PathVariable String DNI) {
        Especialista deletedEspecialista = repoEspecialista.findById(DNI).get();

        repoEspecialista.delete(deletedEspecialista);

        return "Especialista borrado";
    }

}

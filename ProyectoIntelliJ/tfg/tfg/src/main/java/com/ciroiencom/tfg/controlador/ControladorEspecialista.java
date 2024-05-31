package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.modelo.DTO.EspecialistaDTO;
import com.ciroiencom.tfg.modelo.DTO.MascotaDTO;
import com.ciroiencom.tfg.modelo.Especialista;
import com.ciroiencom.tfg.modelo.Mascota;
import com.ciroiencom.tfg.repositorio.RepoCliente;
import com.ciroiencom.tfg.repositorio.RepoEspecialista;
import com.ciroiencom.tfg.repositorio.RepoMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorEspecialista {

@Autowired
private RepoEspecialista repoEspecialista;
@Autowired
private RepoMascota repoMascota;

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

    @PostMapping(value="/especialistaPorDni")
    public EspecialistaDTO getEspecialistaPorDni(@RequestBody EspecialistaDTO especialistaDTO){
        Especialista esp = repoEspecialista.findByDNI(especialistaDTO.getDNI());

        if(esp != null) {
            EspecialistaDTO espDTP = EspecialistaDTO.especialistaAEspecialistaDTO(esp);
            return espDTP;
        }

        return null;
    }

    @PostMapping(value="/especialistaPorNombre")
    public List<EspecialistaDTO> getEspecialistaPorNombre(@RequestBody EspecialistaDTO especialistaDTO){
        List<Especialista> listaEspecialistas = repoEspecialista.findByNombre(especialistaDTO.getNombre());

        if(!listaEspecialistas.isEmpty()) {
            List<EspecialistaDTO> listaEspecialistasDTO = new ArrayList<>();
            //Cada especialista obtenido lo convierto a MascotaDTO
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
    public String updateEspecialistaPorDNI(@PathVariable String DNI, @RequestBody EspecialistaDTO especialistaDTO) {
        Especialista updatedEspecialista = repoEspecialista.findById(DNI).get();

        updatedEspecialista.setNombre(especialistaDTO.getNombre());
        updatedEspecialista.setApellidos(especialistaDTO.getApellidos());
        updatedEspecialista.setTelefono(especialistaDTO.getTelefono());
        updatedEspecialista.setCorreo(especialistaDTO.getCorreo());
        updatedEspecialista.setResidencia(especialistaDTO.getResidencia());
        updatedEspecialista.setSueldo(especialistaDTO.getSueldo());

        //Busco y añado a la lista que setearé como lista de mascotas cada una de las mascotas a través de sus DNI
        List<Mascota> listaMascotas = new ArrayList<>();
        for(String dni_mascota: especialistaDTO.getDNIMascotaList()) {
            Mascota masc = repoMascota.findByDNI(dni_mascota);
            listaMascotas.add(masc);
        }

        updatedEspecialista.setMascotasList(listaMascotas);

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

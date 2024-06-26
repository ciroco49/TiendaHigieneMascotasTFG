package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.modelo.DTO.EspecialistaDTO;
import com.ciroiencom.tfg.modelo.DTO.MascotaDTO;
import com.ciroiencom.tfg.modelo.Especialista;
import com.ciroiencom.tfg.modelo.Mascota;
import com.ciroiencom.tfg.repositorio.RepoEspecialista;
import com.ciroiencom.tfg.repositorio.RepoMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorMascota {

@Autowired
private RepoMascota repoMascota;
@Autowired
private RepoEspecialista repoEspecialista;

    @GetMapping(value = "/mascotas")
    public List<MascotaDTO> getEspecialistas() {
        List<Mascota> listMascotas = repoMascota.findAll();

        if(!listMascotas.isEmpty()) {
            List <MascotaDTO> listMascotasDTO = new ArrayList<>();
            //Cada mascota del repositorio la convierta a MascotaDTO
            for(Mascota masc: listMascotas) {
                listMascotasDTO.add(MascotaDTO.mascotaAMascotaDTO(masc));
            }

            return listMascotasDTO;
        }

        return null;
    }

    @PostMapping(value="/mascotaPorDni")
    public MascotaDTO getMascotaPorDni(@RequestBody MascotaDTO mascotaDTO){
        Mascota masc = repoMascota.findByDNI(mascotaDTO.getDNI());

        if(masc != null) {
            MascotaDTO mascDTO = MascotaDTO.mascotaAMascotaDTO(masc);
            return mascDTO;
        }

        return null;
    }

    @PostMapping(value="/mascotaPorEspecie")
    public List<MascotaDTO> getMascotaPorEspecie(@RequestBody MascotaDTO mascotaDTO){
        List<Mascota> listaMascotas = repoMascota.findByEspecie(mascotaDTO.getEspecie());

        if(!listaMascotas.isEmpty()) {
            List<MascotaDTO> listaMascotasDTO = new ArrayList<>();
            //Cada mascota obtenida la convierta a MascotaDTO
            for(Mascota masc: listaMascotas) {
                listaMascotasDTO.add(MascotaDTO.mascotaAMascotaDTO(masc));
            }
            return listaMascotasDTO;
        }

        return null;
    }

    @PostMapping(value="/mascotaPorRaza")
    public List<MascotaDTO> getMascotaPorRaza(@RequestBody MascotaDTO mascotaDTO){
        List<Mascota> listaMascotas = repoMascota.findByRaza(mascotaDTO.getRaza());

        if(!listaMascotas.isEmpty()) {
            List<MascotaDTO> listaMascotasDTO = new ArrayList<>();
            //Cada mascota obtenida la convierta a MascotaDTO
            for(Mascota masc: listaMascotas) {
                listaMascotasDTO.add(MascotaDTO.mascotaAMascotaDTO(masc));
            }
            return listaMascotasDTO;
        }

        return null;
    }

    @PostMapping(value="/mascotasPorEspecialista")
    public List<MascotaDTO> getMascotasPorEspecialista(@RequestBody EspecialistaDTO especialistaDTO){
        Especialista especialista = repoEspecialista.findByDNI(especialistaDTO.getDNI());

        List<Mascota> listaMascotas = especialista.getMascotasList();

        if(!listaMascotas.isEmpty()) {
            List<MascotaDTO> listaMascotasDTO = new ArrayList<>();
            //Cada mascota obtenida la convierta a MascotaDTO
            for(Mascota masc: listaMascotas) {
                listaMascotasDTO.add(MascotaDTO.mascotaAMascotaDTO(masc));
            }
            return listaMascotasDTO;
        }

        return null;
    }

    @PostMapping(value = "/saveMascotas")
    public String saveMascotas(@RequestBody Mascota mascota) {
        repoMascota.save(mascota);
        return "Mascota guardada";
    }

    @PutMapping(value = "/updateMascota/{DNI}")
    public String updateMascota(@PathVariable String DNI, @RequestBody MascotaDTO mascotaDTO) {
        Mascota updatedMascota = repoMascota.findById(DNI).get();

        updatedMascota.setNombre(mascotaDTO.getNombre());
        updatedMascota.setEdad(mascotaDTO.getEdad());
        updatedMascota.setSexo(mascotaDTO.getSexo());
        updatedMascota.setEspecie(mascotaDTO.getEspecie());
        updatedMascota.setRaza(mascotaDTO.getRaza());
        updatedMascota.setDNI_especialista(repoEspecialista.findByDNI(mascotaDTO.getDNI_especialista()));

        repoMascota.save(updatedMascota);

        return "Mascota actualizada";
    }

    @DeleteMapping(value = "/deleteMascota/{DNI}")
    public String deleteMascota(@PathVariable String DNI) {
        Mascota deletedMascota = repoMascota.findById(DNI).get();

        repoMascota.delete(deletedMascota);

        return "Mascota borrada";
    }

}

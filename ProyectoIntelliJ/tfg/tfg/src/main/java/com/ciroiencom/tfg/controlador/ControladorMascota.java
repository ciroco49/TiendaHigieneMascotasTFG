package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.Cliente;
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

    @GetMapping(value = "/mascotas")
    public List<MascotaDTO> getEspecialistas() {
        List<Mascota> listMascotas = repoMascota.findAll();

        List <MascotaDTO> listMascotasDTO = new ArrayList<>();
        //Cada mascota del repositorio la convierta a MascotaDTO
        for(Mascota masc: listMascotas) {
            listMascotasDTO.add(MascotaDTO.mascotaAMascotaDTO(masc));
        }

        return listMascotasDTO;
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

    @PostMapping(value="/mascotaPorNombre")
    public List<MascotaDTO> getMascotaPorNombre(@RequestBody MascotaDTO mascotaDTO){
        List<Mascota> listaMascotas = repoMascota.findByNombre(mascotaDTO.getNombre());

        List<MascotaDTO> listaMascotasDTO = new ArrayList<>();
        //Cada mascota obtenida la convierta a MascotaDTO
        for(Mascota masc: listaMascotas) {
            listaMascotasDTO.add(MascotaDTO.mascotaAMascotaDTO(masc));
        }

        return listaMascotasDTO;
    }

    @PostMapping(value = "/saveMascotas")
    public String saveMascotas(@RequestBody Mascota mascota) {
        repoMascota.save(mascota);
        return "Mascota guardada";
    }

    @PutMapping(value = "/updateMascota/{DNI}")
    public String updateMascota(@PathVariable String DNI, @RequestBody Mascota mascota) {
        Mascota updatedMascota = repoMascota.findById(DNI).get();

        updatedMascota.setNombre(mascota.getNombre());
        updatedMascota.setEdad(mascota.getEdad());
        updatedMascota.setSexo(mascota.getSexo());
        updatedMascota.setEspecie(mascota.getEspecie());
        updatedMascota.setRaza(mascota.getRaza());
        updatedMascota.setDNI_especialista(mascota.getDNI_especialista());

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

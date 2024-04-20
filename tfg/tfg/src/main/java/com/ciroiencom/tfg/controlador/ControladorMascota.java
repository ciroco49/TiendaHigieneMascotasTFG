package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.Especialista;
import com.ciroiencom.tfg.modelo.Mascota;
import com.ciroiencom.tfg.repositorio.RepoEspecialista;
import com.ciroiencom.tfg.repositorio.RepoMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorMascota {

@Autowired
private RepoMascota repoMascota;

    @GetMapping(value = "/mascotas")
    public List<Mascota> getEspecialistas() {
        return repoMascota.findAll();
    }

    @PostMapping(value = "/saveMascotas")
    public String saveMascotas(@RequestBody Mascota mascota) {
        repoMascota.save(mascota);
        return "Mascota guardada";
    }

    @PutMapping(value = "/update/{DNI}")
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

    @DeleteMapping(value = "delete/{DNI}")
    public String deleteMascota(@PathVariable String DNI) {
        Mascota deletedMascota = repoMascota.findById(DNI).get();

        repoMascota.delete(deletedMascota);

        return "Mascota borrada";
    }

}

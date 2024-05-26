package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.modelo.DTO.TenerDTO;
import com.ciroiencom.tfg.modelo.Tener;
import com.ciroiencom.tfg.modelo.TenerID;
import com.ciroiencom.tfg.repositorio.RepoTener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorTener {

    @Autowired
    private RepoTener repoTener;

    @GetMapping(value = "/tener")
    public List<Tener> getTener() {
        return repoTener.findAll();
    }

    @PostMapping(value= "/tenerPorDNICliente")
    public List<TenerDTO> getTenerPorDNICliente(@RequestBody Cliente cliente) {
        List<Tener> listTener = repoTener.findByDNICliente(cliente);
        List<TenerDTO> listTenerDTO = new ArrayList<>();

        for(Tener tener: listTener) {
            listTenerDTO.add(TenerDTO.tenerATenerDTO(tener));
        }

        return listTenerDTO;
    }


    @PostMapping(value = "/saveTener")
    public String saveTener(@RequestBody Tener tener) {
        repoTener.save(tener);
        return "Relación Tener guardada";
    }

    @PutMapping(value = "/updateTener/{DNI_Cliente}/{DNI_Mascota}")
    public String updateTener(@PathVariable String DNI_Cliente, @PathVariable String DNI_Mascota, @RequestBody Tener tener) {
        Tener updatedTener = repoTener.findById(new TenerID(DNI_Cliente, DNI_Mascota)).get();

        updatedTener.setDNI_Cliente(tener.getDNI_Cliente());
        updatedTener.setDNI_Mascota(tener.getDNI_Mascota());

        repoTener.save(updatedTener);

        return "Relación Tener actualizada";
    }

    @DeleteMapping(value = "/deleteTener/{DNI_Cliente}/{DNI_Mascota}")
    public String deleteTener(@PathVariable String DNI_Cliente, @PathVariable String DNI_Mascota) {
        Tener deletedTener = repoTener.findById(new TenerID(DNI_Cliente, DNI_Mascota)).get();

        repoTener.delete(deletedTener);

        return "Relación Tener borrada";
    }

}

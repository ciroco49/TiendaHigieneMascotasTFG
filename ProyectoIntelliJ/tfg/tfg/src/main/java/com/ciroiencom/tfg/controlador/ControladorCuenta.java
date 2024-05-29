package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.Cuenta;
import com.ciroiencom.tfg.modelo.Cuenta;
import com.ciroiencom.tfg.repositorio.RepoCliente;
import com.ciroiencom.tfg.repositorio.RepoCuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorCuenta {

    @Autowired
    private RepoCuenta repoCuenta;

    @GetMapping(value = "/cuentas")
    public List<Cuenta> getCuentas() {
        return repoCuenta.findAll();
    }

    @PostMapping("/login")
    public Cuenta getCuenta(@RequestBody Cuenta cuenta) {
        return repoCuenta.findByCorreo(cuenta.getCorreo());
    }

    @PostMapping(value = "/saveCuentas")
    public String saveCuenta(@RequestBody Cuenta cuenta) {
        repoCuenta.save(cuenta);
        return "Cuenta registrada";
    }

    @PutMapping(value = "/updateCuenta/{correo}")
    public String updateCuenta(@PathVariable String correo, @RequestBody Cuenta Cuenta) {
        Cuenta updatedCuenta = repoCuenta.findById(correo).get();

        updatedCuenta.setContrasenha(Cuenta.getContrasenha());
        updatedCuenta.setImagen(Cuenta.getImagen());

        repoCuenta.save(updatedCuenta);

        return "Cuenta actualizada";
    }

    @DeleteMapping(value = "/deleteCuenta/{correo}")
    public String deleteCuenta(@PathVariable String correo) {
        Cuenta deletedCuenta = repoCuenta.findById(correo).get();

        repoCuenta.delete(deletedCuenta);

        return "Cuenta borrada";
    }


}

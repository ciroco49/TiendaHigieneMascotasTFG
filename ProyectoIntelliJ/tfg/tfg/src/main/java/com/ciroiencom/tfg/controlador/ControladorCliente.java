package com.ciroiencom.tfg.controlador;

import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.repositorio.RepoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControladorCliente {

@Autowired
private RepoCliente repoCliente;

    @GetMapping(value = "/clientes")
    public List<Cliente> getClientes() {
        return repoCliente.findAll();
    }

    @PostMapping(value="/clientePorDni")
    public Cliente getClientePorDni(@RequestBody Cliente cliente){
        return repoCliente.findByDNI(cliente.getDNI());
    }

    @PostMapping(value="/clientePorNombre")
    public List<Cliente> getClientePorNombre(@RequestBody Cliente cliente){
        List<Cliente> listaCliente = repoCliente.findByNombre(cliente.getNombre());

        if(!listaCliente.isEmpty()) {
            return listaCliente;
        }

        return null;

    }

    @PostMapping(value = "/saveClientes")
    public String saveCliente(@RequestBody Cliente cliente) {
        repoCliente.save(cliente);
        return "Cliente guardado";
    }

    @PutMapping(value = "/updateCliente/{DNI}")
    public String updateCliente(@PathVariable String DNI, @RequestBody Cliente cliente) {
        Cliente updatedCliente = repoCliente.findById(DNI).get();

        updatedCliente.setNombre(cliente.getNombre());
        updatedCliente.setApellidos(cliente.getApellidos());
        updatedCliente.setTelefono(cliente.getTelefono());
        updatedCliente.setCorreo(cliente.getCorreo());
        updatedCliente.setResidencia(cliente.getResidencia());

        repoCliente.save(updatedCliente);

        return "Cliente actualizado";
    }

    @DeleteMapping(value = "/deleteCliente/{DNI}")
    public String deleteCliente(@PathVariable String DNI) {
        Cliente deletedCliente = repoCliente.findById(DNI).get();

        repoCliente.delete(deletedCliente);

        return "Cliente borrado";
    }

}

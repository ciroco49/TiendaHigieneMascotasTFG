package com.ciroiencom.tfg.repositorio;


import com.ciroiencom.tfg.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoCliente extends JpaRepository<Cliente, String> {
    List<Cliente> findByNombre(String nombre);
}

package com.ciroiencom.tfg.repositorio;


import com.ciroiencom.tfg.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCliente extends JpaRepository<Cliente, String> {



}

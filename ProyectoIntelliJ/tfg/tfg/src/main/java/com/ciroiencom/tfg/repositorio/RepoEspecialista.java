package com.ciroiencom.tfg.repositorio;


import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.modelo.DTO.EspecialistaDTO;
import com.ciroiencom.tfg.modelo.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoEspecialista extends JpaRepository<Especialista, String> {
    Especialista findByDNI(String DNI);
    List<Especialista> findByNombre(String nombre);

}

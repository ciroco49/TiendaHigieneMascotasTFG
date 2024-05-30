package com.ciroiencom.tfg.repositorio;


import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.modelo.DTO.MascotaDTO;
import com.ciroiencom.tfg.modelo.Especialista;
import com.ciroiencom.tfg.modelo.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoMascota extends JpaRepository<Mascota, String> {
    List<Mascota> findByNombre(String nombre);
    Mascota findByDNI(String DNI);

}

package com.ciroiencom.tfg.repositorio;

import com.ciroiencom.tfg.modelo.Cliente;
import com.ciroiencom.tfg.modelo.DTO.MascotaDTO;
import com.ciroiencom.tfg.modelo.DTO.TenerDTO;
import com.ciroiencom.tfg.modelo.Mascota;
import com.ciroiencom.tfg.modelo.Tener;
import com.ciroiencom.tfg.modelo.TenerID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoTener extends JpaRepository<Tener, TenerID> {
    List<Tener> findByDNICliente(Cliente cliente);
    List<Tener> findByDNIMascota(Mascota mascota);
}

package com.ciroiencom.tfg.repositorio;


import com.ciroiencom.tfg.modelo.Especialista;
import com.ciroiencom.tfg.modelo.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoMascota extends JpaRepository<Mascota, String> {



}

package com.ciroiencom.tfg.repositorio;

import com.ciroiencom.tfg.modelo.Tener;
import com.ciroiencom.tfg.modelo.TenerID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoTener extends JpaRepository<Tener, TenerID> {
}

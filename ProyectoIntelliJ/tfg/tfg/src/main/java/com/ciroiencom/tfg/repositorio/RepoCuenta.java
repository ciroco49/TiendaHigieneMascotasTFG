package com.ciroiencom.tfg.repositorio;

import com.ciroiencom.tfg.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoCuenta extends JpaRepository<Cuenta, String> {
}

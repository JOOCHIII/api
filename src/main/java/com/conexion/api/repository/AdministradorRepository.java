package com.conexion.api.repository;

import com.conexion.api.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Administrador findByUsuarioadmin(String usuarioadmin);
    Administrador findByCorreoadmin(String correoadmin);
}

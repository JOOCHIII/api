package com.conexion.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.conexion.api.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    List<Comentario> findByIdReporteOrderByFechaCreacionAsc(Integer idReporte);
}
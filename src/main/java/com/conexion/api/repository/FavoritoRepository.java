package com.conexion.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.api.model.Favorito;
import com.conexion.api.model.FavoritoId;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {
    List<Favorito> findByIdIdUsuario(Long idUsuario);
}


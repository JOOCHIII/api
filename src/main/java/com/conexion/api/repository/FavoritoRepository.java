package com.conexion.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.conexion.api.model.Favorito;
import com.conexion.api.model.FavoritoId;
import com.conexion.api.model.Productos;

import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {
    @Query("SELECT f.producto FROM Favorito f WHERE f.id.idUsuario = :idUsuario")
    List<Productos> findProductosFavoritosByUsuario(Long idUsuario);
}

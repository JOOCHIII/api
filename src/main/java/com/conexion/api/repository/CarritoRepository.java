package com.conexion.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.api.model.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    List<Carrito> findByUsuarioId(Long idUsuario);
    void deleteByUsuarioIdAndProductoId(Long idUsuario, Long idProducto);
}

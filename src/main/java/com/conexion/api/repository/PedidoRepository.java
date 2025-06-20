package com.conexion.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.api.model.Pedido;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long idUsuario);
}

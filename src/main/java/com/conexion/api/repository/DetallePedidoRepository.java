package com.conexion.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.api.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    List<DetallePedido> findByPedidoId(Long idPedido);
}
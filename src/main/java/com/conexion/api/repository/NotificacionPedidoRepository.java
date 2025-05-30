package com.conexion.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.api.model.NotificacionPedido;

public interface NotificacionPedidoRepository extends JpaRepository<NotificacionPedido, Long> {
    List<NotificacionPedido> findByUsuarioIdOrderByFechaDesc(Long idUsuario);
}

package com.conexion.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conexion.api.model.Notificacion;


public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByIdUsuarioAndTipoDestinoOrderByFechaDesc(int idUsuario, String tipoDestino);
}

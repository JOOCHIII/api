package com.conexion.api.repository;

import com.conexion.api.model.NotificacionPedidoAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionPedidoAdminRepository extends JpaRepository<NotificacionPedidoAdmin, Long> {

    // Obtener solo las no leídas
    List<NotificacionPedidoAdmin> findByLeidoFalse();

    // Obtener todas ordenadas por fecha descendente
    List<NotificacionPedidoAdmin> findAllByOrderByFechaDesc();
}

package com.conexion.api.model;

import java.time.LocalDateTime;

public class NotificacionPedidoDTO {
    private Long id;
    private String mensaje;
    private LocalDateTime fecha;
    private boolean leido;
    private Long idUsuario;
    private Long idPedido;

    // Constructor
    public NotificacionPedidoDTO(NotificacionPedido n) {
        this.id = n.getId();
        this.mensaje = n.getMensaje();
        this.fecha = n.getFecha();
        this.leido = n.isLeido();
        this.idUsuario = n.getUsuario() != null ? n.getUsuario().getId() : null;
        this.idPedido = n.getPedido() != null ? n.getPedido().getId() : null;
    }

    // getters y setters...
}

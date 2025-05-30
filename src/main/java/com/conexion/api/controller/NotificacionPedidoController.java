package com.conexion.api.controller;

import com.conexion.api.model.NotificacionPedido;
import com.conexion.api.model.NotificacionPedidoDTO;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.NotificacionPedidoRepository;
import com.conexion.api.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notificacion-pedido")
public class NotificacionPedidoController {

    @Autowired
    private NotificacionPedidoRepository notificacionPedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener notificaciones por usuario
    @GetMapping("/usuario")
    public ResponseEntity<?> obtenerNotificaciones(@RequestParam Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        List<NotificacionPedido> notificaciones = notificacionPedidoRepository.findByUsuarioIdOrderByFechaDesc(idUsuario);

        List<NotificacionPedidoDTO> dtos = notificaciones.stream()
            .map(NotificacionPedidoDTO::new)
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    // Marcar una notificación como leída
    @PutMapping("/marcar-leida")
    public ResponseEntity<?> marcarComoLeida(@RequestParam Long idNotificacion) {
        NotificacionPedido notificacion = notificacionPedidoRepository.findById(idNotificacion).orElse(null);
        if (notificacion == null) {
            return ResponseEntity.badRequest().body("Notificación no encontrada");
        }

        notificacion.setLeido(true);
        notificacionPedidoRepository.save(notificacion);
        return ResponseEntity.ok("Notificación marcada como leída");
    }

    // Marcar todas las notificaciones como leídas para un usuario
    @PutMapping("/marcar-todas-leidas")
    public ResponseEntity<?> marcarTodasComoLeidas(@RequestParam Long idUsuario) {
        List<NotificacionPedido> notificaciones = notificacionPedidoRepository.findByUsuarioIdOrderByFechaDesc(idUsuario);
        for (NotificacionPedido noti : notificaciones) {
            noti.setLeido(true);
        }
        notificacionPedidoRepository.saveAll(notificaciones);
        return ResponseEntity.ok("Todas las notificaciones marcadas como leídas");
    }
}

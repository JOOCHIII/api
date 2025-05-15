package com.conexion.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.model.Notificacion;
import com.conexion.api.repository.NotificacionRepository;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notiRepo;

    // Obtener notificaciones por usuario y tipoDestino (ej: tienda o incidencias)
    @GetMapping("/usuario")
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones(
            @RequestParam("id_usuario") int idUsuario,
            @RequestParam("tipoDestino") String tipoDestino) {
        
        List<Notificacion> notificaciones = notiRepo.findByIdUsuarioAndTipoDestinoOrderByFechaDesc(idUsuario, tipoDestino);
        return ResponseEntity.ok(notificaciones);
    }

    // Marcar una notificación como leída por ID
    @PutMapping("/leida")
    public ResponseEntity<String> marcarComoLeida(@RequestParam int id) {
        Optional<Notificacion> noti = notiRepo.findById(id);
        if (noti.isPresent()) {
            Notificacion n = noti.get();
            n.setLeido(true);
            notiRepo.save(n);
            return ResponseEntity.ok("Notificación marcada como leída");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrada");
        }
    }
}

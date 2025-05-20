package com.conexion.api.controller;

import java.sql.Timestamp;
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
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notiRepo;

    // ✅ 1. Obtener notificaciones no leídas por usuario y destino
    @GetMapping("/usuario")
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones(
            @RequestParam("id_usuario") int idUsuario,
            @RequestParam("tipoDestino") String tipoDestino) {

        List<Notificacion> notificaciones = notiRepo
            .findByIdUsuarioAndTipoDestinoAndLeidoFalseOrderByFechaDesc(idUsuario, tipoDestino);

        return ResponseEntity.ok(notificaciones);
    }
    
    @GetMapping("/admin")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesAdmin(
            @RequestParam("tipoDestino") String tipoDestino) {

        // Solo devuelve notificaciones generales (sin id_usuario)
        List<Notificacion> notificaciones = notiRepo
            .findByTipoDestinoAndLeidoFalseAndIdUsuarioIsNullOrderByFechaDesc(tipoDestino);

        return ResponseEntity.ok(notificaciones);
    }
    // ✅ 2. Marcar notificación como leída por ID
    @PutMapping("/{id}/leida")
    public ResponseEntity<String> marcarComoLeida(@PathVariable int id){
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

    // ✅ 3. Crear notificación (opcional, útil para cuando se crea un reporte)
    @PostMapping("/crear")
    public ResponseEntity<String> crearNotificacion(@RequestBody Notificacion notificacion) {
        notificacion.setFecha(new Timestamp(System.currentTimeMillis()));
        notificacion.setLeido(false); // siempre nueva = no leída
        notiRepo.save(notificacion);
        return ResponseEntity.ok("Notificación creada correctamente");
    }
}

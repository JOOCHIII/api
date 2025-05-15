package com.conexion.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conexion.api.model.Notificacion;
import com.conexion.api.repository.NotificacionRepository;
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notiRepo;

    @GetMapping("/usuario")
    public List<Notificacion> obtenerNotificaciones(
            @RequestParam int id_usuario,
            @RequestParam String tipoDestino) {
        return notiRepo.findByIdUsuarioAndTipoDestinoOrderByFechaDesc(id_usuario, tipoDestino);
    }

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
	

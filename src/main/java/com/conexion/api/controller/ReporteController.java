package com.conexion.api.controller;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.model.Notificacion;
import com.conexion.api.model.Reporte;
import com.conexion.api.repository.NotificacionRepository;
import com.conexion.api.repository.ReporteRepository;

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepo;

    @Autowired
    private NotificacionRepository notiRepo;

    // Crear reporte y notificar a admin y usuario creador
    @PostMapping("/crear")
    public ResponseEntity<String> crearReporte(
        @RequestParam int id_usuario,
        @RequestParam String asunto,
        @RequestParam String descripcion
    ) {
        Reporte reporte = new Reporte();
        reporte.setIdUsuario(id_usuario);
        reporte.setAsunto(asunto);
        reporte.setDescripcion(descripcion);
        reporte.setEstado("pendiente");
        reporte.setFechaCreacion(new Timestamp(System.currentTimeMillis()));

        reporteRepo.save(reporte);

        // Notificación para el admin
        Notificacion notiAdmin = new Notificacion();
        notiAdmin.setIdUsuario(1); // ID admin válido, ajustar según tu sistema
        notiAdmin.setMensaje("Nuevo reporte creado: " + asunto);
        notiAdmin.setLeido(false);
        notiAdmin.setTipoDestino("incidencias");
        notiAdmin.setFecha(new Timestamp(System.currentTimeMillis()));
        notiRepo.save(notiAdmin);

        // Notificación para el usuario creador
        Notificacion notiUsuario = new Notificacion();
        notiUsuario.setIdUsuario(id_usuario);
        notiUsuario.setMensaje("Tu reporte ha sido creado: " + asunto);
        notiUsuario.setLeido(false);
        notiUsuario.setTipoDestino("tienda");
        notiUsuario.setFecha(new Timestamp(System.currentTimeMillis()));
        notiRepo.save(notiUsuario);

        return ResponseEntity.ok("Reporte creado y notificaciones enviadas");
    }

    // Cambiar estado y notificar al usuario
    @PutMapping("/cambiarEstado")
    public ResponseEntity<String> cambiarEstado(
        @RequestParam int id_reporte,
        @RequestParam String nuevo_estado
    ) {
        Optional<Reporte> reporteOpt = reporteRepo.findById(id_reporte);

        if (reporteOpt.isPresent()) {
            Reporte reporte = reporteOpt.get();
            reporte.setEstado(nuevo_estado);
            reporteRepo.save(reporte);

            // Notificar al usuario
            Notificacion noti = new Notificacion();
            noti.setIdUsuario(reporte.getIdUsuario());
            noti.setMensaje("El estado de tu reporte '" + reporte.getAsunto() + "' ha cambiado a: " + nuevo_estado);
            noti.setLeido(false);
            noti.setTipoDestino("tienda");
            noti.setFecha(new Timestamp(System.currentTimeMillis()));
            notiRepo.save(noti);

            return ResponseEntity.ok("Estado actualizado y usuario notificado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reporte no encontrado");
        }
    }
}

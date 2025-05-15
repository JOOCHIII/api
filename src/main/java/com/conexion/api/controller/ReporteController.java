package com.conexion.api.controller;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conexion.api.model.Notificacion;
import com.conexion.api.model.Reporte;
import com.conexion.api.repository.NotificacionRepository;
import com.conexion.api.repository.ReporteRepository;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepo;

    @Autowired
    private NotificacionRepository notiRepo;

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
        reporte.setFechaCreacion(new Timestamp(System.currentTimeMillis())); // ✅

        reporteRepo.save(reporte);

        // Notificación para el admin
        Notificacion noti = new Notificacion();
        noti.setIdUsuario(1); // Usa un ID válido
        noti.setMensaje("Nuevo reporte creado: " + asunto);
        noti.setLeido(false);
        noti.setTipoDestino("incidencias");
        noti.setFecha(new Timestamp(System.currentTimeMillis())); // ✅
        notiRepo.save(noti);

        return ResponseEntity.ok("Reporte creado y notificado al admin");
    }

    @PutMapping("/cambiarEstado")
    public ResponseEntity<String> cambiarEstado(
        @RequestParam int id_reporte,
        @RequestParam String nuevo_estado
    ) {
        Optional<Reporte> reporteOpt = reporteRepo.findById(id_reporte);
        if (reporteOpt.isPresent()) {
            Reporte r = reporteOpt.get();
            r.setEstado(nuevo_estado);
            reporteRepo.save(r);

            // Notificación al usuario
            Notificacion noti = new Notificacion();
            noti.setIdUsuario(r.getIdUsuario());
            noti.setMensaje("Tu reporte '" + r.getAsunto() + "' ahora está " + nuevo_estado);
            noti.setLeido(false);
            noti.setTipoDestino("tienda");
            noti.setFecha(new Timestamp(System.currentTimeMillis())); // ✅
            notiRepo.save(noti);

            return ResponseEntity.ok("Estado actualizado y notificado al usuario");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reporte no encontrado");
        }
    }
}

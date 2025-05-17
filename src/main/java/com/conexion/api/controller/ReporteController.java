package com.conexion.api.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.dto.ReporteDTO;
import com.conexion.api.model.Notificacion;
import com.conexion.api.model.Reporte;
import com.conexion.api.repository.NotificacionRepository;
import com.conexion.api.repository.ReporteRepository;

@RestController
@RequestMapping("/api/reporte")
@CrossOrigin(origins = "*") 
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepo;

    @Autowired
    private NotificacionRepository notiRepo;



    // ✅ Crear un nuevo reporte y notificar
    @PostMapping("/crear")
    public ResponseEntity<String> crearReporte(@RequestBody ReporteDTO datos) {
        if (datos.getAsunto() == null || datos.getDescripcion() == null) {
            return ResponseEntity.badRequest().body("Asunto y descripción son requeridos");
        }

        Reporte reporte = new Reporte();
        reporte.setIdUsuario(datos.getIdUsuario());
        reporte.setAsunto(datos.getAsunto());
        reporte.setDescripcion(datos.getDescripcion());
        reporte.setEstado("pendiente");
        reporte.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
        reporteRepo.save(reporte);

        // Notificación al admin
        Notificacion notiAdmin = new Notificacion();
        notiAdmin.setIdUsuario(datos.getIdUsuario());
        notiAdmin.setMensaje("Nuevo reporte creado: " + datos.getAsunto());
        notiAdmin.setLeido(false);
        notiAdmin.setTipoDestino("incidencias");
        notiAdmin.setFecha(new Timestamp(System.currentTimeMillis()));
        notiRepo.save(notiAdmin);

        // Notificación al usuario
        Notificacion notiUsuario = new Notificacion();
        notiUsuario.setIdUsuario(datos.getIdUsuario());
        notiUsuario.setMensaje("Tu reporte ha sido creado: " + datos.getAsunto());
        notiUsuario.setLeido(false);
        notiUsuario.setTipoDestino("tienda");
        notiUsuario.setFecha(new Timestamp(System.currentTimeMillis()));
        notiRepo.save(notiUsuario);

        return ResponseEntity.ok("Reporte creado y notificaciones enviadas");
    }

    // ✅ Cambiar estado del reporte
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
    @GetMapping("/listar")
    public ResponseEntity<List<Reporte>> listarReportes() {
        List<Reporte> reportes = reporteRepo.findAll();
        return ResponseEntity.ok(reportes);
    }
}

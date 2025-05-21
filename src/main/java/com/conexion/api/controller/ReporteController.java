package com.conexion.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.dto.ReporteDTO;
import com.conexion.api.model.Notificacion;
import com.conexion.api.model.Reporte;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.NotificacionRepository;
import com.conexion.api.repository.ReporteRepository;
import com.conexion.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/reporte")
@CrossOrigin(origins = "*") 
public class ReporteController {

    @Autowired
    private ReporteRepository reporteRepo;

    @Autowired
    private NotificacionRepository notiRepo;
    

    @Autowired
    private UsuarioRepository usuarioRepo;
    



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

        // Obtener info usuario creador
        Optional<Usuario> usuarioOpt = usuarioRepo.findById((long) datos.getIdUsuario());
        String nombreUsuario = usuarioOpt.map(u -> u.getNombrecompleto()).orElse("Usuario desconocido");

        // Notificación al admin para TODOS (sin idUsuario)
        Notificacion notiAdmin = new Notificacion();
        notiAdmin.setIdUsuario(null);  // Muy importante: sin idUsuario para que la notificación vaya a todos admins
        notiAdmin.setMensaje("Nuevo reporte creado por " + nombreUsuario + ": " + datos.getAsunto());
        notiAdmin.setLeido(false);
        notiAdmin.setTipoDestino("incidencias");
        notiAdmin.setFecha(new Timestamp(System.currentTimeMillis()));
        notiRepo.save(notiAdmin);

        // Notificación al usuario creador
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

            // Notificar al usuario tienda que creó el reporte
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
    @GetMapping("/estadoReporte")
    public ResponseEntity<List<Reporte>> obtenerReportesPorEstado(@RequestParam String estado) {
        List<Reporte> reportes = reporteRepo.findByEstadoIgnoreCase(estado);
        return ResponseEntity.ok(reportes);
    }
    @PutMapping("/asignar")
    public ResponseEntity<String> asignarReporte(
            @RequestParam int id_reporte,
            @RequestParam int id_usuario) {
        Optional<Reporte> reporteOpt = reporteRepo.findById(id_reporte);
        Optional<Usuario> usuarioOpt = usuarioRepo.findById((long) id_usuario);
        if (reporteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reporte no encontrado");
        }
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        // ✅ Solo permitir si origen_app es "incidencias"
        if (!usuario.getOrigenApp().equalsIgnoreCase("incidencias")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solo se puede asignar a usuarios de tipo 'incidencias'");
        }
        Reporte reporte = reporteOpt.get();
        reporte.setIdUsuarioAsignado(id_usuario);
        reporteRepo.save(reporte);
        return ResponseEntity.ok("Reporte asignado correctamente");
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Reporte>> listarReportes() {
        List<Reporte> reportes = reporteRepo.findAll();
        return ResponseEntity.ok(reportes);
    }
    

    @GetMapping("/listarReporte")
    public ResponseEntity<List<ReporteDTO>> listarReportesDTO() {
        List<Reporte> reportes = reporteRepo.findAll();
        List<ReporteDTO> resultado = new ArrayList<>();

        for (Reporte reporte : reportes) {
            ReporteDTO dto = new ReporteDTO(reporte, null);
            dto.setIdUsuario(reporte.getIdUsuario());
            dto.setAsunto(reporte.getAsunto());
            dto.setDescripcion(reporte.getDescripcion());
            dto.setEstado(reporte.getEstado());
            dto.setFecha(reporte.getFechaCreacion());

            // Si hay un usuario asignado, buscamos su nombre
            if (reporte.getIdUsuarioAsignado() != null) {
                usuarioRepo.findById((long) reporte.getIdUsuarioAsignado())
                    .ifPresent(usuario -> dto.setNombreAsignado(usuario.getNombrecompleto()));
            } else {
                dto.setNombreAsignado("No asignado");
            }

            resultado.add(dto);
        }

        return ResponseEntity.ok(resultado);
    }

    



    @GetMapping("/usuario")
    public ResponseEntity<List<Reporte>> obtenerReportesPorUsuario(@RequestParam("id_usuario") int idUsuario) {
        List<Reporte> reportes = reporteRepo.findByIdUsuario(idUsuario);
        return ResponseEntity.ok(reportes);
    }

}

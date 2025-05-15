package com.conexion.api.service;

import com.conexion.api.model.Reporte;
import com.conexion.api.model.Notificacion;
import com.conexion.api.repository.NotificacionRepository;
import com.conexion.api.repository.ReporteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final NotificacionRepository notificacionRepository;

    public ReporteService(ReporteRepository reporteRepository, NotificacionRepository notificacionRepository) {
        this.reporteRepository = reporteRepository;
        this.notificacionRepository = notificacionRepository;
    }

    // Crear un nuevo reporte y generar notificación para admin o responsable
    public Reporte crearReporte(Reporte reporte) {
        reporte.setEstado("Abierto");
        Reporte nuevoReporte = reporteRepository.save(reporte);

        // Crear notificación para el admin (idUsuario=0 o el id que corresponda)
        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuario(0); // Cambiar al id del usuario administrador si aplica
        notificacion.setMensaje("Nuevo reporte creado: " + reporte.getAsunto());
        notificacion.setLeido(false);

        notificacionRepository.save(notificacion);

        return nuevoReporte;
    }

    // Listar todos los reportes
    public List<Reporte> listarReportes() {
        return reporteRepository.findAll();
    }

    // Obtener reporte por ID
    public Optional<Reporte> obtenerPorId(int id) {
        return reporteRepository.findById(id);
    }

    // Actualizar estado de un reporte y notificar al usuario
    public Optional<Reporte> actualizarEstado(int idReporte, String nuevoEstado) {
        Optional<Reporte> reporteOpt = reporteRepository.findById(idReporte);

        if (reporteOpt.isPresent()) {
            Reporte reporte = reporteOpt.get();
            reporte.setEstado(nuevoEstado);
            reporteRepository.save(reporte);

            // Crear notificación para el usuario que creó el reporte
            Notificacion notificacion = new Notificacion();
            notificacion.setIdUsuario(reporte.getIdUsuario());
            notificacion.setMensaje("El estado de tu reporte '" + reporte.getAsunto() + "' ha cambiado a: " + nuevoEstado);
            notificacion.setLeido(false);
            notificacionRepository.save(notificacion);

            return Optional.of(reporte);
        } else {
            return Optional.empty();
        }
    }
}

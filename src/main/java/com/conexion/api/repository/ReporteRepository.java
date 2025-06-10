package com.conexion.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.conexion.api.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Integer> {
    List<Reporte> findByIdUsuario(int idUsuario);
    List<Reporte> findByIdUsuarioAsignado(Integer idUsuarioAsignado);
    List<Reporte> findByEstado(String estado);
    List<Reporte> findByEstadoIgnoreCase(String estado);
    List<Reporte> findByIdUsuarioAsignadoAndEstadoIgnoreCase(int idUsuario, String estado);
    List<Reporte> findTop5ByOrderByFechaDesc();
}

package com.conexion.api.dto;

import com.conexion.api.model.Reporte;

public class ReporteDTO {
    private int idUsuario;
    private String asunto;
    private String descripcion;
    private String nombreAsignado;
    private String estado;


    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAsunto() {
        return asunto;
    }
    
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getNombreAsignado() {
        return nombreAsignado;
    }

    public void setNombreAsignado(String nombreAsignado) {
        this.nombreAsignado = nombreAsignado;
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public ReporteDTO(Reporte reporte, String nombreAsignado) {
        this.idUsuario = reporte.getIdUsuario();
        this.asunto = reporte.getAsunto();
        this.descripcion = reporte.getDescripcion();
        this.estado = reporte.getEstado();
        this.nombreAsignado = nombreAsignado != null ? nombreAsignado : "No asignado";
    }

}

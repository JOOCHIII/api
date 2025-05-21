package com.conexion.api.dto;

import java.sql.Timestamp;

public class ReporteDTO {
    private int idUsuario;
    private String asunto;
    private String descripcion;
    private String nombreAsignado;
    private String estado;
    private Timestamp fecha; // ✅ Campo nuevo

    // Getters y setters

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

    public Timestamp getFecha() {          // ✅ Getter correcto
        return fecha;
    }

    public void setFecha(Timestamp fecha) { // ✅ Setter correcto
        this.fecha = fecha;
    }
}

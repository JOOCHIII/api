package com.conexion.api.model;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reporte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int idUsuario;
    private String asunto;
    private String descripcion;
    private String estado;
    @CreationTimestamp
    private Timestamp fechaCreacion;
    private Integer idUsuarioAsignado;
    // Getters y settersx	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Integer getIdUsuarioAsignado() {
		return idUsuarioAsignado;
	}
	public void setIdUsuarioAsignado(Integer idUsuarioAsignado) {
		this.idUsuarioAsignado = idUsuarioAsignado;
	}
    
    
    
}



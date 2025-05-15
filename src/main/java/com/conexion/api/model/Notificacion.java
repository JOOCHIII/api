package com.conexion.api.model;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idUsuario;
    private String mensaje;
    private boolean leido;
    private Timestamp fecha;
    private String tipoDestino; 
    // Getters y setters
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
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isLeido() {
		return leido;
	}	
	public void setLeido(boolean leido) {
		this.leido = leido;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public String getTipoDestino() {
		return tipoDestino;
	}
	public void setTipoDestino(String tipoDestino) {
		this.tipoDestino = tipoDestino;
	}
	
}


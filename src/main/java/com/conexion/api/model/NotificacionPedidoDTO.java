package com.conexion.api.model;

import java.time.LocalDateTime;

public class NotificacionPedidoDTO {
    private Long id;
    private String mensaje;
    private LocalDateTime fecha;
    private boolean leido;
    private Long idUsuario;
    private Long idPedido;

    // Constructor
    public NotificacionPedidoDTO(Long id, String mensaje, LocalDateTime fecha, boolean leido, Long idUsuario, Long idPedido) {
        this.id = id;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.leido = leido;
        this.idUsuario = idUsuario;
        this.idPedido = idPedido;
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

    // getters y setters...
}

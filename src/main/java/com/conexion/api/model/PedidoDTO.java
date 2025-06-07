package com.conexion.api.model;

import java.time.LocalDateTime;

public class PedidoDTO {
    private Long id;
    private String estado;
    private LocalDateTime fecha;
    private Double total;
    private String nombreUsuario;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.estado = pedido.getEstado();
        this.fecha = pedido.getFecha();
        this.total = pedido.getTotal();
        this.nombreUsuario = pedido.getUsuario().getNombrecompleto(); // O getCorreo()
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
    

    // Getters y setters
}

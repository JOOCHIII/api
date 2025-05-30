// PedidoDetalleDTO.java
package com.conexion.api.model;

import java.util.List;
import java.time.LocalDateTime;

public class PedidoDetalleDTO {
    private Long id;
    private LocalDateTime fecha;
    private String estado;
    private double total;
    private List<ItemDetalleDTO> detalles;

    public PedidoDetalleDTO(Long id, LocalDateTime fecha, String estado, double total, List<ItemDetalleDTO> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.detalles = detalles;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<ItemDetalleDTO> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<ItemDetalleDTO> detalles) {
		this.detalles = detalles;
	}

    // Getters y setters
}

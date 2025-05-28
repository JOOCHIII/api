package com.conexion.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "carrito")
public class Carrito {

    @EmbeddedId
    private CarritoId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto", nullable = false)
    private Productos producto;

    private int cantidad;

    private LocalDateTime fechaAgregado;

    public Carrito() {
        this.fechaAgregado = LocalDateTime.now();
    }

    public CarritoId getId() {
        return id;
    }

    public void setId(CarritoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    // Nuevo getter y setter para talla, que usan el id embebido
    public String getTalla() {
        return (id != null) ? id.getTalla() : null;
    }

    public void setTalla(String talla) {
        if (id == null) {
            id = new CarritoId();
        }
        id.setTalla(talla);
    }
}

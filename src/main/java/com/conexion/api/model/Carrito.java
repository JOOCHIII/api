package com.conexion.api.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "carrito")
public class Carrito implements Serializable {

    @EmbeddedId
    private CarritoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto")
    private Productos producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "talla")
    private String talla;

    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado;

    public Carrito() {}

    public Carrito(CarritoId id, Usuario usuario, Productos producto, int cantidad, LocalDateTime fechaAgregado) {
        this.id = id;
        this.usuario = usuario;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fechaAgregado = fechaAgregado;
    }


    // Getters y setters
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

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}

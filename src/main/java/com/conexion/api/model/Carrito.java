package com.conexion.api.model;

import jakarta.persistence.*;
import java.io.Serializable;

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

    @Column(name = "cantidad")
    private int cantidad;

    public Carrito() {}

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
        return id != null ? id.getTalla() : null;
    }

    public void setTalla(String talla) {
        if (id != null) {
            id.setTalla(talla);
        }
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}

package com.conexion.api.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "favoritos")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    // Relación con Productos, enlazada mediante idProducto de FavoritoId
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProducto") // indica que este campo mapea la parte idProducto de FavoritoId
    @JoinColumn(name = "id_producto")
    private Productos producto;

    public Favorito() {}

    public Favorito(FavoritoId id, Productos producto) {
        this.id = id;
        this.producto = producto;
    }

    public FavoritoId getId() {
        return id;
    }

    public void setId(FavoritoId id) {
        this.id = id;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
}

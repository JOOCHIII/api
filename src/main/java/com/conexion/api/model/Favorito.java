package com.conexion.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favoritos")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")  // mapea la parte idProducto del composite key
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

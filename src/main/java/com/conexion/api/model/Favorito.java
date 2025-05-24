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

    // Relación con Productos (muchos favoritos apuntan a un producto)
    @ManyToOne(fetch = FetchType.EAGER)  // eager para que cargue el producto junto al favorito
    @MapsId("idProducto")  // indica que esta relación mapea la parte idProducto del composite key FavoritoId
    @JoinColumn(name = "id_producto")  // columna que une con la tabla productos
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

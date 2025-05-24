package com.conexion.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favoritos")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idUsuario")  // mapea la parte idUsuario del composite key
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")  // mapea la parte idProducto del composite key
    @JoinColumn(name = "id_producto")
    private Productos producto;

    public Favorito() {}

    public Favorito(FavoritoId id, Usuario usuario, Productos producto) {
        this.id = id;
        this.usuario = usuario;
        this.producto = producto;
    }

    public FavoritoId getId() {
        return id;
    }

    public void setId(FavoritoId id) {
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
}



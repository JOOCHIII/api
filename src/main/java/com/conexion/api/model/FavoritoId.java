package com.conexion.api.model;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoritoId implements Serializable {

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_producto")
    private Long idProducto;

    public FavoritoId() {}

    public FavoritoId(Long idUsuario, Long idProducto) {
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoritoId)) return false;
        FavoritoId that = (FavoritoId) o;
        return Objects.equals(getIdUsuario(), that.getIdUsuario()) &&
                Objects.equals(getIdProducto(), that.getIdProducto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUsuario(), getIdProducto());
    }
}
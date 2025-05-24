package com.conexion.api.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarritoId implements Serializable {

    private Long idUsuario;
    private Long idProducto;  // Cambiado a Long

    public CarritoId() {}

    public CarritoId(Long idUsuario, Long idProducto) {
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
        if (!(o instanceof CarritoId)) return false;
        CarritoId that = (CarritoId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
               Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idProducto);
    }
}

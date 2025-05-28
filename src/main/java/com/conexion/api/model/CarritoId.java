package com.conexion.api.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarritoId implements Serializable {

    private Long idUsuario;
    private Long idProducto;
    private String talla;  // Nuevo campo añadido

    public CarritoId() {}

    public CarritoId(Long idUsuario, Long idProducto, String talla) {
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.talla = talla;
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

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarritoId)) return false;
        CarritoId that = (CarritoId) o;
        return Objects.equals(idUsuario, that.idUsuario) &&
               Objects.equals(idProducto, that.idProducto) &&
               Objects.equals(talla, that.talla);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idProducto, talla);
    }
}

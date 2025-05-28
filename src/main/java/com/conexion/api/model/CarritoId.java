package com.conexion.api.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CarritoId implements Serializable {

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "talla")
    private String talla;

    public CarritoId() {}

    public CarritoId(Long idUsuario, Long idProducto, String talla) {
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.talla = talla;
    }

    // Getters y setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    // equals y hashCode (muy importantes para clave compuesta)
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

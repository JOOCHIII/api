package com.conexion.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarritoDTO {
    private Long idProducto;
    private String nombreProducto;
    private String descripcionProducto;
    private double precioProducto;
    private int cantidad;
    private String talla;
    private String urlImagen;  // Nueva propiedad

    public CarritoDTO(Long idProducto, String nombreProducto, String descripcionProducto,
                      double precioProducto, int cantidad, String talla, String urlImagen) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.precioProducto = precioProducto;
        this.cantidad = cantidad;
        this.talla = talla;
        this.urlImagen = urlImagen;
    }

    // Getters y setters

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
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
    @JsonProperty("imgurl")
    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}

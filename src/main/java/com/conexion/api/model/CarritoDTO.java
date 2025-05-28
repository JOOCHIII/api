package com.conexion.api.model;

import java.util.List;

public class CarritoDTO {
    private Long idProducto;
    private String nombreProducto;
    private String talla;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private List<String> fotos;

    public CarritoDTO(Long idProducto, String nombreProducto, String talla, int cantidad, double precioUnitario, List<String> fotos) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.talla = talla;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario * cantidad;
        this.fotos = fotos;
    }

    // Getters y setters (puedes usar lombok si quieres)

    public Long getIdProducto() { return idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public String getTalla() { return talla; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
    public List<String> getFotos() { return fotos; }

    // No setters si solo quieres respuesta inmutable
}

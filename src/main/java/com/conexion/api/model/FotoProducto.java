package com.conexion.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "fotos_producto")
public class FotoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String urlFoto;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonBackReference
    private Productos producto;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    
    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public Productos getProducto() { return producto; }
    public void setProducto(Productos producto) { this.producto = producto; }
}

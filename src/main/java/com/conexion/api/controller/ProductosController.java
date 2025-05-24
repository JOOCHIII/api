package com.conexion.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexion.api.model.FotoProducto;
import com.conexion.api.model.Productos;
import com.conexion.api.repository.ProductosRepository;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin
public class ProductosController {

    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping
    public List<Productos> obtenerTodos() {
        return productosRepository.findAll();
    }

    @PostMapping
    public Productos crearProducto(@RequestBody Productos producto) {
        // Asocia las fotos con el producto antes de guardar
        for (FotoProducto foto : producto.getFotos()) {
            foto.setProducto(producto);
        }
        return productosRepository.save(producto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Productos> obtenerPorId(@PathVariable int id) {
        Optional<Productos> producto = productosRepository.findById((long) id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

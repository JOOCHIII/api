package com.conexion.api.controller;

import java.util.ArrayList;
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
import com.conexion.api.model.ProductoRequestDTO;
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
    public ResponseEntity<Productos> obtenerPorId(@PathVariable Long id) {
        Optional<Productos> producto = productosRepository.findById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crear")
    public Productos crearProducto(@RequestBody ProductoRequestDTO request) {
        Productos nuevoProducto = new Productos();
        nuevoProducto.setNombre(request.getNombre());
        nuevoProducto.setDescripcion(request.getDescripcion());
        nuevoProducto.setPrecio(request.getPrecio());
        nuevoProducto.setStock(request.getStock());
        nuevoProducto.setCategoria(request.getCategoria());
        nuevoProducto.setDestacado(request.isDestacado());

        // Asignar fotos
        List<FotoProducto> fotos = new ArrayList<>();
        if (request.getFotos() != null) {
            for (String url : request.getFotos()) {
                FotoProducto foto = new FotoProducto();
                foto.setUrlFoto(url);
                foto.setProducto(nuevoProducto);  // Relación bidireccional
                fotos.add(foto);
            }
        }

        nuevoProducto.setFotos(fotos);

        return productosRepository.save(nuevoProducto);
    }
 // Total de productos
    @GetMapping("/api/productos/count")
    public Long contarProductos() {
        return productosRepository.count();
    }

    // Productos en borrador (ej. si `stock == 0` los consideras "borrador")
    @GetMapping("/api/productos/count/borrador")
    public Long contarBorradores() {
        return productosRepository.countByStock(0);
    }

}

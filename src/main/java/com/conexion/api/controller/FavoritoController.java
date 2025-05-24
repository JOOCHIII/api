package com.conexion.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conexion.api.model.Favorito;
import com.conexion.api.model.FavoritoId;
import com.conexion.api.model.Productos;
import com.conexion.api.repository.FavoritoRepository;
import com.conexion.api.repository.ProductosRepository;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarFavorito(@RequestParam Long idUsuario, @RequestParam Long idProducto) {
        FavoritoId id = new FavoritoId(idUsuario, idProducto);

        if (favoritoRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("El favorito ya existe");
        }

        // Obtener el producto de la base de datos
        Productos producto = productosRepository.findById(idProducto).orElse(null);
        if (producto == null) {
            return ResponseEntity.badRequest().body("Producto no encontrado");
        }

        Favorito favorito = new Favorito(id, producto);
        favoritoRepository.save(favorito);
        return ResponseEntity.ok("Favorito agregado");
    }


    @GetMapping("/usuario")
    public ResponseEntity<List<Productos>> obtenerFavoritos(@RequestParam Long idUsuario) {
        List<Productos> favoritos = favoritoRepository.findProductosFavoritosByUsuario(idUsuario);
        if (favoritos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoritos);
    }

}

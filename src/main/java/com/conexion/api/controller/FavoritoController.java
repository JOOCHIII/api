package com.conexion.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.model.Favorito;
import com.conexion.api.model.FavoritoId;
import com.conexion.api.model.Productos;
import com.conexion.api.repository.FavoritoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarFavorito(@RequestParam Long idUsuario, @RequestParam Long idProducto) {
        FavoritoId id = new FavoritoId(idUsuario, idProducto);

        if (favoritoRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("El favorito ya existe");
        }

        Favorito favorito = new Favorito();
        favorito.setId(id);
        favoritoRepository.save(favorito);
        return ResponseEntity.ok("Favorito agregado");
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarFavorito(@RequestParam Long idUsuario, @RequestParam Long idProducto) {
        FavoritoId id = new FavoritoId(idUsuario, idProducto);

        if (!favoritoRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("El favorito no existe");
        }

        favoritoRepository.deleteById(id);
        return ResponseEntity.ok("Favorito eliminado");
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Productos>> obtenerFavoritos(@PathVariable Long idUsuario) {
        List<Productos> favoritos = favoritoRepository.findProductosFavoritosByUsuario(idUsuario);
        if (favoritos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoritos);
    }
}

package com.conexion.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.model.Favorito;
import com.conexion.api.model.FavoritoId;
import com.conexion.api.model.Productos;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.FavoritoRepository;
import com.conexion.api.repository.ProductosRepository;
import com.conexion.api.repository.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarFavorito(@RequestParam Long idUsuario, @RequestParam Long idProducto) {
        FavoritoId id = new FavoritoId(idUsuario, idProducto);

        if (favoritoRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("El favorito ya existe");
        }

        Productos producto = productosRepository.findById(idProducto).orElse(null);
        if (producto == null) {
            return ResponseEntity.badRequest().body("Producto no encontrado");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        Favorito favorito = new Favorito(id, usuario, producto);
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

    @GetMapping("/usuario")
    public ResponseEntity<List<Productos>> obtenerFavoritos(@RequestParam Long idUsuario) {
        List<Productos> favoritos = favoritoRepository.findProductosFavoritosByUsuario(idUsuario);
        if (favoritos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(favoritos);
    }
}

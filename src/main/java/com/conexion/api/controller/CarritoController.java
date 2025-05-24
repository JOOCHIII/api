package com.conexion.api.controller;

import com.conexion.api.model.Carrito;
import com.conexion.api.model.CarritoId;
import com.conexion.api.model.Productos;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.CarritoRepository;
import com.conexion.api.repository.ProductosRepository;
import com.conexion.api.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarAlCarrito(@RequestParam Long idUsuario,
                                              @RequestParam Long idProducto,
                                              @RequestParam int cantidad) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Productos producto = productosRepository.findById(idProducto).orElse(null);

        if (usuario == null || producto == null) {
            return ResponseEntity.badRequest().body("Usuario o producto no encontrado");
        }

        CarritoId carritoId = new CarritoId(idUsuario, idProducto);
        Carrito carrito = new Carrito();
        carrito.setId(carritoId);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setCantidad(cantidad);

        carritoRepository.save(carrito);

        return ResponseEntity.ok("Producto agregado al carrito");
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<Carrito>> obtenerCarritoUsuario(@RequestParam Long idUsuario) {
        List<Carrito> carrito = carritoRepository.findByUsuarioId(idUsuario);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarDelCarrito(@RequestParam Long idUsuario, @RequestParam Long idProducto) {
        carritoRepository.deleteByUsuarioIdAndProductoId(idUsuario, idProducto);
        return ResponseEntity.ok("Producto eliminado del carrito");
    }
}

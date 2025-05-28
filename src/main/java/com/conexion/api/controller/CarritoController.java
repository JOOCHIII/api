package com.conexion.api.controller;

import com.conexion.api.model.Carrito;
import com.conexion.api.model.CarritoDTO;
import com.conexion.api.model.CarritoId;
import com.conexion.api.model.Productos;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.CarritoRepository;
import com.conexion.api.repository.ProductosRepository;
import com.conexion.api.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

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
                                              @RequestParam String talla,
                                              @RequestParam int cantidad) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        Productos producto = productosRepository.findById(idProducto).orElse(null);

        if (usuario == null || producto == null) {
            return ResponseEntity.badRequest().body("Usuario o producto no encontrado");
        }

        CarritoId carritoId = new CarritoId(idUsuario, idProducto, talla);
        Carrito carrito = carritoRepository.findById(carritoId).orElse(new Carrito());

        carrito.setId(carritoId);
        carrito.setUsuario(usuario);
        carrito.setProducto(producto);
        carrito.setTalla(talla);
        carrito.setCantidad(carrito.getCantidad() + cantidad);  // Si ya existía suma cantidad

        carritoRepository.save(carrito);

        return ResponseEntity.ok("Producto agregado al carrito");
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<CarritoDTO>> obtenerCarritoUsuario(@RequestParam Long idUsuario) {
        List<Carrito> carrito = carritoRepository.findByUsuarioId(idUsuario);

        List<CarritoDTO> carritoDTO = carrito.stream().map(c -> {
            List<String> fotosUrls = c.getProducto().getFotos().stream()
                .map(f -> f.getUrlFoto())
                .toList();

            return new CarritoDTO(
                c.getProducto().getId(),
                c.getProducto().getNombre(),
                c.getId().getTalla(),  // talla desde id compuesto
                c.getCantidad(),
                c.getProducto().getPrecio(),
                fotosUrls
            );
        }).toList();

        return ResponseEntity.ok(carritoDTO);
    }


    @Transactional
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarDelCarrito(@RequestParam Long idUsuario,
                                                @RequestParam Long idProducto,
                                                @RequestParam String talla) {
        CarritoId carritoId = new CarritoId(idUsuario, idProducto, talla);
        if (carritoRepository.existsById(carritoId)) {
            carritoRepository.deleteById(carritoId);
            return ResponseEntity.ok("Producto eliminado del carrito");
        } else {
            return ResponseEntity.badRequest().body("Producto no encontrado en el carrito");
        }
    }

    @Transactional
    @PutMapping("/actualizarCantidad")
    public ResponseEntity<?> actualizarCantidad(@RequestParam Long idUsuario,
                                                @RequestParam Long idProducto,
                                                @RequestParam String talla,
                                                @RequestParam int cantidad) {
        CarritoId carritoId = new CarritoId(idUsuario, idProducto, talla);
        if (carritoRepository.existsById(carritoId)) {
            Carrito carrito = carritoRepository.findById(carritoId).get();
            carrito.setCantidad(cantidad);
            carritoRepository.save(carrito);
            return ResponseEntity.ok("Cantidad actualizada");
        } else {
            return ResponseEntity.badRequest().body("Producto no encontrado en el carrito");
        }
    }
}

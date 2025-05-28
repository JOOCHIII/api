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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        CarritoId carritoId = new CarritoId(idUsuario, idProducto, talla);
        Optional<Carrito> carritoExistente = carritoRepository.findById(carritoId);

        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();
            carrito.setCantidad(carrito.getCantidad() + cantidad);
            carrito.setFechaAgregado(LocalDateTime.now());
            carritoRepository.save(carrito);
            return ResponseEntity.ok("Producto actualizado en el carrito");
        } else {
            Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
            Productos producto = productosRepository.findById(idProducto).orElse(null);
            if (usuario == null || producto == null) {
                return ResponseEntity.badRequest().body("Usuario o producto no encontrado");
            }

            Carrito nuevoCarrito = new Carrito(carritoId, usuario, producto, cantidad, LocalDateTime.now());
            carritoRepository.save(nuevoCarrito);
            return ResponseEntity.ok("Producto agregado al carrito");
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<CarritoDTO>> obtenerCarritoUsuario(@RequestParam Long idUsuario) {
        List<Carrito> carrito = carritoRepository.findByUsuarioId(idUsuario);

        List<CarritoDTO> carritoDTOs = carrito.stream()
            .map(c -> {
                if (c.getProducto() == null) return null;
                return new CarritoDTO(
                    c.getProducto().getId(),
                    c.getProducto().getNombre(),
                    c.getProducto().getDescripcion(),
                    c.getProducto().getPrecio(),
                    c.getCantidad(),
                    c.getTalla()
                );
            })
            .filter(dto -> dto != null)
            .collect(Collectors.toList());

        return ResponseEntity.ok(carritoDTOs);
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
        Optional<Carrito> carritoOptional = carritoRepository.findById(carritoId);

        if (carritoOptional.isPresent()) {
            Carrito carrito = carritoOptional.get();
            carrito.setCantidad(cantidad);
            carritoRepository.save(carrito);
            return ResponseEntity.ok("Cantidad actualizada");
        } else {
            return ResponseEntity.badRequest().body("Producto no encontrado en el carrito");
        }
    }
}


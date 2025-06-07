package com.conexion.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.model.Carrito;
import com.conexion.api.model.DetallePedido;
import com.conexion.api.model.ItemDetalleDTO;
import com.conexion.api.model.NotificacionPedido;
import com.conexion.api.model.Pedido;
import com.conexion.api.model.PedidoDetalleDTO;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.CarritoRepository;
import com.conexion.api.repository.DetallePedidoRepository;
import com.conexion.api.repository.NotificacionPedidoRepository;
import com.conexion.api.repository.PedidoRepository;
import com.conexion.api.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacionPedidoRepository notificacionPedidoRepository;

    // 1. Tramitar pedido
    @PostMapping("/tramitar")
    public ResponseEntity<?> tramitarPedido(@RequestParam Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        List<Carrito> carritoItems = carritoRepository.findByUsuarioId(idUsuario);
        if (carritoItems.isEmpty()) {
            return ResponseEntity.badRequest().body("El carrito está vacío");
        }

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("pendiente");

        // Calcular total
        double total = 0.0;
        for (Carrito item : carritoItems) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        pedido.setTotal(total);

        pedido = pedidoRepository.save(pedido);

        // Agregar detalles del pedido
        for (Carrito item : carritoItems) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setTalla(item.getTalla());
            detalle.setPrecioUnitario(item.getProducto().getPrecio());
            detallePedidoRepository.save(detalle);
        }

        // Vaciar carrito
        carritoRepository.deleteAll(carritoItems);

        // Crear notificación
        NotificacionPedido noti = new NotificacionPedido();
        noti.setUsuario(usuario);
        noti.setMensaje("Tu pedido ha sido tramitado correctamente.");
        noti.setFecha(LocalDateTime.now());
        noti.setLeido(false);
        noti.setPedido(pedido);
        notificacionPedidoRepository.save(noti);

        return ResponseEntity.ok("Pedido tramitado con éxito");
    }

    // 2. Cambiar estado del pedido
    @PutMapping("/cambiar-estado")
    public ResponseEntity<?> cambiarEstadoPedido(
            @RequestParam Long idPedido,
            @RequestParam String nuevoEstado) {

        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido == null) {
            return ResponseEntity.badRequest().body("Pedido no encontrado");
        }

        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);

        // Crear notificación
        NotificacionPedido noti = new NotificacionPedido();
        noti.setUsuario(pedido.getUsuario());
        noti.setMensaje("El estado de tu pedido ha cambiado a: " + nuevoEstado);
        noti.setFecha(LocalDateTime.now());
        noti.setLeido(false);
        noti.setPedido(pedido);
        notificacionPedidoRepository.save(noti);

        return ResponseEntity.ok("Estado del pedido actualizado a: " + nuevoEstado);
    }

    // 3. Obtener pedidos por usuario
    @GetMapping("/usuario")
    public ResponseEntity<?> obtenerPedidosPorUsuario(@RequestParam Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(idUsuario);
        return ResponseEntity.ok(pedidos);
    }

    // 4. Obtener detalles de un pedido
    @GetMapping("/detalles")
    public ResponseEntity<?> obtenerDetallesDePedido(@RequestParam Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido == null) {
            return ResponseEntity.badRequest().body("Pedido no encontrado");
        }

        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(idPedido);

        List<ItemDetalleDTO> items = detalles.stream().map(detalle -> {
            String imagenUrl = detalle.getProducto().getFotos() != null && !detalle.getProducto().getFotos().isEmpty()
                    ? detalle.getProducto().getFotos().get(0).getUrlFoto()
                    : "https://tudominio.com/imagen-defecto.jpg";

            return new ItemDetalleDTO(
                    detalle.getProducto().getNombre(),
                    detalle.getCantidad(),
                    detalle.getTalla(),
                    detalle.getPrecioUnitario(),
                    imagenUrl
            );
        }).toList();

        PedidoDetalleDTO respuesta = new PedidoDetalleDTO(
                pedido.getId(),
                pedido.getFecha(),
                pedido.getEstado(),
                pedido.getTotal(),
                items
        );

        return ResponseEntity.ok(respuesta);
    }
 // 5. Obtener todos los pedidos (uso de administrador)
    @GetMapping("/todos")
    public ResponseEntity<List<Pedido>> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }


}

package com.conexion.api.controller;

import com.conexion.api.dto.UsuarioEditar;
import com.conexion.api.model.LoginResponse;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    //ELIMINAR CUENTA 

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id, @RequestParam String origenApp) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!usuario.getOrigenApp().equals(origenApp)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No tienes permiso para eliminar este usuario");
        }
        
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String usuario, @RequestParam String contrasena,
                                   @RequestParam String origen_app) {

        Usuario usuarioEncontrado = usuarioRepository.findByUsuario(usuario);

        if (usuarioEncontrado == null) {
            return ResponseEntity.ok("USUARIO_NO_EXISTE");
        }

        if (!usuarioEncontrado.getContrasena().equals(contrasena)) {
            return ResponseEntity.ok("CONTRASENA_INCORRECTA");
        }

        if (!usuarioEncontrado.getOrigenApp().equals(origen_app)) {
            return ResponseEntity.ok("ACCESO_DENEGADO_ORIGEN_APP");
        }

        // Aquí está la clave
        LoginResponse respuesta = new LoginResponse(
            usuarioEncontrado.getId(),  // <-- Asegúrate que esto sea el ID correcto
            usuarioEncontrado.getUsuario(),
            "ACCESO_CONCEDIDO"
        );

        return ResponseEntity.ok(respuesta);
    }

//EDITAR DATOS DEL USUARIO

    // ENUM para los códigos de error
    public enum ErrorCode {
        NOMBRE_DUPLICADO, CORREO_DUPLICADO, TELEFONO_DUPLICADO, USUARIO_DUPLICADO
    }

    // DTO para respuesta de error
    public static class ErrorResponse {
        private ErrorCode error;
        private String mensaje;

        public ErrorResponse(ErrorCode error, String mensaje) {
            this.error = error;
            this.mensaje = mensaje;
        }

        public ErrorCode getError() { return error; }
        public String getMensaje() { return mensaje; }
    }

    // Método PUT mejorado para actualizar usuario con validaciones y respuestas JSON
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioEditar dto) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "USUARIO_NO_ENCONTRADO", "mensaje", "Usuario no encontrado"));
        }

        Usuario usuario = optionalUsuario.get();

        // Validaciones con campos no nulos ni vacíos
        if (dto.getNombrecompleto() != null && !dto.getNombrecompleto().isBlank()) {
            if (usuarioRepository.existsByNombrecompletoAndIdNot(dto.getNombrecompleto(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(ErrorCode.NOMBRE_DUPLICADO, "El nombre ya está en uso"));
            }
            usuario.setNombrecompleto(dto.getNombrecompleto());
        }

        if (dto.getCorreo() != null && !dto.getCorreo().isBlank()) {
            if (usuarioRepository.existsByCorreoAndIdNot(dto.getCorreo(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(ErrorCode.CORREO_DUPLICADO, "El correo ya está en uso"));
            }
            usuario.setCorreo(dto.getCorreo());
        }

        if (dto.getTelefono() != null && !dto.getTelefono().isBlank()) {
            if (usuarioRepository.existsByTelefonoAndIdNot(dto.getTelefono(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(ErrorCode.TELEFONO_DUPLICADO, "El teléfono ya está en uso"));
            }
            usuario.setTelefono(dto.getTelefono());
        }

        if (dto.getUsuario() != null && !dto.getUsuario().isBlank()) {
            if (usuarioRepository.existsByUsuarioAndIdNot(dto.getUsuario(), id)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(ErrorCode.USUARIO_DUPLICADO, "El usuario ya está en uso"));
            }
            usuario.setUsuario(dto.getUsuario());
        }

        if (dto.getContrasena() != null && !dto.getContrasena().isEmpty()) {
            // Aquí puedes cifrar la contraseña si usas cifrado antes de guardar
            usuario.setContrasena(dto.getContrasena());
        }

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado correctamente"));
    }

   
    @GetMapping("/incidencias")
    public ResponseEntity<List<Usuario>> obtenerUsuariosIncidencias() {
        List<Usuario> usuariosIncidencias = usuarioRepository.findByOrigenApp("incidencias");
        return ResponseEntity.ok(usuariosIncidencias);
    }

    @PostMapping("/registro")
    public String registrarUsuario(
        @RequestParam String nombrecompleto,
        @RequestParam String correo,
        @RequestParam String telefono,
        @RequestParam String usuario,
        @RequestParam String contrasena,
        @RequestParam String origen_app) {
        if (usuarioRepository.existsByUsuario(usuario)) {
            return "Usuario ya registrado";
        }
        if (usuarioRepository.existsByCorreo(correo)) {
            return "Correo ya registrado";
        }
        if (usuarioRepository.existsByTelefono(telefono)) {
            return "Teléfono ya registrado";
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombrecompleto(nombrecompleto);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setUsuario(usuario);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setOrigenApp(origen_app);

        usuarioRepository.save(nuevoUsuario);
        return "Usuario registrado correctamente";
    }
    
    
    // OBTENER DATOS DEL USUARIO PARA EL PERFIL
    @GetMapping("/buscar/{usuario}")
    public Usuario getUsuarioPorNombre(@PathVariable String usuario) {
        Usuario usuarioEncontrado = usuarioRepository.findByUsuario(usuario);
        if (usuarioEncontrado == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuarioEncontrado;
    }


}

package com.conexion.api.controller;

import com.conexion.api.dto.UsuarioEditar;
import com.conexion.api.model.LoginResponse;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioEditar dto) {
        // Validar existencia del usuario
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (!optionalUsuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        Usuario usuario = optionalUsuario.get();

        // Actualizar campos
        usuario.setNombrecompleto(dto.getNombrecompleto());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setUsuario(dto.getUsuario());

        if (dto.getContrasena() != null && !dto.getContrasena().isEmpty()) {
            // Aquí cifrar la contraseña antes de guardar si usas cifrado
            usuario.setContrasena(dto.getContrasena());
        }

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario actualizado correctamente");
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

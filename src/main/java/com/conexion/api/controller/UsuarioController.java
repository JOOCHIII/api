package com.conexion.api.controller;

import com.conexion.api.model.LoginResponse;
import com.conexion.api.model.Usuario;
import com.conexion.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
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

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
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


}

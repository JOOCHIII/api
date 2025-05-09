package com.conexion.api.controller;

import com.conexion.api.model.Usuario;
import com.conexion.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String login(@RequestParam String usuario, @RequestParam String contrasena) {
        Usuario usuarioEncontrado = usuarioRepository.findByUsuario(usuario);

        if (usuarioEncontrado == null) {
            return "USUARIO_NO_EXISTE";
        } else if (!usuarioEncontrado.getContrasena().equals(contrasena)) {
            return "CONTRASENA_INCORRECTA";
        } else {
            return "ACCESO_CONCEDIDO";
        }
    }
}

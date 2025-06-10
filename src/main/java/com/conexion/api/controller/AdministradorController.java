package com.conexion.api.controller;

import com.conexion.api.model.Administrador;
import com.conexion.api.model.LoginResponse;
import com.conexion.api.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorRepository administradorRepository;

    @GetMapping
    public List<Administrador> getAllAdministradores() {
        return administradorRepository.findAll();
    }

    @PostMapping
    public Administrador createAdministrador(@RequestBody Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @GetMapping("/{id}")
    public Administrador getAdministradorById(@PathVariable Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrador(@PathVariable Long id) {
        administradorRepository.deleteById(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdministrador(
            @RequestParam String usuarioadmin,
            @RequestParam String contrasenaadmin,
            @RequestParam String origen_app) {

        Administrador admin = administradorRepository.findByUsuarioadmin(usuarioadmin);

        if (admin == null) {
            return ResponseEntity.status(404).body(new LoginResponse(null, usuarioadmin, "USUARIO_NO_EXISTE"));
        }

        if (!admin.getContrasenaAdmin().equals(contrasenaadmin)) {
            return ResponseEntity.status(401).body(new LoginResponse(null, usuarioadmin, "CONTRASENA_INCORRECTA"));
        }

        if (!admin.getOrigenApp().equals(origen_app)) {
            return ResponseEntity.status(403).body(new LoginResponse(null, usuarioadmin, "ACCESO_DENEGADO_ORIGEN_APP"));
        }

        return ResponseEntity.ok(new LoginResponse(admin.getId(), usuarioadmin, "ACCESO_CONCEDIDO"));
    }


    @PostMapping("/registro")
    public String registrarAdministrador(
            @RequestParam String nombrecompletoadmin,
            @RequestParam String correoadmin,
            @RequestParam String telefonoadmin,
            @RequestParam String usuarioadmin,
            @RequestParam String contrasenaadmin,
            @RequestParam String origen_app) {

        if (administradorRepository.findByUsuarioadmin(usuarioadmin) != null) {
            return "Usuario ya registrado";
        }

        if (administradorRepository.findByCorreoadmin(correoadmin) != null) {
            return "Correo ya registrado";
        }

        if (telefonoadmin != null && !telefonoadmin.isEmpty()) {
            List<Administrador> admins = administradorRepository.findAll();
            for (Administrador a : admins) {
                if (telefonoadmin.equals(a.getTelefonoAdmin())) {
                    return "Teléfono ya registrado";
                }
            }
        }

        Administrador administrador = new Administrador();
        administrador.setNombrecompletoAdmin(nombrecompletoadmin);
        administrador.setCorreoAdmin(correoadmin);
        administrador.setTelefonoAdmin(telefonoadmin);
        administrador.setUsuarioAdmin(usuarioadmin);
        administrador.setContrasenaAdmin(contrasenaadmin);
        administrador.setOrigenApp(origen_app);

        administradorRepository.save(administrador);
        return "Administrador registrado correctamente";
    }
    
    //OBTENER DATOS DEL ADMINSITRADOR 
    @GetMapping("/usuario/{usuarioadmin}")
    public ResponseEntity<?> getAdministradorByUsuario(@PathVariable String usuarioadmin) {
        Administrador admin = administradorRepository.findByUsuarioadmin(usuarioadmin);

        if (admin == null) {
            return ResponseEntity.status(404).body("Administrador no encontrado");
        }

        return ResponseEntity.ok(admin);
    }

}

package com.conexion.api.repository;

import com.conexion.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsuario(String usuario);
    Usuario findByCorreo(String correo);


        boolean existsByUsuario(String usuario);

        boolean existsByCorreo(String correo);

        boolean existsByTelefono(String telefono);
    }



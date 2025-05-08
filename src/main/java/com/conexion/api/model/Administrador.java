package com.conexion.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombrecompletoadmin", nullable = false)
    private String nombrecompletoadmin;

    @Column(name = "correoadmin", nullable = false, unique = true)
    private String correoadmin;

    @Column(name = "telefonoadmin", unique = true)
    private String telefonoadmin;

    @Column(name = "usuarioadmin", nullable = false, unique = true)
    private String usuarioadmin;

    @Column(name = "contrasenaadmin", nullable = false)
    private String contrasenaadmin;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombrecompletoAdmin() { return nombrecompletoadmin; }
    public void setNombrecompletoAdmin(String nombrecompletoadmin) { this.nombrecompletoadmin = nombrecompletoadmin; }

    public String getCorreoAdmin() { return correoadmin; }
    public void setCorreoAdmin(String correoadmin) { this.correoadmin = correoadmin; }

    public String getTelefonoAdmin() { return telefonoadmin; }
    public void setTelefonoAdmin(String telefonoadmin) { this.telefonoadmin = telefonoadmin; }

    public String getUsuarioAdmin() { return usuarioadmin; }
    public void setUsuarioAdmin(String usuarioadmin) { this.usuarioadmin = usuarioadmin; }

    public String getContrasenaAdmin() { return contrasenaadmin; }
    public void setContrasenaAdmin(String contrasenaadmin) { this.contrasenaadmin = contrasenaadmin; }
}

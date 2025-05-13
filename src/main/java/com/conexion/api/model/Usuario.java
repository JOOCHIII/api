package com.conexion.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("nombreCompleto")

    @Column(nullable = false)
    private String nombrecompleto;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(unique = true)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String contrasena;
    
   
    @Column(name = "origen_app", nullable = false)
    private String origenApp;


    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombrecompleto() { return nombrecompleto; }
    public void setNombrecompleto(String nombrecompleto) { this.nombrecompleto = nombrecompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getOrigenApp() { return origenApp; }
    public void setOrigenApp(String origenApp) { this.origenApp = origenApp; }

    
}

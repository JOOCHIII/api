package com.conexion.api.model;

public class LoginResponse {
    private Long id;
    private String usuario;
    private String mensaje;

    public LoginResponse(Long id, String usuario, String mensaje) {
        this.id = id;
        this.usuario = usuario;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}

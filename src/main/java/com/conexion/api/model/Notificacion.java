package com.conexion.api.model;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity
public class Notificacion {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_usuario", nullable = true)  // <-- ESTA LÍNEA ES CLAVE
    private Integer idUsuario;

    private String mensaje;
    private boolean leido;
    private Timestamp fecha;
    private String tipoDestino; 

    // Getters y setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeido() {
        return leido;
    }	
    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getTipoDestino() {
        return tipoDestino;
    }
    public void setTipoDestino(String tipoDestino) {
        this.tipoDestino = tipoDestino;
    }
}

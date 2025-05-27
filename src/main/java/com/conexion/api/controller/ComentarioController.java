package com.conexion.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conexion.api.model.Comentario;
import com.conexion.api.repository.ComentarioRepository;

@RestController
@RequestMapping("/api/comentario")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepo;

    // Obtener todos los comentarios de un reporte
    @GetMapping("/porReporte")
    public ResponseEntity<List<Comentario>> getComentariosPorReporte(@RequestParam Integer idReporte) {
        List<Comentario> comentarios = comentarioRepo.findByIdReporteOrderByFechaCreacionAsc(idReporte);
        return ResponseEntity.ok(comentarios);
    }

    // Agregar un comentario nuevo a un reporte
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarComentario(@RequestBody Comentario nuevoComentario) {
        if (nuevoComentario.getIdReporte() == null || nuevoComentario.getIdUsuario() == null || nuevoComentario.getComentario() == null) {
            return ResponseEntity.badRequest().body("Faltan datos obligatorios");
        }
        comentarioRepo.save(nuevoComentario);
        return ResponseEntity.ok("Comentario agregado correctamente");
    }
}

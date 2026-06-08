package com.cibertec.gestionHotel.controller;

import com.cibertec.gestionHotel.DTO.*;
import com.cibertec.gestionHotel.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        try {
            String token = usuariosService.login(dto);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuariosService.crearUsuario(dto));
    }

    @GetMapping("/no-admins")
    public ResponseEntity<List<UsuarioResponseDTO>> listarNoAdmins() {
        return ResponseEntity.ok(usuariosService.listarTodosMenosAdmin());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<UsuarioResponseDTO> editar(@PathVariable Integer id, @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuariosService.editarUsuario(id, dto));
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<?> desactivar(@PathVariable Integer id) {
        try {
            usuariosService.desactivarUsuario(id);
            return ResponseEntity.ok(Map.of("mensaje", "Usuario cambiado a estado INACTIVO con éxito."));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
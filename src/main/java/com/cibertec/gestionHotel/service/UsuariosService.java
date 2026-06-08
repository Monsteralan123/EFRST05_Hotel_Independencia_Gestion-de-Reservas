package com.cibertec.gestionHotel.service;

import com.cibertec.gestionHotel.DTO.*;
import com.cibertec.gestionHotel.model.Roles;
import com.cibertec.gestionHotel.model.Usuarios;
import com.cibertec.gestionHotel.repository.RolesRepository;
import com.cibertec.gestionHotel.repository.UsuariosRepository;
import com.cibertec.gestionHotel.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(LoginRequestDTO dto) {
        Usuarios usuario = usuariosRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("El correo electrónico no existe."));

        if ("INACTIVO".equals(usuario.getEstado())) {
            throw new RuntimeException("Esta cuenta se encuentra desactivada.");
        }

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("La contraseña es incorrecta.");
        }

        return jwtUtil.generateToken(usuario);
    }

    public List<UsuarioResponseDTO> listarTodosMenosAdmin() {
        List<Usuarios> usuarios = usuariosRepository.findByRolNombreRolNot("ADMINISTRADOR");
        return usuarios.stream().map(this::convertirADto).collect(Collectors.toList());
    }

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        if (usuariosRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }
        if (usuariosRepository.findByNroDocumento(dto.getNroDocumento()).isPresent()) {
            throw new RuntimeException("El número de documento ya está registrado.");
        }

        Roles rol = rolesRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("El Rol especificado no existe."));

        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setApellidoUsuario(dto.getApellidoUsuario());
        usuario.setTipoDocumento(dto.getTipoDocumento());
        usuario.setNroDocumento(dto.getNroDocumento());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(rol);
        usuario.setEstado("ACTIVO");
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        Usuarios guardado = usuariosRepository.save(usuario);
        return convertirADto(guardado);
    }

    public UsuarioResponseDTO editarUsuario(Integer id, UsuarioRequestDTO dto) {
        Usuarios usuarioExistente = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));

        Roles rol = rolesRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("El Rol especificado no existe."));

        usuarioExistente.setNombreUsuario(dto.getNombreUsuario());
        usuarioExistente.setApellidoUsuario(dto.getApellidoUsuario());
        usuarioExistente.setTipoDocumento(dto.getTipoDocumento());
        usuarioExistente.setNroDocumento(dto.getNroDocumento());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefono(dto.getTelefono());
        usuarioExistente.setRol(rol);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Usuarios actualizado = usuariosRepository.save(usuarioExistente);
        return convertirADto(actualizado);
    }

    public void desactivarUsuario(Integer id) {
        Usuarios usuario = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        
        usuario.setEstado("INACTIVO");
        usuariosRepository.save(usuario);
    }

    private UsuarioResponseDTO convertirADto(Usuarios usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuarios(usuario.getIdUsuarios());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setApellidoUsuario(usuario.getApellidoUsuario());
        dto.setTipoDocumento(usuario.getTipoDocumento());
        dto.setNroDocumento(usuario.getNroDocumento());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setEstado(usuario.getEstado());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        
        if (usuario.getRol() != null) {
            dto.setNombreRol(usuario.getRol().getNombreRol());
        }
        return dto;
    }
}
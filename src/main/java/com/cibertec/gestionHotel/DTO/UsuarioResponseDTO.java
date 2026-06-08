package com.cibertec.gestionHotel.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Integer idUsuarios;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String tipoDocumento;
    private String nroDocumento;
    private String email;
    private String telefono;
    private String nombreRol;
    private String estado;
    private LocalDateTime fechaCreacion;
}
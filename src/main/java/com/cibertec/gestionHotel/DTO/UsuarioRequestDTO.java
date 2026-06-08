package com.cibertec.gestionHotel.DTO;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
	
    private String nombreUsuario;
    private String apellidoUsuario;
    private String tipoDocumento;
    private String nroDocumento;
    private String email;
    private String password; 
    private String telefono;
    private Integer idRol;   
}
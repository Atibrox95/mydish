package com.nerea.mydish.service.dto;

import lombok.Data;

@Data
public class UsuarioDto {
	private Long idUsuario;
	private String nombre;
	private String apellidos;
	private String correo;
	private String contraseña;
}

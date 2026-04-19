package com.nerea.mydish.service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioDto {
	private Long idUsuario;
	private String nombre;
	private String apellidos;
	private String correo;
	private String contraseña;
	private LocalDate fechaNacimiento;
	private Double altura;
	private Double peso;
	private Double imc;
}

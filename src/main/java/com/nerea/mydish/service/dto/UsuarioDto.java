package com.nerea.mydish.service.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsuarioDto {
	private Long idUsuario;
	@NotBlank(message = "Nombre es obligatorio")
	private String nombre;
	@NotBlank(message = "Apellidos es obligatorio")
	private String apellidos;
	@NotBlank(message = "Correo es obligatorio")
	@Email(message = "Debe tener formato email")
	private String correo;
	@NotBlank(message = "Contraseña es obligatorio")
	@Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!._-])(?=\\S+$).{8,}$",
	    message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un carácter especial"
	)
	private String contraseña;
	@NotNull(message = "Fecha de nacimiento es obligatorio")
	private LocalDate fechaNacimiento;
	private Double altura;
	private Double peso;
	private Double imc;
}

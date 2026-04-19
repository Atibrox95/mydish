package com.nerea.mydish.repository.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "usuarios")
public class UsuarioEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

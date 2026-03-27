package com.nerea.mydish.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "platos")
public class PlatoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPlato;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private UsuarioEntity usuario;
}

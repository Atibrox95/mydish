package com.nerea.mydish.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tipos")
public class TipoEntity {
	@Id
	@GeneratedValue()
	private Long idTipo;
	private String nombre;
}

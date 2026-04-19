package com.nerea.mydish.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "alimentos")
public class AlimentoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAlimento;
	private String nombre;
	private Double cal;
}

package com.nerea.mydish.repository.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PlatoAlimentoId implements Serializable {
	
	private static final long serialVersionUID = 4025236545722348777L;

	private Long idPlato; 
	private Long idAlimento;

}

package com.nerea.mydish.repository.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Data
@Entity(name = "platos_alimentos")
public class PlatoAlimentoEntity {
	
	@EmbeddedId
	private PlatoAlimentoId id;
	
	@ManyToOne
	@MapsId("idPlato")
	@JoinColumn(name = "id_plato")
	private PlatoEntity plato;
	
	@ManyToOne
	@MapsId("idAlimento")
	@JoinColumn(name = "id_alimento")
	private AlimentoEntity alimento;
	
}

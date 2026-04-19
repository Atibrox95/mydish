package com.nerea.mydish.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlatoAlimentoEntity> alimentos = new ArrayList<>();
}

package com.nerea.mydish.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class PlatoDto {
	private Long idPlato;
	private Long idUsuario;
	private List<AlimentoDto> alimentos;
}

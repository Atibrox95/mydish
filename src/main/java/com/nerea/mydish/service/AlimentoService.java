package com.nerea.mydish.service;

import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nerea.mydish.repository.AlimentoRepository;
import com.nerea.mydish.repository.entity.AlimentoEntity;
import com.nerea.mydish.service.dto.AlimentoDto;

@Service
public class AlimentoService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AlimentoRepository alimentoRepository;

	/**
	 * recuperar todos los alimentos
	 * 
	 * @return
	 */
	public List<AlimentoDto> recuperar() {
		List<AlimentoEntity> alimentosEntity = alimentoRepository.findAll();
		List<AlimentoDto> alimentosDto = alimentosEntity.stream()
				.map(alimentoEntity -> modelMapper.map(alimentoEntity, AlimentoDto.class))
				.toList();
		return alimentosDto;
	}

	/**
	 * Método recuperar alimento por tipo
	 * 
	 * @param idTipo
	 * @return
	 */
	public List<AlimentoDto> recuperarPorTipo(Long idTipo) {
		List<AlimentoEntity> alimentosEntity = alimentoRepository.findByTipoIdTipo(idTipo);
		return alimentosEntity.stream().map(entidad -> modelMapper.map(entidad, AlimentoDto.class))
				.sorted(Comparator.comparing(AlimentoDto::getNombre, String.CASE_INSENSITIVE_ORDER)).toList();
	}

}

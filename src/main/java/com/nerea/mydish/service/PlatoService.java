package com.nerea.mydish.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nerea.mydish.repository.PlatoRepository;
import com.nerea.mydish.repository.entity.PlatoEntity;
import com.nerea.mydish.service.dto.PlatoDto;

@Service
public class PlatoService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PlatoRepository platoRepository;

	public List<PlatoDto> recuperar() {
		List<PlatoEntity> platosEntity = platoRepository.findAll();
		List<PlatoDto> platosDto = platosEntity.stream()
				.map(platoEntity -> modelMapper.map(platoEntity, PlatoDto.class)).toList();
		return platosDto;
	}
}

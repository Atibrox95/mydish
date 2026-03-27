package com.nerea.mydish.service;

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

	public List<AlimentoDto> recuperar() {
		List<AlimentoEntity> alimentosEntity = alimentoRepository.findAll();
		List<AlimentoDto> alimentosDto = alimentosEntity.stream()
				.map(alimentoEntity -> modelMapper.map(alimentoEntity, AlimentoDto.class)).toList();
		return alimentosDto;
	}
}

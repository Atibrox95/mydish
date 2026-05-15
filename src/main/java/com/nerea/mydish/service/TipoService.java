package com.nerea.mydish.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nerea.mydish.repository.TipoRepository;
import com.nerea.mydish.repository.entity.TipoEntity;
import com.nerea.mydish.service.dto.TipoDto;

@Service
public class TipoService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private TipoRepository tipoRepository;
	
	/**
	 * @return
	 */
	public List<TipoDto> recuperar(){
		List<TipoEntity> tiposEntity = tipoRepository.findAll();
		List<TipoDto> tiposDto = tiposEntity.stream()
				.map(tipoEntity -> modelMapper.map(tipoEntity, TipoDto.class)).toList();
		return tiposDto;
	}
	
}

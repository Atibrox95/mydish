package com.nerea.mydish.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nerea.mydish.repository.entity.PlatoEntity;
import com.nerea.mydish.service.dto.PlatoDto;

@Configuration
public class ModelMapperConfigurer {
	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.typeMap(PlatoEntity.class, PlatoDto.class)
				.addMappings(mapper -> mapper.map(src -> src.getUsuario().getIdUsuario(), PlatoDto::setIdUsuario));

		return modelMapper;
	}
}

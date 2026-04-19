package com.nerea.mydish.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nerea.mydish.repository.entity.PlatoAlimentoEntity;
import com.nerea.mydish.repository.entity.PlatoEntity;
import com.nerea.mydish.service.dto.AlimentoDto;
import com.nerea.mydish.service.dto.PlatoDto;

@Configuration
public class ModelMapperConfigurer {
	@Bean
	ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		//Ponemos modo estricto para que modelMapper no me devuelva por descarte algo que no quiero.
		//modelMapper sin STRICT -> si no encuentra el parámetro que le he pasado, busca el siguiente que se le parezca
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		//Damos instrucciones a modelMapper de lo que quiero exactamente que recoja de un platoEntity a un platoDto
		modelMapper.typeMap(PlatoEntity.class, PlatoDto.class)
				.addMappings(mapper -> mapper.map(src -> src.getUsuario().getIdUsuario(), PlatoDto::setIdUsuario));

		//Esto es igual que lo anterior, pero recogiendo varias cosas, IdAlimento y Nombre de PlatoAlimentoEntity
		modelMapper.typeMap(PlatoAlimentoEntity.class, AlimentoDto.class)
		.addMappings(mapper -> {
			mapper.map(src -> src.getAlimento().getIdAlimento(), AlimentoDto::setIdAlimento);
			mapper.map(src -> src.getAlimento().getNombre(), AlimentoDto::setNombre);
		});

		return modelMapper;
	}
}
package com.nerea.mydish.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nerea.mydish.repository.AlimentoRepository;
import com.nerea.mydish.repository.PlatoRepository;
import com.nerea.mydish.repository.UsuarioRepository;
import com.nerea.mydish.repository.entity.AlimentoEntity;
import com.nerea.mydish.repository.entity.PlatoAlimentoEntity;
import com.nerea.mydish.repository.entity.PlatoAlimentoId;
import com.nerea.mydish.repository.entity.PlatoEntity;
import com.nerea.mydish.repository.entity.UsuarioEntity;
import com.nerea.mydish.service.dto.AlimentoDto;
import com.nerea.mydish.service.dto.PlatoDto;

@Service
public class PlatoService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PlatoRepository platoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AlimentoRepository alimentoRepository;

	public List<PlatoDto> recuperar() {
		List<PlatoEntity> platosEntity = platoRepository.findAll();
		List<PlatoDto> platosDto = platosEntity.stream()
				.map(platoEntity -> modelMapper.map(platoEntity, PlatoDto.class)).toList();
		return platosDto;
	}

	@Transactional
	public PlatoDto crearPlato(PlatoDto platoDto) {
		UsuarioEntity usuario = usuarioRepository.findById(platoDto.getIdUsuario())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		PlatoEntity platoEntity = new PlatoEntity();
		platoEntity.setUsuario(usuario);

		for (AlimentoDto alimentoDto : platoDto.getAlimentos()) {
			AlimentoEntity alimentoEntity = alimentoRepository.findById(alimentoDto.getIdAlimento())
					.orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

			PlatoAlimentoEntity relacionPlatoAlimento = new PlatoAlimentoEntity();

			PlatoAlimentoId id = new PlatoAlimentoId();
			id.setIdAlimento(alimentoEntity.getIdAlimento());

			relacionPlatoAlimento.setId(id);
			relacionPlatoAlimento.setPlato(platoEntity);
			relacionPlatoAlimento.setAlimento(alimentoEntity);

			platoEntity.getAlimentos().add(relacionPlatoAlimento);
		}
		PlatoEntity platoGuardado = platoRepository.save(platoEntity);
		return modelMapper.map(platoGuardado, PlatoDto.class);
	}
}

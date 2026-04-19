package com.nerea.mydish.service;

import java.util.ArrayList;
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

	// Notación que hace un rollback en caso de que algo no vaya bien
	@Transactional
	public PlatoDto crearPlato(PlatoDto platoDto) {
		// Con esto localizamos al usuarioEntity del plato gracias al platoDto que
		// recibe el IdUsuario
		UsuarioEntity usuario = usuarioRepository.findById(platoDto.getIdUsuario())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		// Creamos el plato donde vinculamos con el usuario encontrado
		PlatoEntity platoEntity = new PlatoEntity();
		platoEntity.setUsuario(usuario);

		// Comienza el bucle para la lista de alimentos
		for (AlimentoDto alimentoDto : platoDto.getAlimentos()) {
			// Busca si el alimento se encuentra en la lista en el entity
			AlimentoEntity alimentoEntity = alimentoRepository.findById(alimentoDto.getIdAlimento())
					.orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

			// Creamos un registro en la tabla PlatoAlimento
			PlatoAlimentoEntity relacionPlatoAlimento = new PlatoAlimentoEntity();

			// Crea la PK combinada de IdAlimento e Id
			PlatoAlimentoId id = new PlatoAlimentoId();
			id.setIdAlimento(alimentoEntity.getIdAlimento());
			relacionPlatoAlimento.setId(id);

			// Conecta la fila de Plato con Alimento
			relacionPlatoAlimento.setPlato(platoEntity);
			relacionPlatoAlimento.setAlimento(alimentoEntity);

			//
			platoEntity.getAlimentos().add(relacionPlatoAlimento);
		}
		PlatoEntity platoGuardado = platoRepository.save(platoEntity);
		return modelMapper.map(platoGuardado, PlatoDto.class);
	}

	// METODO PARA RECUERAR PLATOS POR USUARIO
	public List<PlatoDto> recuperarPlatos(Long idUsuario) {
		// Aqui buscamos todos los platos en repository por idUsuario y lo guardamos en
		// una lista de tipo entity
		List<PlatoEntity> platosRecuperados = platoRepository.findByUsuarioIdUsuario(idUsuario);

		// Ahora hacermos el mapeo de entity a dto
		List<PlatoDto> platosDto = new ArrayList<PlatoDto>();
		for (PlatoEntity platoEntity2 : platosRecuperados) {
			PlatoDto platoDto = modelMapper.map(platoEntity2, PlatoDto.class);
			platosDto.add(platoDto);
		}
		// devolvemos un platoDto
		return platosDto;
	}

	// METODO PARA ACTUALIZAR PLATO GUARDADO
	@Transactional
	public PlatoDto actualizarPLato(Long idPlato, PlatoDto platoDto) {

		// BUSCAMOS EL PLATO POR ID
		PlatoEntity platoExistente = platoRepository.findById(idPlato)
				.orElseThrow(() -> new RuntimeException("Plato no encontrado"));

		// RECOGEMOS EL PLATO Y LO LIMPIAMOS
		platoExistente.getAlimentos().clear();

		//
		for (AlimentoDto alimentoDto : platoDto.getAlimentos()) {
			AlimentoEntity alimentoEntity = alimentoRepository.findById(alimentoDto.getIdAlimento()).orElseThrow(
					() -> new RuntimeException("No se encontró el alimento con ID: " + alimentoDto.getIdAlimento()));

			PlatoAlimentoEntity nuevaRelacion = new PlatoAlimentoEntity();

			PlatoAlimentoId idCompuesto = new PlatoAlimentoId();
			idCompuesto.setIdPlato(platoExistente.getIdPlato());
			idCompuesto.setIdAlimento(alimentoEntity.getIdAlimento());

			nuevaRelacion.setId(idCompuesto);
			nuevaRelacion.setPlato(platoExistente);
			nuevaRelacion.setAlimento(alimentoEntity);

			platoExistente.getAlimentos().add(nuevaRelacion);
		}

		PlatoEntity platoActualizado = platoRepository.save(platoExistente);
		return modelMapper.map(platoActualizado, PlatoDto.class);
	}

	public void borrarPlato(Long idPlato) {
		// bucar plato por id
		PlatoEntity platoEncontrado = platoRepository.findById(idPlato)
				.orElseThrow(() -> new RuntimeException("No hay plato"));
		platoRepository.delete(platoEncontrado);
	}
}

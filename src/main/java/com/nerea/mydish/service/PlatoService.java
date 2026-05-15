package com.nerea.mydish.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nerea.mydish.exception.MyDishException;
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

//TODO cambiar las excepciones por mydish


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

	/**
	 * @param platoDto
	 * @return
	 */
	@Transactional
	// Notación que hace un rollback en caso de que algo no vaya bien
	
	public PlatoDto crearPlato(PlatoDto platoDto) {
		// Con esto localizamos al usuarioEntity del plato gracias al platoDto que
		// recibe el IdUsuario
		UsuarioEntity usuario = usuarioRepository.findById(platoDto.getIdUsuario())
				.orElseThrow(() -> new MyDishException("Usuario no encontrado", HttpStatus.NOT_FOUND));

		// Creamos el plato donde vinculamos con el usuario encontrado
		PlatoEntity platoEntity = new PlatoEntity();
		platoEntity.setUsuario(usuario);
		platoEntity.setNombrePlato(platoDto.getNombrePlato());
		// Comienza el bucle para la lista de alimentos
		for (AlimentoDto alimentoDto : platoDto.getAlimentos()) {
			// Busca si el alimento se encuentra en la lista en el entity
			AlimentoEntity alimentoEntity = alimentoRepository.findById(alimentoDto.getIdAlimento())
					.orElseThrow(() -> new MyDishException("Alimento no encontrado", HttpStatus.NOT_FOUND));

			// Creamos un registro en la tabla PlatoAlimento
			PlatoAlimentoEntity relacionPlatoAlimento = new PlatoAlimentoEntity();

			// Crea la PK combinada de IdAlimento e Id
			PlatoAlimentoId id = new PlatoAlimentoId();
			id.setIdAlimento(alimentoEntity.getIdAlimento());
			relacionPlatoAlimento.setId(id);

			// Conecta la fila de Plato con Alimento
			relacionPlatoAlimento.setPlato(platoEntity);
			relacionPlatoAlimento.setAlimento(alimentoEntity);

			platoEntity.getAlimentos().add(relacionPlatoAlimento);
		}
		PlatoEntity platoGuardado = platoRepository.save(platoEntity);
		return modelMapper.map(platoGuardado, PlatoDto.class);
	}

	/**
	 * Método para recuperar platos por usuario
	 * @param idUsuario
	 * @return
	 */
	public List<PlatoDto> recuperarPlatos(Long idUsuario) {
	    List<PlatoEntity> platosRecuperados = platoRepository.findByUsuarioIdUsuario(idUsuario);
	    List<PlatoDto> platosDto = new ArrayList<>();

	    for (PlatoEntity entity : platosRecuperados) {
	        // Mapeamos lo básico del plato (id, idUsuario)
	        PlatoDto dto = modelMapper.map(entity, PlatoDto.class);
	        
	        // Mapeamos manualmente la lista de alimentos para asegurar que entran por el typeMap correcto
	        List<AlimentoDto> listaAlimentos = entity.getAlimentos().stream()
	            .map(pa -> modelMapper.map(pa, AlimentoDto.class)).sorted(Comparator.comparing(AlimentoDto::getNombre, String.CASE_INSENSITIVE_ORDER))
	            .sorted(Comparator.comparing(AlimentoDto::getIdTipo))
	            .toList();
	        
	        dto.setAlimentos(listaAlimentos);
	        platosDto.add(dto);
	    }
	    return platosDto;
	}
	
	/**
	 * Método para recuperar plato por su id
	 * @param idPlato
	 * @return
	 */
	public PlatoDto recuperarPlatoPorId(Long idPlato) {
	    PlatoEntity plato = platoRepository.findById(idPlato).orElseThrow();
	    PlatoDto platoDto = modelMapper.map(plato, PlatoDto.class);
	    
	    List<AlimentoDto> alimentosOrdenados = plato.getAlimentos().stream()
	        .map(pa -> modelMapper.map(pa, AlimentoDto.class))
	        .toList();
	    
	    platoDto.setAlimentos(alimentosOrdenados);
	    return platoDto;
	}
	/**
	 * Método para actualizar plato guardado
	 * @param idPlato
	 * @param platoDto
	 * @return
	 */
	@Transactional
	public PlatoDto actualizarPlato(Long idPlato, PlatoDto platoDto) {

		// Buscamos plato por id
		PlatoEntity platoExistente = platoRepository.findById(idPlato)
				.orElseThrow(() -> new MyDishException("Plato no encontrado", HttpStatus.NOT_FOUND));

		// Recogemos plato y limpiamos
		platoExistente.getAlimentos().clear();
		platoExistente.setNombrePlato(platoDto.getNombrePlato());

		for (AlimentoDto alimentoDto : platoDto.getAlimentos()) {
			AlimentoEntity alimentoEntity = alimentoRepository.findById(alimentoDto.getIdAlimento()).orElseThrow(
					() -> new MyDishException("Alimento no encontado", HttpStatus.NOT_FOUND));

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

	/**
	 * Método borrar plato
	 * @param idPlato
	 */
	public void borrarPlato(Long idPlato) {
		// bucar plato por id
		PlatoEntity platoEncontrado = platoRepository.findById(idPlato)
				.orElseThrow(() -> new MyDishException("Plato no encontrado", HttpStatus.NOT_FOUND));
		platoRepository.delete(platoEncontrado);
	}
}

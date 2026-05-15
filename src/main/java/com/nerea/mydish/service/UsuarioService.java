package com.nerea.mydish.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nerea.mydish.exception.MyDishException;
import com.nerea.mydish.repository.UsuarioRepository;
import com.nerea.mydish.repository.entity.UsuarioEntity;
import com.nerea.mydish.service.dto.UsuarioDto;

@Service
public class UsuarioService {
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<UsuarioDto> recuperar() {
		List<UsuarioEntity> usuariosEntity = usuarioRepository.findAll();
		List<UsuarioDto> usuariosDto = usuariosEntity.stream()
				// .map tranforma el objeto que le das a la clase que le digas
				// modelMapper es la clase que tiene método map que hace el cambio de clase
				.map(usuarioEntity -> modelMapper.map(usuarioEntity, UsuarioDto.class)).toList();
		return usuariosDto;
	}

	/**
	 * Registro de usuario, create estamos pasando el usuario que me ha llegado en
	 * forma de dto
	 * @param usuario
	 * @return
	 */
	public UsuarioDto registroUsuario(UsuarioDto usuario) {
		// transformamos un dto en una entity porque al usar el metodo save, este recibe
		// un parametro que siempre es una entity
		UsuarioEntity usuarioEntity = modelMapper.map(usuario, UsuarioEntity.class);
		// .save crea o modifica una entidad y siempre recibe una entity
		UsuarioEntity usuarioCreado = usuarioRepository.save(usuarioEntity);
		return modelMapper.map(usuarioCreado, UsuarioDto.class);
	}

	// Alt shitf J -> Generar javadoc
	/**
	 * Inicio de sesión, el usuario introduce correo y contraseña y el método
	 * comprueba si está el correo y si la contraseña coincide.
	/**
	 *Función iniciar sesión 
	 * @param correo
	 * @param contraseña
	 * @return
	 */
	public UsuarioDto inicioSesion(String correo, String contraseña) { 
		// Ya no necesita throws Exception por que tenngo MyDish Exception
	    UsuarioEntity usuarioBuscado = new UsuarioEntity();
	    usuarioBuscado.setCorreo(correo);

	    Example<UsuarioEntity> ejemploUsuario = Example.of(usuarioBuscado);
	    //Un Optional siempre devuelve un Entity
	    Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findOne(ejemploUsuario);

	    // Verificamos si existe el usuario
	    UsuarioEntity usuarioEntity = usuarioOptional
	            .orElseThrow(() -> new MyDishException("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED));

	    // Verificamos la contraseña
	    if (!contraseña.equals(usuarioEntity.getContraseña())) {
	        throw new MyDishException("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
	    }
	    	
	    //Mapeamos ENTITY a clase DTO
	    return modelMapper.map(usuarioEntity, UsuarioDto.class);
	}
	/**
	 * Actualiza los datos de un usuario existente
	 * @param idUsuario  ID del usuario a modificar
	 * @param usuarioDto DTO con los nuevos datos
	 * @return UsuarioDto actualizado
	 */
	public UsuarioDto actualizarUsuario(Long idUsuario, UsuarioDto usuarioDto) {

		// Buscamos usuario por id 
		UsuarioEntity usuarioExistente = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new MyDishException("", HttpStatus.NOT_FOUND));

		// Actualizamos campos 
		// Seteamos los valores que vienen del DTO a la entidad que ya existe en la DB
		usuarioExistente.setNombre(usuarioDto.getNombre());
		usuarioExistente.setApellidos(usuarioDto.getApellidos());
		usuarioExistente.setCorreo(usuarioDto.getCorreo());
		usuarioExistente.setContraseña(usuarioDto.getContraseña());
		usuarioExistente.setAltura(usuarioDto.getAltura());
		usuarioExistente.setPeso(usuarioDto.getPeso());


		// Guardamos entidad actualizada
		// El método .save detecta que el ID ya existe y realiza un UPDATE en lugar de
		// un INSERT
		UsuarioEntity usuarioActualizado = usuarioRepository.save(usuarioExistente);

		// Transformamos y devolvemos un DTO
		return modelMapper.map(usuarioActualizado, UsuarioDto.class);
	}

	
	/**
	 * Método borrar usuario
	 * @param idUsuario
	 */
	public void borrarUsuario(Long idUsuario) {
		//Buscamos usuario por id
		UsuarioEntity usuarioEncontrado = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new MyDishException("Usuario no encontrado", HttpStatus.NOT_FOUND));
		//Borramos el usuario
		usuarioRepository.delete(usuarioEncontrado);
	}

}

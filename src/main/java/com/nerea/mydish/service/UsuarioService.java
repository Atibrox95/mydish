package com.nerea.mydish.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
				//.map tranforma el objeto que le das a la clase que le digas
				//modelMapper es la clase que tiene método map que hace el cambio de clase
				.map(usuarioEntity -> modelMapper.map(usuarioEntity, UsuarioDto.class)).toList();
		return usuariosDto;
	}
	
	/** Registro de usuario, create estamos pasando el usuario que me ha llegado en forma de dto 
	 * @param usuario
	 * @return
	 */
	public UsuarioDto registroUsuario(UsuarioDto usuario) {
		//transformamos un dto en una entity porque al usar el metodo save, este recibe un parametro que siempre es una entity
		UsuarioEntity usuarioEntity = modelMapper.map(usuario, UsuarioEntity.class);		
		//.save crea o modifica una entidad y siempre recibe una entity
		UsuarioEntity usuarioCreado = usuarioRepository.save(usuarioEntity);
		return modelMapper.map(usuarioCreado, UsuarioDto.class);
	}
	
	
	//Alt shitf J
	/** Inicio de sesión, el usuario introduce correo y contraseña y el método comprueba si está el correo y si la contraseña coincide.
	 * @param correo Es el correo del usuario a buscar para iniciar sesión
	 * @param contraseña Es la contraseña del usuario para iniciar sesión
	 * @throws Exception Si no encuentra usuario o la contraseña no coincide
	 */
	
	public void inicioSesion(String correo, String contraseña) throws Exception {
		UsuarioEntity usuarioBuscado = new UsuarioEntity();
		usuarioBuscado.setCorreo(correo);

		// creo un Example (de tipo UsuarioEntity) al que le doy valor -> ejemplo de
		// usuarioBuscado.
		Example<UsuarioEntity> ejemploUsuario = Example.of(usuarioBuscado);

		// Repositorio de Spring devuelve optionals
		// Estoy dando a usuarioOptional un -> Buscame en usuarioRepository algo que se
		// parezca a esto -> ejemploUsuario.
		Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findOne(ejemploUsuario);

		// Comprobamos que el usuarioOpcional está presente
		if (usuarioOptional.isPresent()) {
			UsuarioEntity usuarioEntity = usuarioOptional.get();
			//Si no está lanza un errror
			if (!contraseña.equals(usuarioEntity.getContraseña())) {
				//TODO: crear mi propia excepción y usarla
				throw new Exception();
			}
		} else {
			//TODO: crear mi propia excepción y usarla
			throw new Exception(); 
		}
	}
}

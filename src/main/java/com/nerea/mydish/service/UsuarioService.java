package com.nerea.mydish.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
				.map(usuarioEntity -> modelMapper.map(usuarioEntity, UsuarioDto.class)).toList();
		return usuariosDto;
	}
}

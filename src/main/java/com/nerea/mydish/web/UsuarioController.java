package com.nerea.mydish.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nerea.mydish.service.UsuarioService;
import com.nerea.mydish.service.dto.UsuarioDto;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/usuarios")
	public List<UsuarioDto> recuperar() {
		return usuarioService.recuperar();
	}
}

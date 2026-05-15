package com.nerea.mydish.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nerea.mydish.service.UsuarioService;
import com.nerea.mydish.service.dto.UsuarioDto;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/usuarios")
	public List<UsuarioDto> recuperar() {
		return usuarioService.recuperar();
	}

	@PostMapping("/login")
	public UsuarioDto iniciarSesion(@RequestBody UsuarioDto usuario) throws Exception {
		return usuarioService.inicioSesion(usuario.getCorreo(), usuario.getContraseña());	
	}
	
	//cuando creas algo lo devuelves 
	@PostMapping("/usuarios")
	@ResponseStatus(code = HttpStatus.CREATED)
	//@requesBody es como decir, esto lo debes sacar del body
	public UsuarioDto registroUsuario(@Valid @RequestBody UsuarioDto usuario) {
		return usuarioService.registroUsuario(usuario);
	}
	
	@PutMapping("/usuarios/{idUsuario}")
	public UsuarioDto actualizarUsuario(@PathVariable Long idUsuario,@Valid @RequestBody UsuarioDto usuarioDto) {
	    return usuarioService.actualizarUsuario(idUsuario, usuarioDto);
	}
	
	
	@DeleteMapping("/usuarios/{idUsuario}")
	public void borrarUsuario(@PathVariable Long idUsuario) {
		usuarioService.borrarUsuario(idUsuario);
	}
	
}

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nerea.mydish.service.PlatoService;
import com.nerea.mydish.service.dto.PlatoDto;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PlatoController {
	@Autowired
	private PlatoService platoService;

	@PostMapping("/platos")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PlatoDto crearPlato(@RequestBody PlatoDto plato) {
		return platoService.crearPlato(plato);
	}

	@GetMapping("/platos")
	public List<PlatoDto> recuperarPlatos(@RequestParam Long idUsuario) {
		return platoService.recuperarPlatos(idUsuario);
	}

	@PutMapping("/platos/{idPlato}")
	public PlatoDto actualizarPlato(@PathVariable Long idPlato, @RequestBody PlatoDto platoDto) {
		return platoService.actualizarPLato(idPlato, platoDto);
	}

	@DeleteMapping("platos/{idPlato}")
	public void borrarPlato(@PathVariable Long idPlato) {
		platoService.borrarPlato(idPlato);
	}
}
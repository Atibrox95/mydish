package com.nerea.mydish.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nerea.mydish.service.PlatoService;
import com.nerea.mydish.service.dto.PlatoDto;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PlatoController {
	@Autowired
	private PlatoService platoService;

	@GetMapping("/platos")
	public List<PlatoDto> recuperar() {
		return platoService.recuperar();
	}

	@PostMapping("/platos")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PlatoDto crearPlato(@RequestBody PlatoDto plato) {
		return platoService.crearPlato(plato);
	}
}

package com.nerea.mydish.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

}

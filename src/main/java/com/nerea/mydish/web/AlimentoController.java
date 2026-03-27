package com.nerea.mydish.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nerea.mydish.service.AlimentoService;
import com.nerea.mydish.service.dto.AlimentoDto;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AlimentoController {
	@Autowired
	private AlimentoService alimentoService;

	@GetMapping("/alimentos")
	public List<AlimentoDto> recuperar() {
		return alimentoService.recuperar();
	}
}

package com.cibertec.gestionHotel.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cibertec.gestionHotel.model.Estacionamiento;
import com.cibertec.gestionHotel.service.EstacionamientoImpService;

@RestController
@RequestMapping("/estacionamientos")
public class EstacionamientoController {

	private final EstacionamientoImpService estacionamientoImpService;
   
	public EstacionamientoController(EstacionamientoImpService estacionamientoImpService) {
		this.estacionamientoImpService = estacionamientoImpService;
	}

	@GetMapping
	public List<Estacionamiento> listarTodos() {
        return estacionamientoImpService.listarEstacionamiento();
	}
	
	@GetMapping("/estado/{estado}")
	public List<Estacionamiento> listarPorEstado(@PathVariable String estado) {
	    return estacionamientoImpService.listarPorEstado(estado);
	}
}

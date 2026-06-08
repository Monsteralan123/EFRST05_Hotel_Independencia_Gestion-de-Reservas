package com.cibertec.gestionHotel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cibertec.gestionHotel.model.Estacionamiento;
import com.cibertec.gestionHotel.repository.EstacionamientoRepository;

@Service
public class EstacionamientoImpService implements IEstacionamientoService{
 
	private final EstacionamientoRepository estacionamientoRepository;

	public EstacionamientoImpService(EstacionamientoRepository estacionamientoRepository) {
		this.estacionamientoRepository = estacionamientoRepository;
	}

	@Override
	public List<Estacionamiento> listarEstacionamiento() {
		return estacionamientoRepository.findAll();
	}

	@Override
	public List<Estacionamiento> listarPorEstado(String estado) {
		return estacionamientoRepository.findByEstado(estado);
	}
}

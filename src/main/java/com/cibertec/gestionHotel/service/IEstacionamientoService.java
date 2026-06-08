package com.cibertec.gestionHotel.service;

import java.util.List;

import com.cibertec.gestionHotel.model.Estacionamiento;

public interface IEstacionamientoService {

	List<Estacionamiento> listarEstacionamiento();
	List<Estacionamiento> listarPorEstado(String estado);
	
}

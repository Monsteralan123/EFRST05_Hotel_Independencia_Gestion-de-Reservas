package com.cibertec.gestionHotel.repository;

import com.cibertec.gestionHotel.model.Estacionamiento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Integer> {
    
	List<Estacionamiento> findByEstado(String estado);
}
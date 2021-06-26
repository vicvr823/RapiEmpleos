package com.cris.ProyectoEmpleo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cris.ProyectoEmpleo.Model.Vacante;


public interface VacantesRepository extends JpaRepository<Vacante, Integer> {
	
	List<Vacante> findByEstatus(String estatus);
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(Integer destacado , String estatus);
	List<Vacante> findBySalarioBetweenOrderBySalarioDesc(double x , double y);
	List<Vacante> findByEstatusIn(String[] estatus);
}

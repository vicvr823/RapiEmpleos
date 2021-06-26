package com.cris.ProyectoEmpleo.Service;

import java.util.List;

import org.springframework.data.domain.*;


import com.cris.ProyectoEmpleo.Model.Vacante;

public interface InterVacantesServic {

	void eliminar(Integer idVacante);
	void guardar(Vacante vacante);
	List<Vacante> buscartodo();
	Vacante buscarxid(Integer id);
	List<Vacante> buscardestacadas();
	List<Vacante> buscarByExample(Example<Vacante> example);
	Page<Vacante> buscarTodas(Pageable page);
}

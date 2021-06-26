package com.cris.ProyectoEmpleo.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.cris.ProyectoEmpleo.Model.Solicitud;


public interface InterSolicitudesService{
	
	void guardar(Solicitud solicitud);
	void eliminar(Integer idSolicitud);
	List<Solicitud> buscarTodas();
	Solicitud buscarPorId(Integer idSolicitud);	
	Page<Solicitud> buscarTodas(Pageable page);
}
